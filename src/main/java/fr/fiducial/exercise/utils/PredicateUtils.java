package fr.fiducial.exercise.utils;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

import fr.fiducial.exercise.entity.Names;

public class PredicateUtils {
	
	/**
	 * @see Method tests if two arguments are equals
	 * @param strName
	 * @return predicate
	 */
	public Predicate<Names> compareNamePredicate(String strName) {
		return x -> x.getName().equalsIgnoreCase(strName);
	}
	
	public <T> List<T> getElements(List<T> list, Predicate<? super T> p1) {
		return list.stream().filter(p1).collect(toList());
	}

}
