package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.Technician;
import com.gabriel.mainms.service.TechnicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class TechnicianController {
	Logger logger = LoggerFactory.getLogger( TechnicianController.class);
	@Autowired
	private TechnicianService technicianService;
	@GetMapping("/api/technician")
	public ResponseEntity<?> listTechnician()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Technician[] technician = technicianService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(technician);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/technician")
	public ResponseEntity<?> add(@RequestBody Technician technician){
		logger.info("Input >> " + technician.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Technician newTechnician = technicianService.create(technician);
			logger.info("created technician >> " + newTechnician.toString() );
			response = ResponseEntity.ok(newTechnician);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve technician with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/technician")
	public ResponseEntity<?> update(@RequestBody Technician technician){
		logger.info("Update Input >> technician.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Technician newTechnician = technicianService.update(technician);
			response = ResponseEntity.ok(technician);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve technician with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/technician/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input technician id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Technician technician = technicianService.get(id);
			response = ResponseEntity.ok(technician);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/technician/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			technicianService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
