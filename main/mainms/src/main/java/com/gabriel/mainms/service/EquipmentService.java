package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Equipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EquipmentService {
	Logger logger = LoggerFactory.getLogger(EquipmentService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/equipment";

	protected static EquipmentService service= null;
	public static EquipmentService getService(){
		if(service == null){
			service = new EquipmentService();
		}
		return service;
	}

	RestTemplate restTemplate = null;
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
		restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public Equipment get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Equipment> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Equipment.class);
		return response.getBody();
	}

	public Equipment[] getAll() {
		String url = endpointUrl;
		logger.info("getEquipments: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Equipment[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Equipment[].class);
		Equipment[] equipments = response.getBody();
		return equipments;
	}

	public Equipment create(Equipment equipment) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Equipment> request = new HttpEntity<>(equipment, headers);
		final ResponseEntity<Equipment> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Equipment.class);
		return response.getBody();
	}
	public Equipment update(Equipment equipment) {
		logger.info("update: " + equipment.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Equipment> request = new HttpEntity<>(equipment, headers);
		final ResponseEntity<Equipment> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Equipment.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Equipment> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Equipment> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Equipment.class);
	}
}
