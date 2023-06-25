package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@SpringBootTest
class NamesServiceImplTest {

	@Autowired
	private NamesRepository namesRepository;

	private ArrayList<Names> collect = null;
	private Names nameTest = new Names("userTest4");
	private NamesServiceImpl namesServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
		String[] strArr = { "Ani10", "Sam11", " Joe12" };
		var list = of(strArr).collect(toCollection(ArrayList::new));
		collect = list.stream().map(u -> new Names(u, now())).collect(toCollection(ArrayList::new));
	}

	@Test
	@Order(1)
	void testNamesServiceImpl() {
		assertNotNull(namesServiceImpl);
	}

	@Test
	@Order(3)
	void testSave() throws Exception {
		var result = namesServiceImpl.save(nameTest);
		assertTrue(!Objects.isNull(result.getId()), "Id");
	}

	@Test
	@Order(4)
	void testListNames() {
		Page<Names> listNames = namesServiceImpl.listNames(of(0, 3, by("name")));
		assertTrue(!listNames.isEmpty());
	}

//	@Test
//	@Order(2)  
//	void testNameExists() {
//		Boolean nameExists = namesServiceImpl.nameExists(nameTest.getName());
//		assertTrue(nameExists);
//	}

	@Test
	@Transactional
	void testDeleteName() {
		try {
			namesRepository.deleteById((long) 53);
			//namesServiceImpl.deleteName(nameTest.getName());
		} catch (Exception e) {
			fail("Not yet implemented");
		}		
	}

	@Test
	@Order(5)
	void testSaveAll() throws NameException {
		List<NamesDto> oneDtos = namesServiceImpl.saveAll(collect);
		boolean getId = oneDtos.stream().allMatch(x -> !Objects.isNull(x.getId()));
		boolean getName = oneDtos.stream().allMatch(x -> x.getName().length() > 0);
		boolean getCreatedAt = oneDtos.stream().allMatch(x -> x.getCreated_At() != null);
		assertTrue(getId, "Id");
		assertTrue(getName, "Name");
		assertTrue(getCreatedAt, "TimeStamp");
	}

}
