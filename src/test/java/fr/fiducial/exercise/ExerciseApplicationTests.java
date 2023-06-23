package fr.fiducial.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.fiducial.exercise.controller.NameController;

@SpringBootTest
class ExerciseApplicationTests {

	@Autowired
	private NameController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
