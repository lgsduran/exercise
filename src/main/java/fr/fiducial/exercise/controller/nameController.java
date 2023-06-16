package fr.fiducial.exercise.controller;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.service.NamesServiceImpl;

@RestController
@RequestMapping("/api/names")
public class nameController {
	
	@Autowired
	private NamesServiceImpl namesServiceImpl;	

	/**
	 * 
	 */
	public nameController() {
	}

//	/**
//	 * @param namesServiceImpl
//	 */
//	public nameController(NamesServiceImpl namesServiceImpl) {
//		this.namesServiceImpl = namesServiceImpl;
//	}
	
	@PostMapping("/addName")
	@ResponseStatus(CREATED)
	void addName(@Valid @RequestBody Names name) throws NameException {	
		this.namesServiceImpl.save(name);
	}
	
	@GetMapping("/names")
	Page<Names> listNames(@Valid  @ParameterObject @PageableDefault Pageable pageable) {
		return this.namesServiceImpl.listNames(pageable);
		
	}
	
	@GetMapping(path = "/{name}")
	Boolean nameExists(@PathVariable String name) {
		return this.namesServiceImpl.nameExists(name);
	}

}
