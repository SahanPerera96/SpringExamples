package com.sahan.demo.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.sahan.demo.exception.MessageBodyConstraintViolationException;
import com.sahan.demo.modal.*;
import com.sahan.demo.repository.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sahan.demo.hystrix.AllocationCommand;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TelephoneRepository telephoneRepository;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EmployeeJDBC repository;

	@Autowired
	ImageRepository imageRepository;
	
	@Override
	public Employee save(Employee employee) {
		
		for(Telephone telephone:employee.getTelephones()) {
			telephone.setEmployee(employee);
		}
		
		return employeeRepository.save(employee);
	}
	@Override
	public List<Employee> fetchAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@Override
//	@Hystrix
//	@HystrixCommand(fallbackMethod = "fetchEmployeeFallBack")
	public Employee fetchEmployee(Employee employee, HttpHeaders httpHeaders) {
		Optional<Employee> optional = employeeRepository.findById(employee.getId());
				
		if(optional.isPresent()) {
//			Allocation[] allocation = fetchEmployeeBack(optional.get());
////			RestTemplate restTemplate = new RestTemplate();
//			HttpHeaders httpHeaders = new HttpHeaders();
//			
//			OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//					SecurityContextHolder.getContext().getAuthentication().getDetails();
//			httpHeaders.add("Authorization","bearer".concat(details.getTokenValue()));
//			
//			ResponseEntity<Allocation[]>responseEntity;
//			HttpEntity<String>entity = new HttpEntity<>("",httpHeaders);
//			responseEntity = restTemplate.exchange("http://allocation-service/allocate/details/"
//					.concat(String.valueOf(employee.getId())),HttpMethod.GET,entity,Allocation[].class);
			Employee employee2 = optional.get();
			AllocationCommand allocationCommand = new AllocationCommand(employee, httpHeaders, restTemplate);
			Allocation[] allocations = allocationCommand.execute();
			employee2.setAllocation(allocations);
//			employee2.setAllocation(allocation);
			
			return employee2;
			
		}else {
			return null;
		}
	}



	@HystrixCommand(fallbackMethod = "fetchEmployeeFallBack")
	public Allocation[] fetchEmployeeBack(Employee employee) {
//		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		
//		OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//				SecurityContextHolder.getContext().getAuthentication().getDetails();
//		httpHeaders.add("Authorization","bearer ".concat(details.getTokenValue()));
//		System.out.println(details.getTokenValue());
		ResponseEntity<Allocation[]>responseEntity;
		HttpEntity<String>entity = new HttpEntity<>("",httpHeaders);
		responseEntity = restTemplate.exchange("http://allocation-service/allocate/details/"
				.concat(String.valueOf(employee.getId())),HttpMethod.GET,entity,Allocation[].class);
		
		return responseEntity.getBody();
		
	}
	public Allocation[] fetchEmployeeFallBack(Employee employee) {
		return new Allocation[1];
	}


	@Override
	public Object runAnyQuery(QueryGenerator generator) {

		return repository.findAll(generator);
	}
	
	@Override
	public ResponseEntity<?> delete(Integer id) {
		
		boolean isExist = employeeRepository.existsById(id);
		if (isExist) {
			System.out.println("have");
			employeeRepository.deleteById(id);

			
			return new ResponseEntity<>("Succesfully deleted",HttpStatus.ACCEPTED);

		} else {
			return new ResponseEntity<>("Id not available deleted",HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> deleteTelephones(Integer id) {

		boolean isExist = employeeRepository.existsById(id);
		if (isExist) {
			System.out.println("have");
			Optional<Employee> employee = employeeRepository.findById(id);
			Employee employee1= employee.get();


			telephoneRepository.deleteInBatch(employee1.getTelephones());
			return new ResponseEntity<>("Succesfully deleted",HttpStatus.ACCEPTED);

		} else {
			return new ResponseEntity<>("Id not available deleted",HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> saveImage(String string) {
		System.out.println("THis is : "+string);
//		String base64Image = string.split(",")[1];
//		System.out.println("THis is : "+base64Image);

		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(string);
		try {
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
			//convert image to byte[]
			System.out.println("dsfdhfjshfks");
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage , "png" , output);
			byte[] img = output.toByteArray();
			System.out.println(img);
			Blob blob = new SerialBlob(img);
			DocumentTable documentTable = new DocumentTable();
			documentTable.setFilename("firstImage");
			documentTable.setContent(img);

			return new ResponseEntity<>(imageRepository.save(documentTable),HttpStatus.ACCEPTED);


		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<?> fetchImageById(Integer id) {
		boolean isExist = imageRepository.existsById(id);
		if (isExist) {
			Optional<DocumentTable> optional = imageRepository.findById(id);
			if (optional.isPresent()) {
//				String resultBase64Encoded = Base64.getEncoder().encodeToString(optional.get().getContent().getBytes("utf-8"));

				byte[] decodedBytes = optional.get().getContent();
				try {
					FileUtils.writeByteArrayToFile(new File("test_image_copy.png"), decodedBytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(optional.get(),HttpStatus.ACCEPTED);
			}else {
				throw new MessageBodyConstraintViolationException("Image is not available.");
			}

		} else {
			throw new MessageBodyConstraintViolationException("Image entry not available.");
		}
	}

}
