package br.com.hcs.progressus.helper;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObjectHelper implements Serializable {
	
	private static final long serialVersionUID = -7307157509687124897L;
	private static final Logger logger = LoggerFactory.getLogger(ObjectHelper.class);

	public static boolean isNullOrEmpty(Object object) {
		
		try {
		
			return object == null || object.toString().equals("0") || StringHelper.isNullOrEmpty(object.toString());
		
		} catch (Exception e) {
			ObjectHelper.logger.warn(e.getMessage());
		}
		
		return true;
	}
}
