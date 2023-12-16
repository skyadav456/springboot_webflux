package com.sharad.springboot_webflux;

import com.sharad.springboot_webflux.dto.EmployeeDto;
import com.sharad.springboot_webflux.repository.EmployeeRepository;
import com.sharad.springboot_webflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeRepository employeeRepository;

    //  execute before each test case
    @BeforeEach
    public  void beforeEach(){
        System.out.println("Before Each executed ");
        employeeRepository.deleteAll();
    }

    @Test
    public void testSaveEmployee(){
        EmployeeDto employeeDto= new EmployeeDto();
        employeeDto.setFirstName("Pari");
        employeeDto.setLastName("Sharma");
        employeeDto.setEmail("pari@gmail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto),EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testGetSingleEmp(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("shubham");
        employeeDto.setLastName("Yadav");
        employeeDto.setEmail("shubham@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get()
                .uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo((employeeDto.getEmail())

                );

    }

    @Test
    public  void testgetAllEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("shubham");
        employeeDto.setLastName("Yadav");
        employeeDto.setEmail("shubham@gmail.com");

        employeeService.saveEmployee(employeeDto).block();

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("shubham");
        employeeDto1.setLastName("Yadav");
        employeeDto1.setEmail("shubham@gmail.com");

        employeeService.saveEmployee(employeeDto1).block();

        webTestClient.get()
                .uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }
    @Test
    public  void updateEmployeeTest(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Rohini");
        employeeDto.setLastName("Maurya");
        employeeDto.setEmail("rohani@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updatedEmployee=new EmployeeDto();
        updatedEmployee.setFirstName("ABC");
        updatedEmployee.setLastName("XYZ");
        updatedEmployee.setEmail("ab@gmail.com");

        webTestClient.put()
                .uri("/api/employees/{id}",Collections.singletonMap("id",savedEmployee.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee),EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());
    }
    @Test
    public void deleteEmployeeTest(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Raju");
        employeeDto.setLastName("singh");
        employeeDto.setEmail("raju@email.com");
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.delete()
                .uri("/api/employees/{id}",Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
