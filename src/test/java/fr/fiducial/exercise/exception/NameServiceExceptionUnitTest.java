package fr.fiducial.exercise.exception;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import fr.fiducial.exercise.service.NamesServiceImpl;

@SpringBootTest
@Testcontainers
class NameServiceExceptionUnitTest {

	@Mock
	private NamesServiceImpl namesService;
	
	@Container
	@ServiceConnection
	static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

	@Test
	@DisplayName("Invoking SaveAll should throw DuplicatedNameException")
	void testSaveAll_DuplicatedNameException() throws NameException, DuplicatedNameException {
		when(this.namesService.saveAll(any())).thenThrow(DuplicatedNameException.class);
		
		assertThatExceptionOfType(DuplicatedNameException.class)
		  .isThrownBy(() -> {
			  this.namesService.saveAll(any());
		});
	}

	@Test
	@DisplayName("Invoking SaveAll should throw NameException")
	void testSaveAll_NameException() throws NameException, DuplicatedNameException {
		when(this.namesService.saveAll(any())).thenThrow(NameException.class);
		
		assertThatExceptionOfType(NameException.class)
		  .isThrownBy(() -> {
			  this.namesService.saveAll(any());
		});
	}

	@Test
	@DisplayName("Invoking Save should throw DuplicatedNameException")
	void testSave_NameException() throws DuplicatedNameException {
		when(this.namesService.save(any())).thenThrow(DuplicatedNameException.class);
		
		assertThatExceptionOfType(DuplicatedNameException.class)
		  .isThrownBy(() -> {
			  this.namesService.save(any());
		});
	}

	@Test
	@DisplayName("Invoking DeleteByName should throw DuplicatedNameException")
	void testDeleteByName_NameException() throws NameException {
		doThrow(NameException.class).when(this.namesService).deleteByName(any());
		
		assertThatExceptionOfType(NameException.class)
			.isThrownBy(() -> {
				this.namesService.deleteByName(any());
		});
	}
}
