package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Department;
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
public class DepartmentService {
	Logger logger = LoggerFactory.getLogger(DepartmentService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/department";

	protected static DepartmentService service= null;
	public static DepartmentService getService(){
		if(service == null){
			service = new DepartmentService();
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

	public Department get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Department> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Department.class);
		return response.getBody();
	}

	public Department[] getAll() {
		String url = endpointUrl;
		logger.info("getDepartments: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Department[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Department[].class);
		Department[] departments = response.getBody();
		return departments;
	}

	public Department create(Department department) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Department> request = new HttpEntity<>(department, headers);
		final ResponseEntity<Department> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Department.class);
		return response.getBody();
	}
	public Department update(Department department) {
		logger.info("update: " + department.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Department> request = new HttpEntity<>(department, headers);
		final ResponseEntity<Department> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Department.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Department> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Department> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Department.class);
	}
}
