package com.gabriel.mainms.service;
import com.gabriel.mainms.model.Task;
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
public class TaskService {
	Logger logger = LoggerFactory.getLogger(TaskService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/task";

	protected static TaskService service= null;
	public static TaskService getService(){
		if(service == null){
			service = new TaskService();
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

	public Task get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Task> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Task.class);
		return response.getBody();
	}

	public Task[] getAll() {
		String url = endpointUrl;
		logger.info("getTasks: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Task[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Task[].class);
		Task[] tasks = response.getBody();
		return tasks;
	}

	public Task create(Task task) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Task> request = new HttpEntity<>(task, headers);
		final ResponseEntity<Task> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Task.class);
		return response.getBody();
	}
	public Task update(Task task) {
		logger.info("update: " + task.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Task> request = new HttpEntity<>(task, headers);
		final ResponseEntity<Task> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Task.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Task> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Task> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Task.class);
	}
}
