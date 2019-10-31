package com.sahan.demo.exception;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.sahan.demo.modal.ResponseMessages;
import com.sahan.demo.modal.ResponseValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @Autowired
    ExceptionResponse exceptionResponse;

    @Autowired
    ResponseValues responseValues;

    @Autowired
    ResponseMessages responseMessages;

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> serviceExceptionHandler(JsonMappingException e){

        responseValues.setStatus(responseMessages.getResponseFailed());
        responseValues.setMessage("Invalid Json body passed");
        responseValues.setCode("#1200003");
        return new ResponseEntity<>(responseValues,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MessageBodyConstraintViolationException.class)
    public ResponseEntity<?> serviceException(MessageBodyConstraintViolationException e){
        responseValues.setStatus(responseMessages.getResponseFailed());
        responseValues.setMessage(e.getMessage());
        responseValues.setCode("#1200003");
        return new ResponseEntity<>(responseValues,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> serviceExceptionFromModel(MethodArgumentNotValidException ex){
    	String message = "Please provide ";
		
		for (int i = 0; i < ex.getBindingResult().getAllErrors().size(); i++) {
			
			if(i == 0) {
				message = message + ex.getBindingResult().getAllErrors().get(i).getDefaultMessage();
			}else {
				message = message +" & " + ex.getBindingResult().getAllErrors().get(i).getDefaultMessage() ;
			}
		}

        responseValues.setMessage(message);
        responseValues.setStatus(responseMessages.getResponseFailed());
        responseValues.setCode("#1200003");
        return new ResponseEntity<>(responseValues,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
