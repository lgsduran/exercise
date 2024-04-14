package fr.fiducial.exercise.service;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
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
	void getAllNames() {
		var name1 = new Names(1L, "Bat girl", now());
		var name2 = new Names(2L, "Batman", now());
 		
 		Page<Names> namePage = new PageImpl<>(Arrays.asList(name1, name2));
 		when(namesRepository.findAll(any(Pageable.class))).thenReturn(namePage);
 		
		var list = namesService.listNames(of(0, 3, by("name"))).toList();
		
		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0).getName()).isEqualToIgnoringCase("Bat girl");
		assertThat(list.get(1).getName()).isEqualToIgnoringCase("batman");
	}

	@Test
	@DisplayName("Should save the name with default parameters")
	public void testsaveName() throws DuplicatedNameException {
		var name = new Names(1L, "Bat girl", now());
		namesService.save(name);
		verify(namesRepository, times(1)).save(name);
		var nameArgumentCaptor = ArgumentCaptor.forClass(Names.class);
		verify(namesRepository).save(nameArgumentCaptor.capture());
		var value = nameArgumentCaptor.getValue();
		assertNotNull(value.getId());
		assertEquals("bat girl", value.getName());
	}

	@Test
	@DisplayName("Should verify if the name exists")
	void testNameExists() {
		var name1 = new Names(1L, "batman", now());
		when(namesRepository.findAll()).thenReturn(Arrays.asList(name1));
		var nameExists = namesService.nameExists(name1.getName());
		assertTrue(nameExists);
	}
}
