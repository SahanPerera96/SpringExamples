package com.evictory.ecloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evictory.ecloud.modal.Allocation;

public interface AllocationRepository extends JpaRepository<Allocation, Integer>{

	List<Allocation> findByEmpid(Integer empid);
}
