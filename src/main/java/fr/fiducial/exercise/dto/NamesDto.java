package fr.fiducial.exercise.dto;

import java.time.Instant;

public class NamesDto {

	private long id;
	private String name;	
	private Instant created_At;
	
	/**
	 * @param id
	 * @param name
	 * @param created_At
	 */
	public NamesDto(long id, String name, Instant created_At) {
		super();
		this.id = id;
		this.name = name;
		this.created_At = created_At;
	}	

	/**
	 * @param name
	 */
	public NamesDto(String name) {
		this.name = name;
	}	

	/**
	 * @param name
	 * @param created_At
	 */
	public NamesDto(String name, Instant created_At) {
		this.name = name;
		this.created_At = created_At;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the created_At
	 */
	public Instant getCreated_At() {
		return created_At;
	}
	
	/**
	 * @see method copies the properties of source object to target object
	 * @return names
	 */	
//	public Names toEntity() {
//		var names = new Names();
//		copyProperties(this, names);
//		return names;
//	}
	
	
	
	
	
	

}
