package com.gabriel.mainms;

import com.gabriel.mainms.model.*;
import com.gabriel.mainms.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

public class DeletemainController extends GenericmainController {
    @FXML
    private ImageView imgMain;
    @FXML
    private ComboBox<Hospital> cmbHospital;
    @FXML
    private ComboBox<Department> cmbDepartment;
    @FXML
    private ComboBox<Equipment> cmbEquipment;
    @FXML
    private ComboBox<Status> cmbStatus;
    @FXML
    private ComboBox<Technician> cmbTechnician;
    @FXML
    private ComboBox<Task> cmbTask;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnBack;

    @Setter
    private Stage stage;
    @Setter
    private Scene manageScene;

    private ManagemainController manageMainController;

    @Override
    public void init() {
        // Populate ComboBoxes with data
        try {
            cmbHospital.getItems().addAll(HospitalService.getService().getAll());
            cmbDepartment.getItems().addAll(DepartmentService.getService().getAll());
            cmbEquipment.getItems().addAll(EquipmentService.getService().getAll());
            cmbStatus.getItems().addAll(StatusService.getService().getAll());
            cmbTechnician.getItems().addAll(TechnicianService.getService().getAll());
            cmbTask.getItems().addAll(TaskService.getService().getAll());
        } catch (Exception e) {
            showErrorDialog("Error loading data", e.getMessage());
            e.printStackTrace();
        }
        setFields("Delete");
        enableFields(false);
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        try {
            main main = this.toObject(true);
            System.out.println("Attempting to delete main record with ID: " + main.getId());

            // Check if mainService is initialized
            if (mainService.getService() == null) {
                System.out.println("mainService is null.");
                showErrorDialog("Service Error", "mainService is not initialized.");
                return;
            }

            // Perform deletion
            mainService.getService().delete(main.getId());
            System.out.println("Record deleted.");

            // Refresh the list view in ManagemainController
            if (manageMainController != null) {
                System.out.println("Refreshing list view in ManagemainController.");
                manageMainController.refreshListView();
            } else {
                System.out.println("manageMainController is null");
            }

            // Close the current window and return to the manage view
            Node node = (Node) actionEvent.getSource();
            Window window = node.getScene().getWindow();
            window.hide();
            if (this.stage == null) {
                this.stage = new Stage();
            }

            this.stage.setTitle("Manage main");
            this.stage.setScene(this.manageScene);
            this.stage.show();
        } catch (Exception e) {
            this.showErrorDialog("Error encountered deleting main", e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeWindowAndReturnToManageMain(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Window window = node.getScene().getWindow();
        window.hide();

        if (this.stage == null) {
            this.stage = new Stage();
        }
        this.stage.setTitle("Manage main");
        this.stage.setScene(this.manageScene);
        this.stage.show();
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        super.onClose(actionEvent);
    }

    public void setManagemainController(ManagemainController manageMainController) {
        this.manageMainController = manageMainController;
    }
}
