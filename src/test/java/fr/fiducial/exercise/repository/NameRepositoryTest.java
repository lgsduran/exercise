package fr.fiducial.exercise.repository;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import fr.fiducial.exercise.entity.Names;

@SpringBootTest
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public class NameRepositoryTest {

	@Autowired
	private NamesRepository namesRepository;

	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");
	
	@BeforeEach
	public void setUp() {
		namesRepository.deleteAll();
	}

	@Test
	@Order(1)
	@DisplayName("Should save name and verify that id exists")
	public void testSaveNameAndFindById() {
		Names lebron = new Names("Lebron James", now());
		namesRepository.save(lebron);
		Names resultName = namesRepository.findById(lebron.getId()).get();
		assertThat(resultName).isNotNull();
		assertThat(resultName).extracting(Names::getId).isNotNull();
		assertThat(resultName).extracting(Names::getCreatedAt).isNotNull();
		assertThat(resultName.getName()).isNotNull().matches(lebron.getName()::equalsIgnoreCase);
	}

	@Test
	@Order(2)
	@DisplayName("Should verify that name exists")
	public void testFindByName() {
		Names mike = new Names("Mike Tyson", now());
		namesRepository.save(mike);
		Names resultName = namesRepository.findByName(mike.getName());
		assertThat(resultName.getName()).isNotNull().matches(mike.getName()::equalsIgnoreCase);
	}

	@Test
	@Transactional
	@Order(3)
	@DisplayName("Should delete by name")
	public void testDeleteByName() {
		Names ozzy = new Names("Ozzy Osbourne", now());
		namesRepository.save(ozzy);
		namesRepository.deleteByName(ozzy.getName().toLowerCase());
		var finalResult = namesRepository.findById(ozzy.getId());
		assertThat(finalResult.isEmpty()).isTrue();
	}

	@Test
	@Order(4)
	@DisplayName("Should save all names and delete all by ids")
	public void testSaveAllAndDeleteByIds() {
		var nameList = new ArrayList<Names>();
		nameList.add(new Names("Lionel Messi", now()));
		nameList.add(new Names("Michael Jordan", now()));

		namesRepository.saveAll(nameList);
		var firstRound = namesRepository.findAll();
		
		assertThat(firstRound)
			.hasSize(2)
			.extracting("name")
			.containsExactly("Lionel Messi", "Michael Jordan");

		var secondRound = namesRepository.findAll();
		var ids = new ArrayList<Long>();
		secondRound.forEach(id -> ids.add(id.getId()));
		namesRepository.deleteAllById(ids);

		var result = namesRepository.findAllById(ids).stream().findAny();
		assertThat(result.isPresent()).isFalse();
	}
}
