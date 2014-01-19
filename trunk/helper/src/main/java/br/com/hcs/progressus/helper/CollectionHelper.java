package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Collection;

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
}
