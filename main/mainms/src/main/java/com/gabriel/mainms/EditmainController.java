package com.gabriel.mainms;

import com.gabriel.mainms.model.main;
import com.gabriel.mainms.service.mainService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class EditmainController extends GenericmainController {

    @FXML
    private ComboBox<String> cmbHospital;
    @FXML
    private ComboBox<String> cmbDepartment;
    @FXML
    private ComboBox<String> cmbEquipment;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private ComboBox<String> cmbTechnician;
    @FXML
    private ComboBox<String> cmbTask;
    @FXML
    private TextField txtTaskDescription;

    @Setter
    private main selectedMain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        populateFields(); // Populate fields with the selected main object values
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

    public void populateFields() {
        if (this.selectedMain != null) {
            System.out.println("Populating fields with selectedMain: " + this.selectedMain); // Debug
            this.cmbHospital.setValue(this.selectedMain.getHospitalName());
            this.cmbDepartment.setValue(this.selectedMain.getDepartmentName());
            this.cmbEquipment.setValue(this.selectedMain.getEquipmentName());
            this.cmbStatus.setValue(this.selectedMain.getStatusName());
            this.cmbTechnician.setValue(this.selectedMain.getTechnicianName());
            this.cmbTask.setValue(this.selectedMain.getTaskName());
            this.txtTaskDescription.setText(this.selectedMain.getTaskDescription());
        } else {
            System.out.println("Selected main is null"); // Debug
        }
    }

    @Override
    public main toObject(boolean isCreate) {
        main main = new main();
        if (!isCreate && this.selectedMain != null) {
            main.setId(this.selectedMain.getId()); // Ensure the ID is retained for updates
        }
        main.setHospitalName(this.cmbHospital.getValue());
        main.setDepartmentName(this.cmbDepartment.getValue());
        main.setEquipmentName(this.cmbEquipment.getValue());
        main.setStatusName(this.cmbStatus.getValue());
        main.setTechnicianName(this.cmbTechnician.getValue());
        main.setTaskName(this.cmbTask.getValue());
        main.setTaskDescription(this.txtTaskDescription.getText());
        System.out.println("Converted form to main object: " + main); // Debug
        return main;
    }

    private ManagemainController manageMainController;

    public void setManageMainController(ManagemainController manageMainController) {
        this.manageMainController = manageMainController;
    }

    public void onSubmit(ActionEvent actionEvent) {
        try {
            main updatedMain = this.toObject(false); // Ensure it's an update operation
            mainService.getService().update(updatedMain);

            // Refresh the list view
            if (this.manageMainController != null) {
                this.manageMainController.refreshListView();
            } else {
                System.err.println("manageMainController is null! Cannot refresh list view.");
            }

            // Close the edit window
            Node node = (Node) actionEvent.getSource();
            Window window = node.getScene().getWindow();
            window.hide();
        } catch (Exception e) {
            this.showErrorDialog("Error encountered updating main", e.getMessage());
        }
    }


    public void onBack(ActionEvent actionEvent) {
        try {
            Node node = (Node) actionEvent.getSource();
            Window window = node.getScene().getWindow();
            window.hide();
            if (this.stage == null) {
                this.stage = new Stage();
            }

            this.stage.setTitle("Manage Main");
            this.stage.setScene(this.manageScene);
            this.stage.show();
        } catch (Exception e) {
            this.showErrorDialog("Error encountered going back", e.getMessage());
        }
    }

    public void onClose(ActionEvent actionEvent) {
        super.onClose(actionEvent);
    }

    public void setMain(main selectedMain) {
        this.selectedMain = selectedMain;
        populateFields();
    }
}
