package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.Task;
import com.gabriel.mainms.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class TaskController {
	Logger logger = LoggerFactory.getLogger( TaskController.class);
	@Autowired
	private TaskService taskService;
	@GetMapping("/api/task")
	public ResponseEntity<?> listTask()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Task[] task = taskService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(task);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/task")
	public ResponseEntity<?> add(@RequestBody Task task){
		logger.info("Input >> " + task.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Task newTask = taskService.create(task);
			logger.info("created task >> " + newTask.toString() );
			response = ResponseEntity.ok(newTask);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve task with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/task")
	public ResponseEntity<?> update(@RequestBody Task task){
		logger.info("Update Input >> task.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Task newTask = taskService.update(task);
			response = ResponseEntity.ok(task);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve task with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/task/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input task id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Task task = taskService.get(id);
			response = ResponseEntity.ok(task);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/task/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			taskService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
