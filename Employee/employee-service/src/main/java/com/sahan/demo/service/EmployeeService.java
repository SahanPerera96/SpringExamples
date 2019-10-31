package com.sahan.demo.service;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.sahan.demo.modal.Employee;
import com.sahan.demo.modal.QueryGenerator;

public interface EmployeeService {

	Employee save(Employee employee);
	List<Employee> fetchAllEmployees();


	Object runAnyQuery(QueryGenerator generator);

	Employee fetchEmployee(Employee employee, HttpHeaders headers);

	ResponseEntity<?> delete(Integer id);

	ResponseEntity<?> deleteTelephones(Integer id);

	ResponseEntity<?> saveImage(String string);

	ResponseEntity<?> fetchImageById(Integer id);


}
