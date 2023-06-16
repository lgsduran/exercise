package fr.fiducial.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fiducial.exercise.dto.NamesDto;
import fr.fiducial.exercise.entity.Names;

public interface NamesRepository extends JpaRepository<Names, Long>{

	void save(NamesDto dto);
	
	String getByName(String name);

}
