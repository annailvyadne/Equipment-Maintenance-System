package com.gabriel.mainms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashViewController implements Initializable {


	Stage stage;

	Scene manageScene = null;

	Scene splashScene = null;

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	public ImageView imgHug;

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "123";

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public void onClicked(ActionEvent actionEvent) throws IOException {
		System.out.println("SplashController :onClicked ");

		String username = usernameField.getText();
		String password = passwordField.getText();

		if (USERNAME.equals(username) && PASSWORD.equals(password)) {
			Node node = ((Node) (actionEvent.getSource()));
			Scene currentScene = node.getScene();
			Window window = currentScene.getWindow();
			window.hide();
			if (manageScene == null) {
				FXMLLoader fxmlLoader = new FXMLLoader(ManagemainJFXApp.class.getResource("manage-main-view.fxml"));
				Parent root = fxmlLoader.load();
				ManagemainController controller = fxmlLoader.getController();
				controller.setStage(stage);
				manageScene = new Scene(root, 300, 800);
				controller.setSplashScene(splashScene);
				controller.setManageScene(manageScene);
			}
			stage.setTitle("main Management");
			stage.setScene(manageScene);
			stage.show();
		} else {
			System.out.println("Invalid username or password");
			// Optionally show an alert dialog here
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setSplashScene(Scene splashScene) {
		this.splashScene = splashScene;
	}
}
