package com.evictory.ecloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evictory.ecloud.modal.Allocation;
import com.evictory.ecloud.repository.AllocationRepository;

@Service
public class AllocationServiceImpl implements AllocationService {

	@Autowired
	AllocationRepository allocationRepository;

	@Override
	public Allocation save(Allocation allocation) {

		return allocationRepository.save(allocation);
	}

	@Override
	public List<Allocation> fetchByEmpId(Integer id) {
//		List<Allocation> allocations = allocationRepository.findAll();
//		List<Allocation> allocations2 = new ArrayList<Allocation>();
//		for (int i = 0; i < allocations.size(); i++) {
//			if (allocations.get(i).getEmpid() == id) {
//				Optional<Allocation> optional = allocationRepository.findById(allocations.get(i).getId());
//
//				if (optional.isPresent()) {
//					allocations2.add(optional.get());
//
//				}
//			}
//		}
//		return allocations2;
		return allocationRepository.findByEmpid(id);

	}

	@Override
	public List<Allocation> fetchAll() {
		return allocationRepository.findAll();
	}
}
