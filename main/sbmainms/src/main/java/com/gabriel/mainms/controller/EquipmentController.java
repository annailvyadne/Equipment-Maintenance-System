package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.Equipment;
import com.gabriel.mainms.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class EquipmentController {
	Logger logger = LoggerFactory.getLogger( EquipmentController.class);
	@Autowired
	private EquipmentService equipmentService;
	@GetMapping("/api/equipment")
	public ResponseEntity<?> listEquipment()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Equipment[] equipment = equipmentService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(equipment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/equipment")
	public ResponseEntity<?> add(@RequestBody Equipment equipment){
		logger.info("Input >> " + equipment.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Equipment newEquipment = equipmentService.create(equipment);
			logger.info("created equipment >> " + newEquipment.toString() );
			response = ResponseEntity.ok(newEquipment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve equipment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/equipment")
	public ResponseEntity<?> update(@RequestBody Equipment equipment){
		logger.info("Update Input >> equipment.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Equipment newEquipment = equipmentService.update(equipment);
			response = ResponseEntity.ok(equipment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve equipment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/equipment/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input equipment id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Equipment equipment = equipmentService.get(id);
			response = ResponseEntity.ok(equipment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/equipment/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			equipmentService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
