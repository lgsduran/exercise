package fr.fiducial.exercise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;

public interface INamesService {
	
	List<NamesDto> saveAll(List<Names> names) throws NameException;
	
	void saveAll(ArrayList<String> names) throws NameException;
	
	NamesDto save(Names name) throws NameException;
	
	Page<Names> listNames(Pageable pageable);
	
	Boolean nameExists(String name);
	
	void deleteName(String name) throws NameException;

}
