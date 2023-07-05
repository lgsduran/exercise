package fr.fiducial.exercise.service;

import static java.lang.String.format;
import static java.time.Instant.now;
import static org.apache.commons.lang3.StringUtils.isAllBlank;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;
import fr.fiducial.exercise.utils.ConvertUtils;
import fr.fiducial.exercise.utils.PredicateUtils;

@Service
public class NamesServiceImpl implements INamesService {

	private PredicateUtils pUtils = new PredicateUtils();
	private ConvertUtils convertUtils = new ConvertUtils();
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
	public NamesDto save(Names name) throws DuplicatedNameException {
		var duplicationResult = this.namesRepository.findAll().stream()
				.anyMatch(pUtils.compareNamePredicate(name.getName()));

		if (duplicationResult)
			throw new DuplicatedNameException(format("Name %s is duplicated", name.getName()));

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
		String nameStr = name.toLowerCase();
		this.namesRepository.findAll().stream().anyMatch(pUtils.compareNamePredicate(nameStr));
		var duplicationResult = this.namesRepository.findAll().stream()
				.anyMatch(pUtils.compareNamePredicate(nameStr));

		if (duplicationResult)
			return true;

		return false;
	}

	/**
	 * @see Method deletes item by its id.
	 * @param name
	 * @throws NameException
	 */
	@Override
	@Transactional
	public void deleteByName(String name) throws NameException {
		String nameStr = name.toLowerCase();
		var isItemFound = this.namesRepository.findByName(nameStr);

		if (isItemFound == null)
			throw new NameException(format("Name %s was not found.", nameStr));

		this.namesRepository.deleteById(isItemFound.getId());
	}


	/**
	 * @see Method persists array of names
	 * @param names
	 * @return List<NamesDto>
	 * @throws NameException, DuplicatedNameException 
	 */

	@Override
	public List<NamesDto> saveAll(ArrayList<Names> names) throws NameException, NameException, DuplicatedNameException {
		if(names.stream().allMatch(x -> isAllBlank(x.getName())))
			throw new NameException("no empty value accepted");
		
		// Retrieve name from data source 
		var findAll = convertUtils.convertToList(this.namesRepository.findAll(), Names::getName);
		var registers = convertUtils.convertToList(findAll, String::toLowerCase);
		
		// Retrieve data from parameters
		var sourceTemp = convertUtils.convertToList(names, Names::getName);
		var source = convertUtils.convertToList(sourceTemp, String::toLowerCase);
		
		// Find duplicated items
		var duplicatedNames = pUtils.getElements(registers, r -> source.contains(r));
		
		// Condition to throw customized exception if both size are equals
		if (source.size() == duplicatedNames.size())
			throw new DuplicatedNameException("Duplicated");
		
		// list of objects
		var uniqueTemp = convertUtils.convertToList(source, n -> new Names(n));
		var uniqueNamesTemp = convertUtils.convertToList(uniqueTemp, n -> n.getName());

		// Get the non-duplicated parameters
		uniqueNamesTemp.removeAll(registers);
		
		// change to the object again
		var uniqueNames = convertUtils.convertToList(uniqueNamesTemp, u -> new Names(u, now()));

		// Condition to throw customized exception if uniqueName is empty
		if (uniqueNames.isEmpty())
			throw new NameException("Deu ruim de novo!!!");
		
		this.namesRepository.saveAll(uniqueNames);		
		
		// Return dto		
		return convertUtils.convertToList(uniqueNames, u -> new NamesDto(u.getId(), u.getName(), u.getCreatedAt()));
	}
}
