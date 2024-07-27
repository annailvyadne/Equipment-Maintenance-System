package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.Hospital;
import com.gabriel.mainms.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class HospitalController {
	Logger logger = LoggerFactory.getLogger( HospitalController.class);
	@Autowired
	private HospitalService hospitalService;
	@GetMapping("/api/hospital")
	public ResponseEntity<?> listHospital()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Hospital[] hospital = hospitalService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(hospital);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/hospital")
	public ResponseEntity<?> add(@RequestBody Hospital hospital){
		logger.info("Input >> " + hospital.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Hospital newHospital = hospitalService.create(hospital);
			logger.info("created hospital >> " + newHospital.toString() );
			response = ResponseEntity.ok(newHospital);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve hospital with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/hospital")
	public ResponseEntity<?> update(@RequestBody Hospital hospital){
		logger.info("Update Input >> hospital.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Hospital newHospital = hospitalService.update(hospital);
			response = ResponseEntity.ok(hospital);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve hospital with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/hospital/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input hospital id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Hospital hospital = hospitalService.get(id);
			response = ResponseEntity.ok(hospital);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/hospital/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			hospitalService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
