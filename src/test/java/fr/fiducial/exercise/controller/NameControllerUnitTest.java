package fr.fiducial.exercise.controller;

import static java.time.Instant.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.service.NamesServiceImpl;

@WebMvcTest(controllers = NameController.class)
@ExtendWith(SpringExtension.class)
//@ExtendWith(SpringExtension.class)
public class NameControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NamesServiceImpl namesService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Should save nameDto")
	void NamesController_saveName_ReturnNamesDto() throws JsonProcessingException, Exception {
		
		var namesDto = new NamesDto(1L, "Lebron James", now());

		when(this.namesService.save(Mockito.any())).thenReturn(namesDto);

		ResultActions response = mockMvc.perform(post("/api/names/addName").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(namesDto)));

		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(longToIntCast(namesDto.getId()))))
				.andExpect(jsonPath("$.name", is(namesDto.getName())))
				.andExpect(jsonPath("$.created_At", notNullValue()));
	}

	private int longToIntCast(long number) {
		return (int) number;
	}

}
