package fr.fiducial.exercise.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.repository.NamesRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class ExceptionTest {
	
	@Autowired
	private NamesRepository namesRepository;

	private NamesServiceImpl namesServiceImpl;
	
	private Names nameTest = new Names("usertest");
	

	@BeforeEach
	public void setUp() throws Exception {
		namesServiceImpl = new NamesServiceImpl(namesRepository);
	}
	
	@Test
	@Order(2)
	public void testSaveException() throws DuplicatedNameException {
		var result = namesServiceImpl.save(nameTest);
		
	}


}
