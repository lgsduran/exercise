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
		var isItemFound = this.namesRepository.findByName(name);

		if (isItemFound == null)
			throw new NameException(format("Name %s was not found.", name));

		this.namesRepository.deleteByName(name);
	}



	@Override
	public List<NamesDto> saveAll(ArrayList<Names> names) throws NameException {
		var registers = this.namesRepository.findAll().stream()
				.map(Names::getName)
				.map(String::toLowerCase)
				.collect(toList());
		
		var nameTemp = names.stream()
				.map(Names::getName)
				.map(String::toLowerCase)
				.collect(toList());
		
		List<String> duplicatedNames = registers.stream()
				.filter(r -> nameTemp.contains(r))
				.collect(toList());
		
		if (nameTemp.size() == duplicatedNames.size())
			throw new NameException("Deu ruim!!!");
		
		var uniqueNamesTemp = nameTemp.stream()
				.map(n -> new Names(n))
				.map(n -> n.getName())
				.map(String::toLowerCase)
				.collect(toList());

		uniqueNamesTemp.removeAll(registers);
		
		var uniqueNames = uniqueNamesTemp.stream()
				.map(u -> new Names(u, now()))
				.collect(toList());		

		if (uniqueNamesTemp.isEmpty())
			throw new NameException("Deu ruim de novo!!!");
		
		this.namesRepository.saveAll(uniqueNames);		
		
		return uniqueNames.stream()
				.map(u -> new NamesDto(u.getId(), u.getName(), u.getCreatedAt()))
				.collect(toList());
	}
}
