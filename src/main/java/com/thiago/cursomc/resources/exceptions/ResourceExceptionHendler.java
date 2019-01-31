package com.thiago.cursomc.resources.exceptions;

import com.thiago.cursomc.services.exceptions.DataIntegrityException;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHendler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){

		StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(),
				e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
				"Erro de validação", System.currentTimeMillis());

		e.getBindingResult().getFieldErrors().forEach(fieldError ->
				err.addError(fieldError.getField(), fieldError.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
