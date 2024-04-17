package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
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
		ArrayList<Names> namesList = new ArrayList<Names>();
		namesList.add(new Names("Lebron James", now()));
		namesList.add(new Names("Derrick Rose", now()));
		this.namesService.saveAll(namesList);

		when(this.namesRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(namesList));

		var values = this.namesService.listNames(of(0, 3, by("name"))).toList();

		assertThat(values).hasSize(2);
		assertThat(values.get(0).getName()).isEqualToIgnoringCase("Lebron James");
		assertThat(values.get(1).getName()).isEqualToIgnoringCase("Derrick Rose");
	}

	@Test
	@DisplayName("Should save the name with default parameters")
	public void testsaveName() throws DuplicatedNameException {
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
	public void testsaveAllName() throws DuplicatedNameException, NameException {
		ArrayList<Names> namesList = new ArrayList<Names>();
		namesList.add(new Names("Lebron James", now()));
		namesList.add(new Names("Derrick Rose", now()));
		this.namesService.saveAll(namesList);

		when(this.namesRepository.saveAll(anyIterable())).thenReturn(namesList);

		verify(this.namesRepository, times(1)).saveAll(anyIterable());

		ArgumentCaptor<Iterable<Names>> namesCaptor = ArgumentCaptor.forClass(Iterable.class);
		verify(this.namesRepository).saveAll(namesCaptor.capture());

		List<Iterable<Names>> values = namesCaptor.getAllValues();

		int i = 0;
		var it = values.get(0).iterator();
		while (it.hasNext()) {			
			assertThat(it.next().getName())
				.isNotNull()
				.matches(namesList.get(i).getName()::equalsIgnoreCase);			
			i++;
		}

//		assertThat(values).hasSize(1).element(0)
//				.matches(x -> x.iterator().next().getName().equalsIgnoreCase("Lebron James"));

//		assertThat(values).allSatisfy(value -> {
//			assertThat(value.iterator().next().getName()).isEqualToIgnoringCase("Derrick Rose");
//		});
	}

	@Test
	@DisplayName("Should verify that name exists")
	void testNameExists() {
		var name = new Names(1L, "latrell sprewell", now());
		when(this.namesRepository.findAll()).thenReturn(Arrays.asList(name));
		var nameExists = this.namesService.nameExists(name.getName());
		assertTrue(nameExists);
	}

	@Test
	@DisplayName("Should delete name")
	public void whenAssetsDeletedByCode_thenControlFlowAsExpected() throws NameException {
		var name = new Names(100L, "Lebron James", now());
		when(this.namesRepository.findByName(any(String.class))).thenReturn(name);
		assertThatNoException().isThrownBy(() -> this.namesService.deleteByName(name.getName()));
		verify(this.namesRepository, times(1)).deleteById(name.getId());		
	}
}
