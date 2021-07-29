package com.example.demo.resource;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmployeeConsumeController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/consume/employees")
	public ResponseEntity<String> getEmployees() {
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		return restTemplate.exchange("http://localhost:8087/employees",HttpMethod.GET,entity,String.class);
	}
	
		
	@GetMapping("/consume/products")
	public String getProducts() {
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		return restTemplate.exchange("http://localhost:8085/products",HttpMethod.GET,entity,String.class).getBody();
	}
	
}
