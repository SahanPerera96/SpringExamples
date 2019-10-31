package com.evictory.ecloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evictory.ecloud.modal.Allocation;
import com.evictory.ecloud.service.AllocationService;


@RestController
@CrossOrigin
@RequestMapping(value = "/allocate")
public class AllocationController {
	

	@Autowired
	AllocationService allocationService;
	
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public Allocation save(@RequestBody Allocation allocation) {
		
		return allocationService.save(allocation);
	}
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public List<Allocation> fetchAll() {
		
		return allocationService.fetchAll();
	}
	
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public List<Allocation> fetchById(@PathVariable Integer id) {
		
		return allocationService.fetchByEmpId(id);
	}
}
