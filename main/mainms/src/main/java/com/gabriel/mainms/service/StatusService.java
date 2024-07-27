package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Status;
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
public class StatusService {
	Logger logger = LoggerFactory.getLogger(StatusService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/status";

	protected static StatusService service= null;
	public static StatusService getService(){
		if(service == null){
			service = new StatusService();
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

	public Status get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Status> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Status.class);
		return response.getBody();
	}

	public Status[] getAll() {
		String url = endpointUrl;
		logger.info("getStatuss: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Status[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Status[].class);
		Status[] statuss = response.getBody();
		return statuss;
	}

	public Status create(Status status) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Status> request = new HttpEntity<>(status, headers);
		final ResponseEntity<Status> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Status.class);
		return response.getBody();
	}
	public Status update(Status status) {
		logger.info("update: " + status.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Status> request = new HttpEntity<>(status, headers);
		final ResponseEntity<Status> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Status.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Status> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Status> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Status.class);
	}
}
