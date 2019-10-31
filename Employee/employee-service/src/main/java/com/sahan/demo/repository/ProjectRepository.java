package com.sahan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahan.demo.modal.Employee;
import com.sahan.demo.modal.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

//    @Query("select c from project c WHERE c.employee_id like :Name")
//    List<Project> find(@Param("Name") String name);
}
