package fr.fiducial.exercise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;

public interface INamesService {
	
	List<NamesDto> saveAll(ArrayList<Names> names) throws DuplicatedNameException, NameException;
	
	NamesDto save(Names name) throws DuplicatedNameException;
	
	Page<Names> listNames(Pageable pageable);
	
	Boolean nameExists(String name);
	
	void deleteByName(String name) throws NameException;

}
