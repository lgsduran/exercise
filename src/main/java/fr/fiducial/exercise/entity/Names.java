package fr.fiducial.exercise.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "names")
public class Names {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "name_id")
	private long id;

	@NotBlank(message = "Field must not be null.")
	private String name;

	@DateTimeFormat(iso = DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate data;

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
	 * @return the data
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Names [id=" + id + ", name=" + name + ", data=" + data + "]";
	}
}
