package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@Testcontainers
@SpringBootTest
@Configuration
class NamesServiceUnitTest {
	
	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

	@Mock
	private NamesRepository namesRepository;

	@InjectMocks
	private NamesServiceImpl namesService;

	@Test
	@DisplayName("Should retrieve all names with default parameters")
	void testGetAllNames() {
		ArrayList<Names> namesList = new ArrayList<Names>();
		namesList.add(new Names("Lebron James"));
		namesList.add(new Names("Derrick Rose"));

		when(this.namesRepository.findAll(any(Pageable.class)))
			.thenReturn(new PageImpl<>(namesList));

		var values = this.namesService.listNames(of(0, 3, by("name"))).toList();
		verify(this.namesRepository, times(1)).findAll(any(Pageable.class));
		
		assertThat(values)
			.hasSize(2)
			.extracting("name")
			.containsExactly("Lebron James", "Derrick Rose");
	}

	@Test
	@DisplayName("Should save the name with default parameters")
	void testsaveName() throws DuplicatedNameException  {
		var name = new Names(1L, "Lebron James", now());
		this.namesService.save(name);

		// verifies that the method is called only once
		verify(this.namesRepository, times(1)).save(name);

		// captures argument values for further assertions.
		var nameArgumentCaptor = ArgumentCaptor.forClass(Names.class);

		// verifies that the mocking service will take a Name object and perform the
		// repository method.
		verify(this.namesRepository).save(nameArgumentCaptor.capture());

		// takes the captor value out of it and compared with the actual value.
		var value = nameArgumentCaptor.getValue();

		assertThat(value.getName())
			.isNotNull()
			.matches(name.getName()::equalsIgnoreCase);
	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Should save the names with default parameters")
	void testsaveAllName() throws NameException, DuplicatedNameException {
		ArrayList<Names> namesList = new ArrayList<Names>();
		namesList.add(new Names("Lebron James"));
		namesList.add(new Names("Derrick Rose"));
		this.namesService.saveAll(namesList);

		when(this.namesRepository.saveAll(anyIterable())).thenReturn(namesList);

		verify(this.namesRepository, times(1)).saveAll(anyIterable());

		ArgumentCaptor<Iterable<Names>> namesCaptor = ArgumentCaptor.forClass(Iterable.class);
		verify(this.namesRepository, times(1)).saveAll(namesCaptor.capture());

		var values = namesCaptor.getAllValues();
		
		 assertThat(values.get(0))
			 .hasSize(2)
			 .extracting("name")
			 .asList()
			 .containsExactly("lebron james", "derrick rose");
	}
	
	@Test
	@DisplayName("Should delete by ids")
	void testDeleteAllByIds() {
		var lebron = new Names(1L, "Lebron James", now());
		var michael = new Names(2L, "Michael Jordan", now());		
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(lebron.getId());
		ids.add(michael.getId());
		doNothing().when(this.namesRepository).deleteAllById(anyIterable());
		this.namesService.deleteAllById(ids);
		verify(this.namesRepository, times(1)).deleteAllById(ids);
	}

	@Test
	@DisplayName("Should verify that name exists")
	void testNameExists() {
		var name = new Names(1L, "latrell sprewell", now());
		when(this.namesRepository.findAll()).thenReturn(Arrays.asList(name));
		var nameExists = this.namesService.nameExists(name.getName());
		verify(this.namesRepository, times(1)).findAll();
		assertThat(nameExists).isTrue();
	}

	@Test
	@DisplayName("Should delete name")
	void whenAssetsDeletedByName_thenControlFlowAsExpected() {
		var name = new Names(100L, "Lebron James", now());
		when(this.namesRepository.findByName(any(String.class))).thenReturn(name);
		assertThatNoException().isThrownBy(() -> this.namesService.deleteByName(name.getName()));
		verify(this.namesRepository, times(1)).deleteById(name.getId());
	}
}
