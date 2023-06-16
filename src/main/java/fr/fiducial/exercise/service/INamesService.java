package fr.fiducial.exercise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;

public interface INamesService {
	
	void save(Names name) throws NameException;
	
	Page<Names> listNames(Pageable pageable);
	
	Boolean nameExists(String name);

}
