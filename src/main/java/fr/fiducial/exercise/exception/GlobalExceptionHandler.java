package fr.fiducial.exercise.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import fr.fiducial.exercise.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NameException.class)
	public ResponseEntity<?> handleBusinessException(NameException ex) {
		ResponseDto response = new ResponseDto("422", ex.getMessage());
		return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobleExcpetionHandler(Exception ex) {
		if (ex.getMessage().contains("br.com.alura.budgetManagement.enums")) {
			ResponseDto response = new ResponseDto("404", "Invalid input.");
			return new ResponseEntity<>(response, BAD_REQUEST);
		}

		ResponseDto response = new ResponseDto("500", ex.getMessage());
		return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
	}

}
