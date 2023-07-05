package fr.fiducial.exercise.utils;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
	
	/**
	 * @see Convert to list based on the function
	 * @param <T>
	 * @param <U>
	 * @param list
	 * @param f1
	 * @return
	 */
	public <T, U> List<U> convertToList(List<T> list, Function<T, U> f1) {
		return list.stream().map(f1).collect(toList());
	}
	
	

}
