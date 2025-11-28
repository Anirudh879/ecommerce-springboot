package com.codeWithProject.ecom.exception;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.codeWithProject.ecom.helper.ApiResponse;
import com.codeWithProject.ecom.helper.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
		
	
	 // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,HttpServletRequest request) {
    	
    	ErrorResponse response = ErrorResponse
    									.builder()
    									.success(false)
    									.errorCode("RESOURCE_NOT_FOUND")
    									.message("Resource not found")
    									.details(ex.getMessage())
    									.path(request.getRequestURI())
    									.build();	
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request){
    	
    	List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(err->err.getField()+": "+err.getDefaultMessage())
    							.collect(Collectors.toList());
    	
    	ErrorResponse response = ErrorResponse.builder()
    										.success(false)
    										.errorCode("VALIDATION_FAILED")
    										.message("Method argument validation error")
    										.details(errors.toString())
    										.path(request.getDescription(false).replace("uri=", ""))
    										.build();
    	
    	
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request){
    	ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .errorCode("INTERNAL_SERVER_ERROR")
                .message("Something went wrong")
                .details(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    	return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    } 
    
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException ex,HttpServletRequest request){
    	ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .errorCode("RESOURCE_ALREADY_EXISTS")
                .message("Resource already exists")
                .details(ex.getMessage())
                .path(request.getRequestURI())
                .build();

    	return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<ErrorResponse> handleFileValidation(FileValidationException ex, HttpServletRequest request){
    	ErrorResponse response = ErrorResponse.builder()
    							.success(false)
    							.errorCode("FILE_VALIDATION_ERROR")
    							.message("File validation error")
    							.details(ex.getMessage())
    							.path(request.getRequestURI())
    			                .build();
    	return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
    
    
	
}
