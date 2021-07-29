package com.example.demo.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Employee;

//@RunWith(SpringRunner.class) provides a bridge between Spring Boot test features and JUnit.
//@DataJpaTest provides some standard setup needed for testing the persistence layer
@RunWith(SpringRunner.class)
@DataJpaTest
class EmployeeRepositoryTest {

	@Autowired
	private TestEntityManager entityManager; // used to setup records in database
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Test
	public void whenFindByName_thenReturnEmployee() {
	    // given
	    Employee alex = new Employee("alex");
	    entityManager.persist(alex);
	    entityManager.flush();

	    // when
	    Employee found = empRepo.findByName(alex.getName());

	    // then
	    assertThat(found.getName())
	      .isEqualTo(alex.getName());
	}

}
