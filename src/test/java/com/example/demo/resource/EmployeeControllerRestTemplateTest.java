package com.example.demo.resource;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerRestTemplateTest {
	 
	@LocalServerPort
	private int port;
	
	private static final ObjectMapper om = new ObjectMapper();
	 
	@MockBean
	private EmployeeService empService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
	  throws Exception {
		
		Employee alex = new Employee("alex");

	    List<Employee> allEmployees = Arrays.asList(alex);

		when(empService.getEmployees()).thenReturn(allEmployees);
		
		String expected = om.writeValueAsString(allEmployees); 
		
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:"+port+"/employees", String.class);
		System.out.println("#### "+response.getBody()+" ###");
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	    

	}

}
