package net.javaguides.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String baseUrl;

    @BeforeEach
    void setUp(){
        baseUrl = "http://localhost:"+port+"/api/v1/employees";
        employeeRepository.deleteAll();
    }

    @Test
    void testAddEmployee() {
        
        Employee employee = new Employee("Nimal","Siriwardena","nimal1235@gmail.com");

        ResponseEntity<Employee> response = testRestTemplate.postForEntity(baseUrl, employee, Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nimal", Objects.requireNonNull(response.getBody()).getFirstName());
    }

    @Test
    void testGetAllEmployees() {

        employeeRepository.save(new Employee("Nimal","Siriwardena","nimal1235@gmail.com"));

        ResponseEntity<Employee[]> response = testRestTemplate.getForEntity(baseUrl, Employee[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).length > 0);
    }

    @Test
    void testGetOneEmployee() {

        Employee employee = new Employee("Nimal","Siriwardena","nimal1235@gmail.com");
        ResponseEntity<Employee> postResponse = testRestTemplate.postForEntity(baseUrl, employee, Employee.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Employee createdEmployee = postResponse.getBody();

        String url = baseUrl+"/"+createdEmployee.getId();

        ResponseEntity<Employee> response = testRestTemplate.getForEntity(url, Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Employee getResponseBody = response.getBody();
        assertNotNull(getResponseBody);
        assertEquals("Nimal", getResponseBody.getFirstName());
        assertEquals("Siriwardena", getResponseBody.getLastName());
        assertEquals("nimal1235@gmail.com", getResponseBody.getEmailId());
    }

    @Test
    void testUpdateEmployee() {

        Employee employee = new Employee("Nimal","Siriwardena","nimal1235@gmail.com");
        ResponseEntity<Employee> postResponse = testRestTemplate.postForEntity(baseUrl, employee, Employee.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Employee createdEmployee = postResponse.getBody();

        String url = baseUrl+"/"+createdEmployee.getId();
        Employee updateEmp = new Employee("Nimal","Siriwardena","nimal1236@gmail.com");

        testRestTemplate.put(url, updateEmp);

        Employee updatedEmployee = testRestTemplate.getForObject(url, Employee.class);
        assertNotNull(updatedEmployee);
        assertEquals("Nimal", updatedEmployee.getFirstName());
        assertEquals("Siriwardena", updatedEmployee.getLastName());
        assertEquals("nimal1236@gmail.com", updatedEmployee.getEmailId());
    }

    @Test
    void testDeleteEmployee() {

        Employee employee = new Employee("Nimal","Siriwardena","nimal1235@gmail.com");
        ResponseEntity<Employee> postResponse = testRestTemplate.postForEntity(baseUrl, employee, Employee.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Employee createdEmployee = postResponse.getBody();

        String url = baseUrl+"/"+createdEmployee.getId();
        testRestTemplate.delete(url);

        Employee deletedEmployee = testRestTemplate.getForObject(url, Employee.class);
        assertNull(deletedEmployee);
    }
}