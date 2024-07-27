package com.gabriel.mainms;

import com.gabriel.mainms.model.main;
import com.gabriel.mainms.service.mainService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatemainController extends GenericmainController implements Initializable {
	public ImageView imgmain;
	public ComboBox<String> cmbHospital;
	public ComboBox<String> cmbDepartment;
	public ComboBox<String> cmbEquipment;
	public ComboBox<String> cmbStatus;
	public ComboBox<String> cmbTechnician;
	public ComboBox<String> cmbTask;

	private ManagemainController manageMainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
		setupListeners();
	}

	private void setupListeners() {
		cmbTask.valueProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				String description = getTaskDescription(newValue);
				txtTaskDescription.setText(description);
			}
		});
	}

	private String getTaskDescription(String task) {
		switch (task) {
			case "Repair":
				return "Fix or restore the functionality of the equipment.";
			case "Replace":
				return "Swap out the old equipment with new one.";
			case "Clean":
				return "Perform cleaning and maintenance on the equipment.";
			case "Rebuild":
				return "Disassemble and reconstruct the equipment.";
			case "Investigate":
				return "Examine and identify issues with the equipment.";
			default:
				return "";
		}
	}

	@Override
	public void init() {
		String[] hospitals = {
				"Evergreen Hospital",
				"Summit Regional Hospital",
				"Hopewell Hospital",
				"Trinity Care Hospital",
				"Healthway Hospital"
		};
		cmbHospital.getItems().addAll(hospitals);

		String[] departments = {
				"Intensive Care Unit",
				"Cardiology",
				"Pediatrics",
				"Surgery",
				"Radiology"
		};
		cmbDepartment.getItems().addAll(departments);

		String[] equipments = {
				"Lighting",
				"Pulse Oximeter",
				"X-Ray machine",
				"Electrocardiogram machine",
				"Blood Pressure Monitor"
		};
		cmbEquipment.getItems().addAll(equipments);

		String[] statuses = {
				"Pending",
				"In Progress",
				"Completed",
				"Failed",
				"Cancelled",
				"Deferred",
				"On Hold"
		};
		cmbStatus.getItems().addAll(statuses);

		String[] technicians = {
				"Pat Taylor",
				"John Doe",
				"Richard William",
				"Jane Smith",
				"Chris Brown",
				"May Chua"
		};
		cmbTechnician.getItems().addAll(technicians);

		String[] tasks = {
				"Repair",
				"Replace",
				"Clean",
				"Rebuild",
				"Investigate"
		};
		cmbTask.getItems().addAll(tasks);

		clearFields("Edit");
		enableFields(true);
	}

	public void setManageMainController(ManagemainController controller) {
		this.manageMainController = controller;
	}


	public void onSubmit(ActionEvent actionEvent) {
		try {
			main newMain = toObject(true);

			if (newMain == null) {
				throw new IllegalArgumentException("Main object creation failed due to invalid input.");
			}

			main createdMain = mainService.getService().create(newMain);

			// Notify ManagemainController to refresh its data
			if (manageMainController != null) {
				manageMainController.refreshListView();
			}

			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();

			stage.setTitle("Manage main");
			stage.setScene(manageScene);
			stage.show();
		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input", "Please ensure all numeric fields are filled correctly.");
		} catch (Exception e) {
			showErrorDialog("Error encountered creating main", e.getMessage());
		}
	}

		public main toObject(boolean validate) {
		main newMain = new main();

		try {
			newMain.setHospitalName(cmbHospital.getValue());
			newMain.setDepartmentName(cmbDepartment.getValue());
			newMain.setEquipmentName(cmbEquipment.getValue());
			newMain.setStatusName(cmbStatus.getValue());
			newMain.setTechnicianName(cmbTechnician.getValue());
			newMain.setTaskName(cmbTask.getValue());


		} catch (NumberFormatException e) {
			showErrorDialog("Invalid input", "Please ensure all numeric fields are filled correctly.");
			return null;
		} catch (NullPointerException e) {
			showErrorDialog("Missing input", "Please fill all the required fields.");
			return null;
		}

		return newMain;
	}

	public void showErrorDialog(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
