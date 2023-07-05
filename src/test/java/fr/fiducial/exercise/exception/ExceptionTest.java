package fr.fiducial.exercise.exception;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.repository.NamesRepository;
import fr.fiducial.exercise.service.NamesServiceImpl;
import fr.fiducial.exercise.utils.ConvertUtils;

@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class ExceptionTest {

	@Autowired
	private NamesRepository namesRepository;

	private NamesServiceImpl namesServiceImpl;
	private ConvertUtils utils;	

	String[] arrNames = {"Jacob","Michael"};

	@BeforeAll
	void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
		utils = new ConvertUtils();
		namesServiceImpl.save(new Names(arrNames[0]));
		namesServiceImpl.save(new Names(arrNames[1]));
	}

	@Test
	@Order(1)
	void testSaveException() throws DuplicatedNameException {		
		assertThrows(DuplicatedNameException.class, 
				() -> namesServiceImpl.save(new Names(arrNames[0])));
	}
	
	@Test
	@Order(2)
	void testSaveAllDuplicatedNameException() throws DuplicatedNameException {
		//namesServiceImpl.save(nameTest);
		var arr = utils.fromArrayToList(arrNames);
		var fromArrayToObj = utils.fromArrayToObj(arr);
		assertThrows(DuplicatedNameException.class, 
				() -> namesServiceImpl.saveAll(fromArrayToObj));
	}
	
	@Test
	@Order(3)
	void testSaveAllNameException() throws NameException {
		String[] array = {""," "};
		var arr = utils.fromArrayToList(array);
		var fromArrayToObj = utils.fromArrayToObj(arr);
		assertThrows(NameException.class, 
				() -> namesServiceImpl.saveAll(fromArrayToObj));
	}
	
	@Test
	@Order(4)  
	void testNameNotExists() {
		Boolean nameExists = namesServiceImpl.nameExists("Ishigo");
		assertFalse(nameExists);
	}
	
	@AfterAll
	void tearDown() throws Exception {
		utils.fromArrayToList(arrNames)
			.forEach(x -> {
				Names byName = namesRepository.findByName(x);
				namesRepository.deleteById(byName.getId());
			});
	}

}
