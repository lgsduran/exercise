package fr.fiducial.exercise.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.service.NamesServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/names")
public class NameController {
	

	private final NamesServiceImpl namesServiceImpl;

	/**
	 * @see Constructor for dependency injection
	 * @param namesServiceImpl
	 */
	NameController(NamesServiceImpl namesServiceImpl) {
		this.namesServiceImpl = namesServiceImpl;
	}

	@PostMapping("/addNames")
	@ResponseStatus(CREATED)
	List<NamesDto> saveAll(@Valid @RequestBody ArrayList<Names> names) throws NameException {
		return this.namesServiceImpl.saveAll(names);	
	}
	
	@PostMapping("/addName")
	@ResponseStatus(CREATED)
	NamesDto addName(@Valid @RequestBody Names name) throws NameException {	
		return this.namesServiceImpl.save(name);
	}
	
	@GetMapping("/")
	Page<Names> listNames(@Parameter(hidden = true) Pageable pageable) {
		return this.namesServiceImpl.listNames(pageable);
		
	}
	
	@GetMapping(path = "/{name}")
	Boolean nameExists(@PathVariable String name) {
		return this.namesServiceImpl.nameExists(name);
	}
	
	@DeleteMapping(path = "/{name}")
	@Transactional
	void deleteName(@PathVariable String name) throws NameException {
		this.namesServiceImpl.deleteName(name);
	}

}
