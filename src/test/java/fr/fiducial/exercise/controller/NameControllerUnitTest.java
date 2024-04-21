package fr.fiducial.exercise.controller;

import static java.time.Instant.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;
import fr.fiducial.exercise.service.NamesServiceImpl;

@WebMvcTest(controllers = NameController.class)
@ExtendWith(SpringExtension.class)
public class NameControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private NamesServiceImpl namesService;

	private ArrayList<NamesDto> namesList = new ArrayList<>();
	private NamesDto namesDto;

	@BeforeEach
	public void setUp() {
		namesDto = new NamesDto(1L, "Lebron James", now());
		namesList.add(namesDto);
	}

	@Test
	@DisplayName("Should save nameDto")
	void NamesController_saveName_ReturnNamesDto() throws Exception {	
		when(this.namesService.save(any())).thenReturn(namesDto);

		mockMvc.perform(post("/api/names/addName").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(namesDto)))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.id", is(longToIntCast(namesDto.getId()))))
				.andExpect(jsonPath("$.name", is(namesDto.getName())))
				.andExpect(jsonPath("$.created_At", notNullValue()));
	}

	@Test
	@DisplayName("Should saveAll namesDto")
	void NamesController_saveAllName_ReturnNamesDto() throws Exception {
		when(this.namesService.saveAll(any())).thenReturn(namesList);

		mockMvc.perform(post("/api/names/addNames")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(namesList)))
				.andExpect(status().is2xxSuccessful())				
				.andExpect(jsonPath("$.[0].name", is(namesList.get(0).getName())));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	@DisplayName("Should retrieve all namesDto")
	void NamesController_listNames_ReturnNames() throws Exception {
		Page<Names> pagedResponse = new PageImpl(namesList);
		when(this.namesService.listNames(any())).thenReturn(pagedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/names/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.content.[0].name", is(namesList.get(0).getName())));
	}

	@Test
	@DisplayName("Should verify that name exists")
	void NamesController_NameExists_ReturnTrue() throws Exception {
		when(this.namesService.nameExists(any())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/names/{name}", namesDto.getName())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())				
				.andExpect(jsonPath("$", is(true)));
	}

	@Test
	@DisplayName("Should verify that name does not exist")
	void NamesController_NameExists_ReturnFalse() throws Exception {
		when(this.namesService.nameExists(any())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/names/{name}", "ozzy")
				.contentType(MediaType.APPLICATION_JSON))	
				.andExpect(status().is2xxSuccessful())				
				.andExpect(jsonPath("$", is(false)));
	}

	@Test
	@DisplayName("Should delete name")
	void NameController_DeleteName_ReturnOk() throws Exception {
		doNothing().when(this.namesService).deleteByName(namesDto.getName());

		mockMvc.perform(delete("/api/names/Lebron James")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Should delete by ids name")
	void NameController_DeleteAllByIds_ReturnOk() throws Exception {
		ArrayList<Long> arrayList = new ArrayList<Long>();
		arrayList.add(namesList.get(0).getId());
		doNothing().when(this.namesService).deleteAllById(arrayList);

		mockMvc.perform(delete("/api/names/deleteAllByIds{id}", arrayList)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	private int longToIntCast(long number) {
		return (int) number;
	}

}
