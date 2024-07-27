package com.gabriel.mainms.controller;
import com.gabriel.mainms.model.main;
import com.gabriel.mainms.service.mainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class mainController {
	Logger logger = LoggerFactory.getLogger( mainController.class);
	@Autowired
	private mainService mainService;
	@GetMapping("/api/main")
	public ResponseEntity<?> listmain()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			main[] main = mainService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(main);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/main")
	public ResponseEntity<?> add(@RequestBody main main){
		logger.info("Input >> " + main.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			main newmain = mainService.create(main);
			logger.info("created main >> " + newmain.toString() );
			response = ResponseEntity.ok(newmain);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve main with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/main")
	public ResponseEntity<?> update(@RequestBody main main){
		logger.info("Update Input >> main.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			main newmain = mainService.update(main);
			response = ResponseEntity.ok(main);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve main with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/main/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input main id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			main main = mainService.get(id);
			response = ResponseEntity.ok(main);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/main/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			mainService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
