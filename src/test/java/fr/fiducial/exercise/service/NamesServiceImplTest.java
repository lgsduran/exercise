package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class NamesServiceImplTest {

	@Autowired
	private NamesRepository namesRepository;

	private NamesServiceImpl namesServiceImpl;

	private Names nameTest = new Names("usertest");
	String[] arrNames = {"Jacob","Michael","Matthew","Joshua","Christopher","Nicholas","Andrew","Joseph","Daniel","Tyler","William","Brandon","Ryan","John","Zachary","David","Anthony","James","Justin","Alexander","Jonathan"};

	@BeforeEach
	public void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
	}

	@Test
	@Order(1)
	public void testNamesServiceImpl() {
		assertNotNull(namesServiceImpl);
	}

	@Test
	@Order(2)
	public void testSave() throws DuplicatedNameException {
		var result = namesServiceImpl.save(nameTest);
		assertNotNull(result.getId(), "Id");
	}

	@Test
	@Order(3)
	public void testListNames() {
		Page<Names> listNames = namesServiceImpl.listNames(of(0, 3, by("name")));
		assertTrue(!listNames.isEmpty());
	}

	@Test
	@Order(5)  
	public void testNameExists() {
		Boolean nameExists = namesServiceImpl.nameExists(nameTest.getName());
		assertTrue(nameExists);
	}

	@Test
	@Order(4)
	public void testSaveAll() throws NameException, DuplicatedNameException {
		var list = of(arrNames)
				.collect(toCollection(ArrayList::new));
		var collect = list.stream().map(u -> new Names(u, now()))
				.collect(toCollection(ArrayList::new));
		List<NamesDto> dtos = namesServiceImpl.saveAll(collect);
		boolean getId = dtos.stream().allMatch(x -> !Objects.isNull(x.getId()));
		boolean getName = dtos.stream().allMatch(x -> x.getName().length() > 0);
		boolean getCreatedAt = dtos.stream().allMatch(x -> x.getCreated_At() != null);
		assertAll(
				() -> assertTrue(getId, "Id"), 
				() -> assertTrue(getName, "Name"),
				() -> assertTrue(getCreatedAt, "TimeStamp")
		);		
	}

}
