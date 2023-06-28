package fr.fiducial.exercise.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = BAD_REQUEST)
public class DuplicatedNameException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedNameException(String message) {
		super(message);
	}	

}
