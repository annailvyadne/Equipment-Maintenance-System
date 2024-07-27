package com.gabriel.mainms;

import com.gabriel.mainms.model.main;
import com.gabriel.mainms.service.mainService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.io.IOException;
import java.util.Arrays;

public class ManagemainController extends GenericmainController {
    @Setter
    private Stage stage;

    @Setter
    private Scene createViewScene;

    @Setter
    private Scene editViewScene;

    @Setter
    private Scene deleteViewScene;

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    private Button imageButton;

    @FXML
    private ListView<main> lvmains;

    @Override
    public void init() {
        refreshListView();
    }

    public void refreshListView() {
        try {
            main[] mains = mainService.getService().getAll();
            System.out.println("Fetched records: " + Arrays.toString(mains)); // Debug statement
            lvmains.getItems().clear();
            lvmains.getItems().addAll(mains);
        } catch (Exception e) {
            showErrorDialog("Error loading data", e.getMessage());
        }
    }

    public void onAction(MouseEvent mouseEvent) {
        GenericmainController.selectedItem = lvmains.getSelectionModel().getSelectedItem();
        if (GenericmainController.selectedItem == null) {
            return;
        }
        setFields("Manage");
    }

    public void openEditView(main selectedMain) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-main-view.fxml"));
            Parent root = loader.load();
            EditmainController editController = loader.getController();
            editController.setManageMainController(this);
            editController.setMain(selectedMain);

            // Show the edit view
            Stage editStage = new Stage();
            editStage.setTitle("Edit Main");
            editStage.setScene(new Scene(root, 300, 720)); // Adjust the size as needed
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLearnMore(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the About Us page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("about-main-view.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the About Us page
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About Us");
            aboutStage.setScene(new Scene(root));
            aboutStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Error opening About Us page", e.getMessage());
        }
    }

    public void onCreate(ActionEvent actionEvent) throws IOException {
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        if (createViewScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagemainJFXApp.class.getResource("create-main-view.fxml"));
            Parent root = fxmlLoader.load();
            CreatemainController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setManageMainController(this);
            createViewScene = new Scene(root, 300, 720);
            controller.setManageScene(manageScene);
            controller.setSplashScene(splashScene);
        }
        stage.setTitle("Create main");
        stage.setScene(createViewScene);
        stage.show();
    }

    public void onEdit(ActionEvent actionEvent) throws IOException {
        if (GenericmainController.selectedItem == null) {
            showErrorDialog("Please select a main from the list", "Cannot edit");
            return;
        }
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        if (editViewScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagemainJFXApp.class.getResource("edit-main-view.fxml"));
            Parent root = fxmlLoader.load();
            EditmainController controller = fxmlLoader.getController();
            controller.setStage(stage);
            editViewScene = new Scene(root, 300, 720);
            controller.setManageScene(manageScene);
            controller.setSplashScene(splashScene);
            // Pass the selected item to the EditmainController
            openEditView(GenericmainController.selectedItem);
        }
        stage.setTitle("Edit main");
        stage.setScene(editViewScene);
        stage.show();
    }

    public void onDelete(ActionEvent actionEvent) throws Exception {
        if (GenericmainController.selectedItem == null) {
            this.showErrorDialog("Please select an item from the list", "Cannot delete");
            return;
        }
        Node node = (Node) actionEvent.getSource();
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();

        if (this.deleteViewScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagemainJFXApp.class.getResource("delete-main-view.fxml"));
            Parent root = fxmlLoader.load();
            DeletemainController controller = fxmlLoader.getController();
            controller.setStage(this.stage);
            controller.setManagemainController(this); // Ensure this line is correct
            controller.setManageScene(currentScene);
            this.deleteViewScene = new Scene(root, 800.0, 600.0);
        }

        if (this.stage == null) {
            this.stage = new Stage();
        }
        this.stage.setTitle("Delete main");
        this.stage.setScene(this.deleteViewScene);
        this.stage.show();
    }

    public void onClose(ActionEvent actionEvent) {
        super.onClose(actionEvent);
    }
}
