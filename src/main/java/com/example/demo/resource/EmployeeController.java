package com.example.demo.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee emp) {
		return empService.addEmployee(emp);
	}
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return empService.getEmployees();
	}
	
	@GetMapping("/employees/{name}")
	public Employee getEmployeeByName(@PathVariable("name") String name) {
		return empService.getEmployeeByName(name);
	}
	
	@GetMapping("/Hello")
	public String getHelloString() {
		return "####Hello World####";
	}
}
