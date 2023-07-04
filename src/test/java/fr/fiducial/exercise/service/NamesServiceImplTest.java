package fr.fiducial.exercise.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.Objects;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;
import fr.fiducial.exercise.utils.ConvertUtils;

@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class NamesServiceImplTest {

	@Autowired
	private NamesRepository namesRepository;

	private NamesServiceImpl namesServiceImpl;
	private ConvertUtils utils;

	private Names nameTest;
	
	private String[] arrNames = {"Jacob","Michael","Matthew","Joshua",
			"Christopher","Nicholas","Andrew","Joseph","Daniel",
			"Tyler","William","Brandon","Ryan","John","Zachary",
			"David","Anthony","James","Justin","Alexander","Jonathan"};

	@BeforeAll
	void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
		utils = new ConvertUtils();
		nameTest = new Names("usertest");
		
	}

	@Test
	@Order(1)
	void testNamesServiceImpl() {
		assertNotNull(namesServiceImpl);
	}

	@Test
	@Order(2)
	void testSave() throws DuplicatedNameException {
		var result = namesServiceImpl.save(nameTest);
		assertNotNull(result.getId(), "Id");
	}

	@Test
	@Order(3)
	void testListNames() {
		Page<Names> listNames = namesServiceImpl.listNames(of(0, 3, by("name")));
		assertTrue(!listNames.isEmpty());
	}

	@Test
	@Order(5)  
	void testNameExists() {
		Boolean nameExists = namesServiceImpl.nameExists(nameTest.getName());
		assertTrue(nameExists);
	}

	@Test
	@Order(4)
	void testSaveAll() throws NameException, DuplicatedNameException {
		var arr = utils.fromArrayToList(arrNames);
		var fromArrayToObj = utils.fromArrayToObj(arr);
		var dtos = namesServiceImpl.saveAll(fromArrayToObj);
		boolean getId = dtos.stream().allMatch(x -> !Objects.isNull(x.getId()));
		boolean getName = dtos.stream().allMatch(x -> x.getName().length() > 0);
		boolean getCreatedAt = dtos.stream().allMatch(x -> x.getCreated_At() != null);
		assertAll(
				() -> assertTrue(getId, "Id"), 
				() -> assertTrue(getName, "Name"),
				() -> assertTrue(getCreatedAt, "TimeStamp")
		);		
	}
	
	@AfterAll
	void tearDown() throws Exception {
		namesServiceImpl.deleteByName(nameTest.getName());
		utils.fromArrayToList(arrNames)
			.forEach(x -> {				
				Names byName = namesRepository.findByName(x);
				namesRepository.deleteById(byName.getId());
			});
	}
}
