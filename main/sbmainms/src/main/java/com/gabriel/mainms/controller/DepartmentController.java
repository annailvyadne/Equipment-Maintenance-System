package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.Department;
import com.gabriel.mainms.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class DepartmentController {
	Logger logger = LoggerFactory.getLogger( DepartmentController.class);
	@Autowired
	private DepartmentService departmentService;
	@GetMapping("/api/department")
	public ResponseEntity<?> listDepartment()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Department[] department = departmentService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(department);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/department")
	public ResponseEntity<?> add(@RequestBody Department department){
		logger.info("Input >> " + department.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Department newDepartment = departmentService.create(department);
			logger.info("created department >> " + newDepartment.toString() );
			response = ResponseEntity.ok(newDepartment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve department with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/department")
	public ResponseEntity<?> update(@RequestBody Department department){
		logger.info("Update Input >> department.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Department newDepartment = departmentService.update(department);
			response = ResponseEntity.ok(department);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve department with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/department/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input department id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Department department = departmentService.get(id);
			response = ResponseEntity.ok(department);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/department/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			departmentService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
