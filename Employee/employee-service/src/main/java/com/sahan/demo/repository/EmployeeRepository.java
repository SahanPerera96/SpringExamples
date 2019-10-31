package com.sahan.demo.repository;

import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sahan.demo.modal.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
