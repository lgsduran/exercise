package fr.fiducial.exercise.service;

import static java.lang.String.format;

import java.util.function.Predicate;

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
	

	/**
	 * @see Before saving a single name, it checks whether or not it is duplicated.
	 * @param dto
	 * @exception NameException
	 */
	@Override
	public void save(Names name) throws NameException {		
		var duplicationResult = this.namesRepository
				.findAll()
				.stream()
				.findFirst()
				.filter(isNameDuplicated(name.getName()));
		
		if (duplicationResult.isPresent())
			throw new NameException(format("Name %s duplicated", name.getName()));
		
		this.namesRepository.save(name);		
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
		var strName = this.namesRepository.getByName(name);
		var duplicationResult = this.namesRepository
								.findAll()
								.stream()
								.findFirst()
								.filter(isNameDuplicated(strName));
		
		if (duplicationResult.isPresent())
			return true;

		return false;
	}
	
	/**
	 * @see Method tests if two arguments are equals 
	 * @param strName
	 * @return predicate
	 */	
	private Predicate<Names> isNameDuplicated(String strName) {
	    return x -> x.getName().equalsIgnoreCase(strName);
	}

}
