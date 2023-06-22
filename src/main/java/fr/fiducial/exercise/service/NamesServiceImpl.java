package fr.fiducial.exercise.service;

import static java.lang.String.format;
import static java.time.Instant.now;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
		var duplicationResult = this.namesRepository.findAll().stream().anyMatch(getNamePredicate(name.getName()));

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
		this.namesRepository.findAll().stream().anyMatch(getNamePredicate(name));
		var duplicationResult = this.namesRepository.findAll().stream().anyMatch(getNamePredicate(name));

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
	

	@Override
	public void saveAll(ArrayList<String> names) throws NameException {
		var registers = this.namesRepository.findAll().stream()
				.map(Names::getName)
				.map(String::toLowerCase)
				.collect(Collectors.toList());
		
		var nameTemp = names.stream()
				.map(String::toLowerCase)
				.collect(Collectors.toList());
		
		List<String> duplicatedNames = registers.stream()
				.filter(r -> nameTemp.contains(r))
				.collect(Collectors.toList());
		
		if (nameTemp.size() == duplicatedNames.size())
			throw new NameException("Deu ruim!!!");
		
		var uniqueNamesTemp = nameTemp.stream()
				.map(n -> new Names(n))
				.map(n -> n.getName())
				.map(String::toLowerCase)
				.collect(Collectors.toList());

		uniqueNamesTemp.removeAll(registers);
		
		var uniqueNames = uniqueNamesTemp.stream()
				.map(u -> new Names(u, now()))
				.collect(Collectors.toList());		

		if (!uniqueNamesTemp.isEmpty())
			this.namesRepository.saveAll(uniqueNames);

//		if (!duplicatedNames.isEmpty())
//			throw new NameException(format("Duplicated: %s", String.join(",", duplicatedNames.stream().toString())));
	}
}
