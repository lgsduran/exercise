package fr.fiducial.exercise.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import fr.fiducial.exercise.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NameException.class)
	public ResponseEntity<?> handleNameException(NameException ex) {
		ResponseDto response = new ResponseDto("400", ex.getMessage());
		return new ResponseEntity<>(response, BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicatedNameException.class)
	public ResponseEntity<?> DuplicatedNameException(DuplicatedNameException ex) {
		ResponseDto response = new ResponseDto("400", ex.getMessage());
		return new ResponseEntity<>(response, BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobleExcpetionHandler(Exception ex) {
		ResponseDto response = new ResponseDto("500", ex.getMessage());
		return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
	}

}
