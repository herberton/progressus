package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapHelper 
	implements 
		Serializable 
{
	private static final long serialVersionUID = 3079023090526467773L;
	private static final Logger logger = LoggerFactory.getLogger(MapHelper.class);
	
	public static <K, V> boolean isNullOrEmpty(Map<K, V> map) {
		
		try {
			
			return map == null || map.size() <= 0;
			
		} catch (Exception e) {
			MapHelper.logger.warn(e.getMessage());
		}
		
		return true;
	}
}
