package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Technician;
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
public class TechnicianService {
	Logger logger = LoggerFactory.getLogger(TechnicianService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/technician";

	protected static TechnicianService service= null;
	public static TechnicianService getService(){
		if(service == null){
			service = new TechnicianService();
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

	public Technician get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Technician> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Technician.class);
		return response.getBody();
	}

	public Technician[] getAll() {
		String url = endpointUrl;
		logger.info("getTechnicians: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Technician[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Technician[].class);
		Technician[] technicians = response.getBody();
		return technicians;
	}

	public Technician create(Technician technician) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Technician> request = new HttpEntity<>(technician, headers);
		final ResponseEntity<Technician> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Technician.class);
		return response.getBody();
	}
	public Technician update(Technician technician) {
		logger.info("update: " + technician.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Technician> request = new HttpEntity<>(technician, headers);
		final ResponseEntity<Technician> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Technician.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Technician> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Technician> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Technician.class);
	}
}
