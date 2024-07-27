package com.gabriel.mainms.service;
import com.gabriel.mainms.model.main;
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
public class mainService {
	Logger logger = LoggerFactory.getLogger(mainService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/main";

	protected static mainService service= null;
	public static mainService getService(){
		if(service == null){
			service = new mainService();
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

	public main get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<main> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, main.class);
		return response.getBody();
	}

	public main[] getAll() {
		String url = endpointUrl;
		logger.info("getmains: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<main[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, main[].class);
		main[] mains = response.getBody();
		return mains;
	}

	public main create(main main) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<main> request = new HttpEntity<>(main, headers);
		final ResponseEntity<main> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, main.class);
		return response.getBody();
	}
	public main update(main main) {
		logger.info("update: " + main.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<main> request = new HttpEntity<>(main, headers);
		final ResponseEntity<main> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, main.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<main> request = new HttpEntity<>(null, headers);
		final ResponseEntity<main> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, main.class);
	}
}
