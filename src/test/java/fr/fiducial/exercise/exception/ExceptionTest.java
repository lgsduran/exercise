package fr.fiducial.exercise.exception;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.repository.NamesRepository;
import fr.fiducial.exercise.service.NamesServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class ExceptionTest {

	@Autowired
	private NamesRepository namesRepository;

	private NamesServiceImpl namesServiceImpl;

	String[] arrNames = {"Jacob","Michael"};

	@BeforeEach
	public void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
	}

	@Test
	@Order(1)
	public void testSaveException() throws DuplicatedNameException {
		//namesServiceImpl.save(nameTest);
		assertThrows(DuplicatedNameException.class, 
				() -> namesServiceImpl.save(new Names(arrNames[0])));
	}
	
	@Test
	@Order(2)
	public void testSaveAllException() throws DuplicatedNameException {
		//namesServiceImpl.save(nameTest);
		var list = of(arrNames)
				.collect(toCollection(ArrayList::new));
		var collect = list.stream().map(u -> new Names(u, now()))
				.collect(toCollection(ArrayList::new));
		assertThrows(DuplicatedNameException.class, 
				() -> namesServiceImpl.saveAll(collect));
	}
	
	@Test
	@Order(3)  
	public void testNameNotExists() {
		Boolean nameExists = namesServiceImpl.nameExists("Ishigo");
		assertFalse(nameExists);
	}	

}
