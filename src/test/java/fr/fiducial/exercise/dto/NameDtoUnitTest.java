package fr.fiducial.exercise.dto;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;

import fr.fiducial.exercise.entity.Names;

@TestMethodOrder(OrderAnnotation.class)
class NameDtoUnitTest {

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	@Order(1)
	void testConvertNameEntityToNameDto() {
		var name = new NamesDto(1L, "luiz", now());
		var nameDto = modelMapper.map(name, NamesDto.class);
		assertEquals(name.getId(), nameDto.getId());
		assertEquals(name.getName(), nameDto.getName());
		assertEquals(name.getCreated_At(), nameDto.getCreated_At());
	}
	
	@Test
	@Order(2)
	void testConvertNameDtoToNameEntity() {
		var name = new Names();
		name.setId(1L);
		name.setName("luiz");
		name.setCreatedAt(now());		
		var nameDto = modelMapper.map(name, Names.class);
		assertEquals(nameDto.getId(), name.getId());
		assertEquals(nameDto.getName(), name.getName());
		assertEquals(nameDto.getCreatedAt(), name.getCreatedAt());
	}

}
