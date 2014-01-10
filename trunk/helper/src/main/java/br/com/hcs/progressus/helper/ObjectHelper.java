package br.com.hcs.progressus.helper;


public class ObjectHelper {
	
	public static boolean isNullOrEmpty(Object object) {
		return object == null || object.toString().equals("0") || StringHelper.isNullOrEmpty(object.toString());
	}
}
