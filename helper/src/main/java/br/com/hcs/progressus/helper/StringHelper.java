package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHelper implements Serializable {
	
	private static final long serialVersionUID = 6548625649992900890L;
	private static final Logger logger = LoggerFactory.getLogger(StringHelper.class);
	
	
	public static <T> String getI18N(Class<T> clazz){
		
		try {
		
			if (clazz == null) {
				return "";
			}
			
			return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().subSequence(1, clazz.getSimpleName().length());
		
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	
	public static String getGetter(String fieldName) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return "";
			}
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
		
		return "";
		
	}
	
	public static String getSetter(String fieldName) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return "";
			}
			
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
	
		return "";
	}
	
	public static boolean isNullOrEmpty(String string) {
		try {
			return string == null || string.isEmpty();
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
		return true;
	}
	
	public static String isNullOrEmptyReplaceBy(String string, String...valueArray) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(string)) {
				
				if (valueArray == null || valueArray.length <= 0) {
					return string;
				}
				
				List<String> stringList = new ArrayList<String>();
				
				for (int i = 1; i < valueArray.length ; i++) {
					stringList.add(valueArray[i]);
				}
				
				return StringHelper.isNullOrEmptyReplaceBy(valueArray[0], stringList.toArray(new String[]{}));
			}
			
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
		
		return string;
	}

	public static boolean isNumeric(String string) { 
		
		try {
			
			if (StringHelper.isNullOrEmpty(string)) {
				return false;
			}
			
			return string.matches("[-+]?\\d*\\.?\\d+"); // "((-|\\+)?[0-9]+(\\.[0-9]+)?)+"  
			
		} catch (Exception e) {
			StringHelper.logger.warn(e.getMessage());
		}
		
		return false;
    }
	
}
