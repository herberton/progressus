package br.com.hcs.progressus.helper;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {
	
	public static <T> String getI18N(Class<T> clazz){
		return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().subSequence(1, clazz.getSimpleName().length());
	}
	public static String getGetter(String fieldName) {
		return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
	}
	public static String getSetter(String fieldName) {
		return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
	}
	
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
	public static String isNullOrEmptyReplaceBy(String string, String...valueArray) {
		
		if (StringHelper.isNullOrEmpty(string)) {
			
			if (valueArray == null || valueArray.length <= 0) {
				return null;
			}
			
			List<String> stringList = new ArrayList<String>();
			
			for (int i = 1; i < valueArray.length ; i++) {
				stringList.add(valueArray[i]);
			}
			
			return StringHelper.isNullOrEmptyReplaceBy(valueArray[0], stringList.toArray(new String[]{}));
		}
		
		return string;
	}

	public static boolean isNumeric(String string) { 
		return string.matches("[-+]?\\d*\\.?\\d+"); // "((-|\\+)?[0-9]+(\\.[0-9]+)?)+"  
    }
	
}
