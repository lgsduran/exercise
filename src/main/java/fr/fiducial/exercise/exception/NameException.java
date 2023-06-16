package fr.fiducial.exercise.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = UNPROCESSABLE_ENTITY)
public class NameException extends Exception {

	private static final long serialVersionUID = 1L;

	public NameException(String message) {
		super(message);
	}

}
