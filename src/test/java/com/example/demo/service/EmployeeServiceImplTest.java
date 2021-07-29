package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest // can be skipped if no need to start complete application
public class EmployeeServiceImplTest {

	/* To check the Service class, we need to have an instance of the Service class created 
	 * and available as a @Bean so that we can @Autowire it in our test class. We can achieve 
	 * this configuration using the @TestConfiguration annotation.
	 * It can be in separate class then it can be imported using @Import	 * 
	 */
//	@TestConfiguration // not required with @SpringBootTest
//    static class EmployeeServiceImplTestContextConfiguration {
// 
//        @Bean
//        public EmployeeService employeeService() {
//            return new EmployeeServiceImpl();
//        }
//    }
	
	@Autowired
	private EmployeeService empService;
	
	/* @MockBean creates a Mock for the EmployeeRepository, which can be used to bypass the call
	 *  to the actual EmployeeRepository:
	 */
	@MockBean // not working without @SpringBootTest annotation
	private EmployeeRepository empRepo;
	
	@BeforeEach
	public void setUp() {
	    Employee alex = new Employee("alex");

	    Mockito.when(empRepo.findByName(alex.getName()))
	      .thenReturn(alex);
	}
	
	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
		
		
	    String name = "alex";
	    Employee found = empService.getEmployeeByName(name);
	 
	     assertThat(found.getName())
	      .isEqualTo(name);
	 }

}
