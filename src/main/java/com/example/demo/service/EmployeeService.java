package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Employee;

public interface EmployeeService {

	public Employee getEmployeeByName(String name);
	public Employee addEmployee(Employee emp);
	public List<Employee> getEmployees();
}
