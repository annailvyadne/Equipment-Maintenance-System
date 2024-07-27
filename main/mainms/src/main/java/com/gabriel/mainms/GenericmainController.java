package com.gabriel.mainms;
import com.gabriel.mainms.model.main;
import com.gabriel.mainms.model.Hospital;
import com.gabriel.mainms.service.HospitalService;
import com.gabriel.mainms.model.Department;
import com.gabriel.mainms.service.DepartmentService;
import com.gabriel.mainms.model.Equipment;
import com.gabriel.mainms.service.EquipmentService;
import com.gabriel.mainms.model.Status;
import com.gabriel.mainms.service.StatusService;
import com.gabriel.mainms.model.Technician;
import com.gabriel.mainms.service.TechnicianService;
import com.gabriel.mainms.model.Task;
import com.gabriel.mainms.service.TaskService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale;

public class GenericmainController implements Initializable{
	@Setter
	CreatemainController createmainController;

	@Setter
	DeletemainController deletemainController ;

	@Setter
	EditmainController editmainController;


	ManagemainController ManagemainController;


	Stage stage;


	Scene splashScene;


	Scene manageScene;


	public ListView<main> lvmains;

	public static main selectedItem;
	public TextField txtId;
	public TextField txtHospitalName;
	public ComboBox<Hospital> cmbHospital;
	public TextField txtDepartmentName;
	public ComboBox<Department> cmbDepartment;
	public TextField txtEquipmentName;
	public ComboBox<Equipment> cmbEquipment;
	public TextField txtStatusName;
	public ComboBox<Status> cmbStatus;
	public TextField txtTechnicianName;
	public ComboBox<Technician> cmbTechnician;
	public TextField txtTaskName;
	public ComboBox<Task> cmbTask;
	public TextField txtTaskDescription;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Hospital[] hospitals =  (Hospital[]) HospitalService.getService().getAll();
		cmbHospital.getItems().addAll(hospitals);
		Department[] departments =  (Department[]) DepartmentService.getService().getAll();
		cmbDepartment.getItems().addAll(departments);
		Equipment[] equipments =  (Equipment[]) EquipmentService.getService().getAll();
		cmbEquipment.getItems().addAll(equipments);
		Status[] statuss =  (Status[]) StatusService.getService().getAll();
		cmbStatus.getItems().addAll(statuss);
		Technician[] technicians =  (Technician[]) TechnicianService.getService().getAll();
		cmbTechnician.getItems().addAll(technicians);
		Task[] tasks =  (Task[]) TaskService.getService().getAll();
		cmbTask.getItems().addAll(tasks);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected main toObject(boolean isEdit){
		main main= new main();
		try {
			if(isEdit) {
				main.setId(Integer.parseInt(txtId.getText()));
			}
			main.setHospitalName(txtHospitalName.getText());
			Hospital hospital = cmbHospital.getSelectionModel().getSelectedItem();
			main.setHospitalId(hospital.getId());
			main.setDepartmentName(txtDepartmentName.getText());
			Department department = cmbDepartment.getSelectionModel().getSelectedItem();
			main.setDepartmentId(department.getId());
			main.setEquipmentName(txtEquipmentName.getText());
			Equipment equipment = cmbEquipment.getSelectionModel().getSelectedItem();
			main.setEquipmentId(equipment.getId());
			main.setStatusName(txtStatusName.getText());
			Status status = cmbStatus.getSelectionModel().getSelectedItem();
			main.setStatusId(status.getId());
			main.setTechnicianName(txtTechnicianName.getText());
			Technician technician = cmbTechnician.getSelectionModel().getSelectedItem();
			main.setTechnicianId(technician.getId());
			main.setTaskName(txtTaskName.getText());
			Task task = cmbTask.getSelectionModel().getSelectedItem();
			main.setTaskId(task.getId());
			main.setTaskDescription(txtTaskDescription.getText());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return main;
	}
	protected void setFields(String action){
		String formattedDate;
		main main = GenericmainController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(main.getId()));
		txtHospitalName.setText(main.getHospitalName());
		Hospital hospital = HospitalService.getService().get(main.getHospitalId());
		cmbHospital.getSelectionModel().select(hospital);
		if(action.equals("Create") || action.equals("Edit")){
			cmbHospital.setVisible(true);
			txtHospitalName.setVisible(false);
		}
		else{
			cmbHospital.setVisible(false);
			txtHospitalName.setVisible(true);
		}
		txtDepartmentName.setText(main.getDepartmentName());
		Department department = DepartmentService.getService().get(main.getDepartmentId());
		cmbDepartment.getSelectionModel().select(department);
		if(action.equals("Create") || action.equals("Edit")){
			cmbDepartment.setVisible(true);
			txtDepartmentName.setVisible(false);
		}
		else{
			cmbDepartment.setVisible(false);
			txtDepartmentName.setVisible(true);
		}
		txtEquipmentName.setText(main.getEquipmentName());
		Equipment equipment = EquipmentService.getService().get(main.getEquipmentId());
		cmbEquipment.getSelectionModel().select(equipment);
		if(action.equals("Create") || action.equals("Edit")){
			cmbEquipment.setVisible(true);
			txtEquipmentName.setVisible(false);
		}
		else{
			cmbEquipment.setVisible(false);
			txtEquipmentName.setVisible(true);
		}
		txtStatusName.setText(main.getStatusName());
		Status status = StatusService.getService().get(main.getStatusId());
		cmbStatus.getSelectionModel().select(status);
		if(action.equals("Create") || action.equals("Edit")){
			cmbStatus.setVisible(true);
			txtStatusName.setVisible(false);
		}
		else{
			cmbStatus.setVisible(false);
			txtStatusName.setVisible(true);
		}
		txtTechnicianName.setText(main.getTechnicianName());
		Technician technician = TechnicianService.getService().get(main.getTechnicianId());
		cmbTechnician.getSelectionModel().select(technician);
		if(action.equals("Create") || action.equals("Edit")){
			cmbTechnician.setVisible(true);
			txtTechnicianName.setVisible(false);
		}
		else{
			cmbTechnician.setVisible(false);
			txtTechnicianName.setVisible(true);
		}
		txtTaskName.setText(main.getTaskName());
		Task task = TaskService.getService().get(main.getTaskId());
		cmbTask.getSelectionModel().select(task);
		if(action.equals("Create") || action.equals("Edit")){
			cmbTask.setVisible(true);
			txtTaskName.setVisible(false);
		}
		else{
			cmbTask.setVisible(false);
			txtTaskName.setVisible(true);
		}
		txtTaskDescription.setText(main.getTaskDescription());
	}

	protected void clearFields(String action){
		txtId.setText("");
		//txtHospitalName.setText("");
		cmbHospital.getSelectionModel().clearSelection();
		txtHospitalName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbHospital.setVisible(true);
			txtHospitalName.setVisible(false);
		}
		else{
			cmbHospital.setVisible(false);
			txtHospitalName.setVisible(true);
		}
		//txtDepartmentName.setText("");
		cmbDepartment.getSelectionModel().clearSelection();
		txtDepartmentName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbDepartment.setVisible(true);
			txtDepartmentName.setVisible(false);
		}
		else{
			cmbDepartment.setVisible(false);
			txtDepartmentName.setVisible(true);
		}
		//txtEquipmentName.setText("");
		cmbEquipment.getSelectionModel().clearSelection();
		txtEquipmentName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbEquipment.setVisible(true);
			txtEquipmentName.setVisible(false);
		}
		else{
			cmbEquipment.setVisible(false);
			txtEquipmentName.setVisible(true);
		}
		//txtStatusName.setText("");
		cmbStatus.getSelectionModel().clearSelection();
		txtStatusName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbStatus.setVisible(true);
			txtStatusName.setVisible(false);
		}
		else{
			cmbStatus.setVisible(false);
			txtStatusName.setVisible(true);
		}
		//txtTechnicianName.setText("");
		cmbTechnician.getSelectionModel().clearSelection();
		txtTechnicianName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbTechnician.setVisible(true);
			txtTechnicianName.setVisible(false);
		}
		else{
			cmbTechnician.setVisible(false);
			txtTechnicianName.setVisible(true);
		}
		//txtTaskName.setText("");
		cmbTask.getSelectionModel().clearSelection();
		txtTaskName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbTask.setVisible(true);
			txtTaskName.setVisible(false);
		}
		else{
			cmbTask.setVisible(false);
			txtTaskName.setVisible(true);
		}
		//txtTaskDescription.setText("");
	}

	protected void enableFields(boolean enable){
		txtHospitalName.editableProperty().set(enable);
		cmbHospital.editableProperty().set(enable);
		txtHospitalName.editableProperty().set(enable);
		txtDepartmentName.editableProperty().set(enable);
		cmbDepartment.editableProperty().set(enable);
		txtDepartmentName.editableProperty().set(enable);
		txtEquipmentName.editableProperty().set(enable);
		cmbEquipment.editableProperty().set(enable);
		txtEquipmentName.editableProperty().set(enable);
		txtStatusName.editableProperty().set(enable);
		cmbStatus.editableProperty().set(enable);
		txtStatusName.editableProperty().set(enable);
		txtTechnicianName.editableProperty().set(enable);
		cmbTechnician.editableProperty().set(enable);
		txtTechnicianName.editableProperty().set(enable);
		txtTaskName.editableProperty().set(enable);
		cmbTask.editableProperty().set(enable);
		txtTaskName.editableProperty().set(enable);
		txtTaskDescription.editableProperty().set(enable);
	}

	public int getId(){
		return Integer.parseInt(txtId.getText());
	}

	protected void showErrorDialog(String message, String expandedMessage){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(expandedMessage)));
		alert.showAndWait();
	}
	public void onBack(ActionEvent actionEvent) {
		Node node = ((Node) (actionEvent.getSource()));
		Window window = node.getScene().getWindow();
		window.hide();
		stage.setScene(manageScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		Platform.exit();
	}
	LocalDate toLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = instant.atZone( z );
		return zdt.toLocalDate();
	}
	protected Date toDate(LocalDate ld){
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = ld.atStartOfDay(z);
		Instant instant  = zdt.toInstant();
		return Date.from(instant);
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}

	public void setSplashScene(final Scene splashScene) {
		this.splashScene = splashScene;
	}

	public void setManageScene(final Scene manageScene) {
		this.manageScene = manageScene;
	}

	public void setLvmains(final ListView<main> lvmains) {
		this.lvmains = lvmains;
	}

	public static void setSelectedItem(final main selectedItem) {
		GenericmainController.selectedItem = selectedItem;
	}
}

