package fr.fiducial.exercise.utils;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;

import java.util.ArrayList;

import fr.fiducial.exercise.entity.Names;
import jakarta.persistence.Convert;

public class ConvertUtils {
	
	/**
	 * @see Convert array to List
	 * @param arr
	 * @return
	 */

	public ArrayList<String> fromArrayToList(String[] arr) {
		return of(arr).collect(toCollection(ArrayList::new));
	}
	
	/**
	 * @see Convert array to entity
	 * @param arr
	 * @return
	 */
	public ArrayList<Names> fromArrayToObj(ArrayList<String> arr) {
		return arr.stream().map(u -> new Names(u, now()))
				.collect(toCollection(ArrayList::new));

	}
	
	

}
