package fr.fiducial.exercise.dto;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.fiducial.exercise.entity.Names;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class NameDtoUnitTest {

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	@Order(1)
	@DisplayName("Should convert NameEntity To NameDto")
	void testConvertNameEntityToNameDto() {
		var name = new NamesDto(1L, "luiz", now());
		var nameDto = modelMapper.map(name, NamesDto.class);
		assertEquals(name.getId(), nameDto.getId());
		assertEquals(name.getName(), nameDto.getName());
		assertEquals(name.getCreated_At(), nameDto.getCreated_At());
	}
	
	@Test
	@Order(2)
	@DisplayName("Should convert NameDto To NameEntity")
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
