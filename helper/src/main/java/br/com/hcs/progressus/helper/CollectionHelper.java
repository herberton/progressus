package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionHelper 
	implements 
		Serializable 
{
	
	private static final long serialVersionUID = 5776762570787593851L;
	private static final Logger logger = LoggerFactory.getLogger(CollectionHelper.class);
	
	public static <T> boolean isNullOrEmpty(T[] array) {
		
		try {
			
			return array == null || array.length <= 0;
			
		} catch (Exception e) {
			CollectionHelper.logger.warn(e.getMessage());
		}
		
		return true;
	}
	
	public static <T> boolean isNullOrEmpty(Collection<T> collection) {
		
		try {
			
			return collection == null || collection.size() <= 0;
			
		} catch (Exception e) {
			CollectionHelper.logger.warn(e.getMessage());
		}
		
		return true;
	}
	
	public static <T> List<T> isNullReplaceByNewArrayList(List<T> list) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(list)) {
				list = new ArrayList<T>();
			}
			
			return list;
			
		} catch (Exception e) {
			CollectionHelper.logger.warn(e.getMessage());
		}
		
		if (list == null) {
			list = new ArrayList<T>();	
		}
		
		return list;
	}
	
	public static <T> Set<T> isNullReplaceByNewHashSet(Set<T> set) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(set)) {
				set = new HashSet<T>();
			}
			
			return set;
			
		} catch (Exception e) {
			CollectionHelper.logger.warn(e.getMessage());
		}
		
		if (set == null) {
			set = new HashSet<T>();	
		}
		
		return set;
	}
}
