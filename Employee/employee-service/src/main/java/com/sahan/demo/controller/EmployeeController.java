package com.sahan.demo.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahan.demo.modal.*;
import com.sahan.demo.repository.EmployeeJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sahan.demo.repository.EmployeeRepository;
import com.sahan.demo.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping(value = "/emscloud")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public Employee save(@RequestBody Employee employee) {
		
		return employeeService.save(employee);
	}
	// delete
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		return employeeService.delete(id);
	}

	@RequestMapping(value = "/employee/telephone/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllTelephones(@PathVariable Integer id) {

		return employeeService.deleteTelephones(id);
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public List<Employee> fetchAllEmployees() {
		
		return employeeService.fetchAllEmployees();
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> fetchEmployee(@PathVariable Integer id) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
//		
//		OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//				SecurityContextHolder.getContext().getAuthentication().getDetails();
//		httpHeaders.add("Authorization","bearer".concat(details.getTokenValue()));
		Employee employee = new Employee();
		employee.setId(id);
		Employee employee1 = employeeService.fetchEmployee(employee, httpHeaders);
		if(employee1 == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employee1);
		}
	}
	
	@RequestMapping(value = "/employee/{id}/projects", method = RequestMethod.GET)
	public ResponseEntity<List<Project>> fetchEmployee1(@PathVariable Integer id) {
		HttpHeaders httpHeaders = new HttpHeaders();
//		
//		OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//				SecurityContextHolder.getContext().getAuthentication().getDetails();
//		httpHeaders.add("Authorization","bearer".concat(details.getTokenValue()));
		
		Employee employee = new Employee();
		employee.setId(id);
		Employee employee1 = employeeService.fetchEmployee(employee, httpHeaders);
		if(employee1 == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employee1.getProjects());
		}
	}

	// run query sql statements
	@RequestMapping(value = "/fromQuery", method = RequestMethod.GET)
	public ResponseEntity<?> fetchFromQuery(@RequestBody QueryGenerator query) {
		ObjectMapper mapper = new ObjectMapper();
		String str = null;
		Object employee1 =  employeeService.runAnyQuery(query);
		try {
			 str = mapper.writeValueAsString(employee1);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println( str);


		if(employee1 == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employee1);
		}
	}

	// upload image as a blob file to mysql
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage( @RequestBody ImageResponse string) {
		return employeeService.saveImage(string.getString());

	}
	// fetch image  and pass base 64 string
	@RequestMapping(value = "/uploadImage/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> uploadImageByID(@PathVariable Integer id ) {
		return employeeService.fetchImageById(id);

	}


}
// response body to be passed on /employee post method
//{
//    "name": "name2",
//     "age": "age1",
//      "nic": "nic3",
//    "address": {
//        "town": "ja ela2",
//        "city": "city2"
//    },
//    "telephones": [
//        {
//            "number": "number3"
//        },
//        {
//            "number": "number4"
//        }
//	    ],
//	    "projects": [
//	        {
//	            "name": "project3"
//	        },
//	        {
//	            "name": "project4"
//	        }
//	    ]
//}

//response body to be passed from /fromQuery get method
//{
//    "query": "select e.name, e.id, e.nic, e.age, e.address_id, a.city, a.town from employee e, address a where a.id = e.address_id",
//    "root": "root",
//    "password": "1qaz2wsx@",
//    "myDriver": "com.mysql.jdbc.Driver",
//    "myUrl": "jdbc:mysql://localhost:3306/emsdb"
//}

// additional query
//{
//   	"query":"SELECT s.city, s.name FROM school s",
//    "root": "root",
//    "password": "1qaz2wsx@",
//    "myDriver": "com.mysql.jdbc.Driver",
//    "myUrl": "jdbc:mysql://localhost:3306/schooldb"
//}