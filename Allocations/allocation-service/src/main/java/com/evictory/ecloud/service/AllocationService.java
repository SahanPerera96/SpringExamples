package com.evictory.ecloud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evictory.ecloud.modal.Allocation;


public interface AllocationService {

	Allocation save(Allocation allocation);
	
	List<Allocation> fetchByEmpId(Integer id);
	
	List<Allocation> fetchAll();
}
