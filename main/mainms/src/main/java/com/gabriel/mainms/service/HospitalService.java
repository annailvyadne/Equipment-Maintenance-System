package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Hospital;
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
public class HospitalService {
	Logger logger = LoggerFactory.getLogger(HospitalService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/hospital";

	protected static HospitalService service= null;
	public static HospitalService getService(){
		if(service == null){
			service = new HospitalService();
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

	public Hospital get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Hospital> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Hospital.class);
		return response.getBody();
	}

	public Hospital[] getAll() {
		String url = endpointUrl;
		logger.info("getHospitals: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Hospital[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Hospital[].class);
		Hospital[] hospitals = response.getBody();
		return hospitals;
	}

	public Hospital create(Hospital hospital) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Hospital> request = new HttpEntity<>(hospital, headers);
		final ResponseEntity<Hospital> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Hospital.class);
		return response.getBody();
	}
	public Hospital update(Hospital hospital) {
		logger.info("update: " + hospital.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Hospital> request = new HttpEntity<>(hospital, headers);
		final ResponseEntity<Hospital> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Hospital.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Hospital> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Hospital> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Hospital.class);
	}
}
