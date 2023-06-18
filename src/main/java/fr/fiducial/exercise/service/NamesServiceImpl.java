package fr.fiducial.exercise.service;

import static java.lang.String.format;
import static java.time.Instant.now;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@Service
public class NamesServiceImpl implements INamesService {
	
	private NamesRepository namesRepository;	

	/**
	 * @param namesRepository	 
	 */
	public NamesServiceImpl(NamesRepository namesRepository) {
		this.namesRepository = namesRepository;
	}
	
	@Override
	public List<NamesDto> saveAll(List<Names> names) throws NameException {
		names.forEach(x -> x.setCreatedAt(now()));
		this.namesRepository.saveAll(names);
		return null;
	}	

	/**
	 * @see Before saving a single name, it checks whether or not it is duplicated.
	 * @param dto
	 * @exception NameException
	 */
	@Override
	public NamesDto save(Names name) throws NameException {		
		var duplicationResult = this.namesRepository
				.findAll()
				.stream()
				.anyMatch(getNamePredicate(name.getName()));
		
		if (duplicationResult)
			throw new NameException(format("Name %s is duplicated", name.getName()));
		
		name.setCreatedAt(now());		
		this.namesRepository.save(name);
		
		var dto = new NamesDto(name.getId(), name.getName(), name.getCreatedAt());
		return dto;
	}
	
	/**
	 * @see Method retrives all names from database.
	 */

	@Override
	public Page<Names> listNames(Pageable pageable) {
		return this.namesRepository.findAll(pageable);
	}

	/**
	 * @see Method searches for a name.
	 * @param name
	 * @return true or false if exists or not.
	 */
	@Override
	public Boolean nameExists(String name) {
		var duplicationResult = this.namesRepository
				.findAll()
				.stream()
				.anyMatch(getNamePredicate(name));
		
		if (duplicationResult)
			return true;

		return false;
	}
	

	/**
	 * @see Method deletes item by name.
	 * @param name
	 */
	@Override
	public void deleteName(String name) throws NameException {
		var isItemFound = this.namesRepository.findByName(name);
		
		if (isItemFound == null)
			throw new NameException(format("Name %s was not found.", name));			      

		this.namesRepository.deleteByName(name);
	}
	
	/**
	 * @see Method tests if two arguments are equals 
	 * @param strName
	 * @return predicate
	 */	
	private Predicate<Names> getNamePredicate(String strName) {
	    return x -> x.getName().equalsIgnoreCase(strName);
	}
}
