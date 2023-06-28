package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
import org.springframework.transaction.annotation.Transactional;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@Transactional
class NamesServiceImplTest {

	@Autowired
	private NamesRepository namesRepository;

	private ArrayList<Names> collect = null;
	private Names nameTest = new Names("userTest5");
	private NamesServiceImpl namesServiceImpl;
	String[] strArr = { "Ani16", "Sam17", " Joe18" };

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
	public void testSave() throws Exception {
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
	@Order(6)  
	public void testNameExists() {
		Boolean nameExists = namesServiceImpl.nameExists(nameTest.getName());
		assertFalse(nameExists);
	}

	@Test
	@Order(4)
	public void testDeleteName() {
		try {
			namesServiceImpl.deleteByName(nameTest.getName());
		} catch (Exception e) {
			fail("Not yet implemented");
		}		
	}

	@Test
	@Order(5)
	public void testSaveAll() throws NameException, DuplicatedNameException {
		var list = of(strArr).collect(toCollection(ArrayList::new));
		collect = list.stream().map(u -> new Names(u, now())).collect(toCollection(ArrayList::new));
		List<NamesDto> oneDtos = namesServiceImpl.saveAll(collect);
		boolean getId = oneDtos.stream().allMatch(x -> !Objects.isNull(x.getId()));
		boolean getName = oneDtos.stream().allMatch(x -> x.getName().length() > 0);
		boolean getCreatedAt = oneDtos.stream().allMatch(x -> x.getCreated_At() != null);
		assertAll(
				() -> assertTrue(getId, "Id"), 
				() -> assertTrue(getName, "Name"),
				() -> assertTrue(getCreatedAt, "TimeStamp")
		);		
	}

}
