package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.exception.DuplicatedNameException;
import fr.fiducial.exercise.exception.NameException;
import fr.fiducial.exercise.repository.NamesRepository;

@ExtendWith(SpringExtension.class)
class NamesServiceUnitTest {

	@Mock
	private NamesRepository namesRepository;

	@InjectMocks
	private NamesServiceImpl namesService;

	@Test
	@DisplayName("Should retrieve all names with default parameters")
	void getAllNames() throws NameException, DuplicatedNameException {
		ArrayList<Names> namesList = new ArrayList<>();
		namesList.add(new Names(1L, "Bat girl", now()));
		namesList.add(new Names(2L, "Batman", now()));
		namesService.saveAll(namesList);
 		
 		when(namesRepository.findAll(any(Pageable.class)))
 			.thenReturn(new PageImpl<>(namesList));
 		
		var result = namesService.listNames(of(0, 3, by("name")))
				.toList();
		
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getName()).isEqualToIgnoringCase("Bat girl");
		assertThat(result.get(1).getName()).isEqualToIgnoringCase("batman");
	}

	@Test
	@DisplayName("Should save the name with default parameters")
	public void testsaveName() throws DuplicatedNameException {
		var name = new Names(1L, "Bat girl", now());
		namesService.save(name);
		
		// verifies that the method is called only once
		verify(namesRepository, times(1)).save(name);		
		
		// captures argument values for further assertions.
		var nameArgumentCaptor = ArgumentCaptor.forClass(Names.class);
		
		// verifies that the mocking service will take a Name object and perform the repository method.
		verify(namesRepository).save(nameArgumentCaptor.capture());
		
		// takes the captor value out of it and compared with the actual value.
		var value = nameArgumentCaptor.getValue();
		
		assertNotNull(value.getId());
		assertThat("bat girl").isEqualToIgnoringCase(value.getName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Should save the names with default parameters")
	public void testsaveAllName() throws DuplicatedNameException, NameException {
		ArrayList<Names> namesList = new ArrayList<Names>();
		namesList.add(new Names(1L, "Bat girl", now()));
		namesList.add(new Names(2L, "Batman", now()));
		namesService.saveAll(namesList);		
		
		when(namesRepository.saveAll(anyIterable())).thenReturn(namesList);	
		
		verify(namesRepository, times(1)).saveAll(anyIterable());
		
		ArgumentCaptor<Iterable<Names>> namesCaptor = ArgumentCaptor.forClass(Iterable.class);
		verify(namesRepository).saveAll(namesCaptor.capture());
		
		List<Iterable<Names>> values1 = namesCaptor.getAllValues();
		
		assertThat(values1.size()).isEqualTo(1);
		//assertThat("bat girl").isEqualToIgnoringCase(values1.get(0).);

	}

	@Test
	@DisplayName("Should verify that name exists")
	void testNameExists() {
		var name1 = new Names(1L, "batman", now());
		when(namesRepository.findAll()).thenReturn(Arrays.asList(name1));
		var nameExists = namesService.nameExists(name1.getName());
		assertTrue(nameExists);
	}
}
