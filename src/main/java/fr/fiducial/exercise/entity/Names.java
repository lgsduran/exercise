package fr.fiducial.exercise.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * The persistent class for the names database table. * 
 */

@Entity
@Table(name = "names")
public class Names {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "name_id")
	private long id;

	@NotBlank(message = "Field must not be null.")
	private String name;

	@CreatedDate
	@Column(name = "created_At", nullable = false)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}	

	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	public Names() {}

	public Names(String name) {
		this.name = name;
	}

	public Names(String name, Instant createdAt) {
		this.name = name;
		this.createdAt = createdAt;
	}


	
	
	
	

}
