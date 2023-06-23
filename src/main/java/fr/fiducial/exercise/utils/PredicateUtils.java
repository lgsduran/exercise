package fr.fiducial.exercise.utils;

import java.lang.reflect.Method;
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
	

}
