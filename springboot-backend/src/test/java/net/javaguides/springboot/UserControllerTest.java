package net.javaguides.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:"+port+"/api/auth";
        userRepository.deleteAll();
    }

    @Test
    void testUserRegister() {

        User user = new User("Emp123","nimal1345@gmail.com","nimal2356",Set.of("Admin"));

        String url = baseUrl + "/register";
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testLogin(){

        User user = new User("Emp123","nimal1345@gmail.com","nimal2356",Set.of("Admin"));
        String url = baseUrl + "/register";

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, user, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User userLogin = new User("","nimal1345@gmail.com","nimal2356",Set.of(""));
        String urlForLogin = baseUrl + "/login";
        ResponseEntity<String> loginResponse = testRestTemplate.postForEntity(urlForLogin, userLogin, String.class);

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

    }
    
}
