package fr.fiducial.exercise.service;

import static java.lang.String.format;
import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;
import fr.fiducial.exercise.utils.PredicateUtils;

@Service
public class NamesServiceImpl implements INamesService {

	private PredicateUtils pUtils = new PredicateUtils();
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
	public NamesDto save(Names name) throws NameException {
		var duplicationResult = this.namesRepository.findAll().stream()
				.anyMatch(pUtils.compareNamePredicate(name.getName()));

		if (duplicationResult)
			throw new NameException(format("Name %s is duplicated", name.getName()));

		name.setName(name.getName().toLowerCase());
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
		this.namesRepository.findAll().stream().anyMatch(pUtils.compareNamePredicate(name));
		var duplicationResult = this.namesRepository.findAll().stream()
				.anyMatch(pUtils.compareNamePredicate(name));

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
		String nameStr = name.toLowerCase();
		var isItemFound = this.namesRepository.findByName(nameStr);

		if (isItemFound == null)
			throw new NameException(format("Name %s was not found.", nameStr));

		this.namesRepository.deleteByName(nameStr);
	}


	/**
	 * @see Method persists array of names
	 * @param names
	 * @return
	 */

	@Override
	public List<NamesDto> saveAll(ArrayList<Names> names) throws NameException {
		// Retrieve name from data source 
		var registers = this.namesRepository.findAll().stream()
				.map(Names::getName)
				.map(String::toLowerCase)
				.collect(toList());
		
		// Retrieve data from parameters
		var nameTemp = names.stream()
				.map(Names::getName)
				.map(String::toLowerCase)
				.collect(toList());
		
		// Find duplicated items
		var duplicatedNames = registers.stream()
				.filter(r -> nameTemp.contains(r))
				.collect(toList());
		
		// Condition to throw customized exception if both size are equals
		if (nameTemp.size() == duplicatedNames.size())
			throw new NameException("Deu ruim!!!");
		
		// list of objects
		var uniqueNamesTemp = nameTemp.stream()
				.map(n -> new Names(n))
				.map(n -> n.getName())
				//.map(String::toLowerCase)
				.collect(toList());

		// Get the non-duplicated parameters
		uniqueNamesTemp.removeAll(registers);
		
		// Change from set to list
		var uniqueNames = uniqueNamesTemp.stream()
				.map(u -> new Names(u, now()))
				.collect(toList());		

		// Condition to throw customized exception if uniqueName is empty
		if (uniqueNames.isEmpty())
			throw new NameException("Deu ruim de novo!!!");
		
		this.namesRepository.saveAll(uniqueNames);		
		
		// Return dto
		return uniqueNames.stream()
				.map(u -> new NamesDto(u.getId(), u.getName(), u.getCreatedAt()))
				.collect(toList());
	}
}
