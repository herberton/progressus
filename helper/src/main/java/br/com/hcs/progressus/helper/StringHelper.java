package br.com.hcs.progressus.helper;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {
	
	public static <T> String getI18N(Class<T> clazz){
		
		try {
		
			if (clazz == null) {
				return "";
			}
			
			return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().subSequence(1, clazz.getSimpleName().length());
		
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	
		return "";
	}
	
	public static boolean isNullOrEmpty(String string) {
		try {
			return string == null || string.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
		return false;
    }
	
}
