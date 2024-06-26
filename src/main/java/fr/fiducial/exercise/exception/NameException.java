package fr.fiducial.exercise.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = BAD_REQUEST)
public class NameException extends Exception {

	private static final long serialVersionUID = 1L;

	public NameException(String message) {
		super(message);
	}

}
