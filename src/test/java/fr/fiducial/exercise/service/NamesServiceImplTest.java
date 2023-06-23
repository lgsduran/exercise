package fr.fiducial.exercise.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;


@SpringBootTest
class NamesServiceImplTest {
	
	private NamesServiceImpl namesServiceImpl;
	
	@Autowired
	private NamesRepository namesRepository;
	
	private ArrayList<Names> places;

	@BeforeEach
	void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);

		places = new ArrayList<Names>();
		places.add(new Names("Ola10", Instant.now()));
		places.add(new Names("Ola11", Instant.now()));

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testNamesServiceImpl() {
		assertNotNull(namesServiceImpl);
	}

	@Test
	void testSave() {
		fail("Not yet implemented");
	}

	@Test
	void testListNames() {
		Page<Names> listNames = namesServiceImpl.listNames(null);
		assertTrue(!listNames.isEmpty());
	}

	@Test
	void testNameExists() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteName() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveAll() throws NameException {
		List<NamesDto> oneDtos = namesServiceImpl.saveAll(places);
		boolean getId = oneDtos.stream().allMatch(x -> !Objects.isNull(x.getId()));
		boolean getName = oneDtos.stream().allMatch(x -> x.getName().length() > 0);
		boolean getCreatedAt = oneDtos.stream().allMatch(x -> x.getCreated_At() != null);
		assertTrue(getId, "Id");
		assertTrue(getName, "Name");
		assertTrue(getCreatedAt, "TimeStamp");
	}

}
