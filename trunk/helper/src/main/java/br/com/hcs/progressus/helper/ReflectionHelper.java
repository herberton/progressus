package br.com.hcs.progressus.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.hcs.progressus.to.ParameterTO;



public class ReflectionHelper {

	public static List<Method> findMethodList(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) {
		
		List<Method> foundMethodCollection = new ArrayList<Method>();
		
		parameterTypeArray =
			parameterTypeArray == null ?
				new Class<?>[] { } :
				parameterTypeArray;
					
		
		boolean isSerchByName = 
			methodName == null || methodName.trim().isEmpty() ?
				false :
				true;
		
		if (isSerchByName) {
			
			Method foudMethod = ReflectionHelper.findMethod(clazz, methodName, methodAnnotation, parameterTypeArray);
			
			if (foudMethod != null) {
				foundMethodCollection.add(foudMethod);
			}
			
			return foundMethodCollection;
		}
		
		boolean isSerchByAnnotation = 
			methodAnnotation == null ?
				false :
				true;
		
		boolean isOnlyMethodsWithoutParameters =
			parameterTypeArray.length <= 0 ?
				false :
				true;
		
		for (Method method : clazz.getMethods()) {
			
			boolean isFoundMethod =
				isSerchByAnnotation && method.isAnnotationPresent(methodAnnotation) || 
				!isSerchByAnnotation;
			
			if(!isFoundMethod) {
				continue;
			}
			
			if (isOnlyMethodsWithoutParameters && method.getParameterTypes().length <= 0) {
				foundMethodCollection.add(method);
				continue;
			}
			
			if(parameterTypeArray.length != method.getParameterTypes().length) {
				continue;
			}
			
			for (int i = 0; i < parameterTypeArray.length; i++) {
				if (!parameterTypeArray[i].equals(method.getParameterTypes()[i])) {
					isFoundMethod = false;
				}
			}
			
			if (isFoundMethod) {
				foundMethodCollection.add(method);
			}
		}
		
		return foundMethodCollection;
	}
	
	public static Method findMethod(Class<?> clazz, String methodName) {
		return ReflectionHelper.findMethod(clazz, methodName, null, null);
	}
	public static Method findMethod(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) {
		
		Method foundMethod = null;
		
		parameterTypeArray =
			parameterTypeArray == null ?
				new Class<?>[] { } :
				parameterTypeArray;
		
		try {
			
			boolean isSerchByAnnotation = 
				methodAnnotation == null ?
					false :
					true;
			
			foundMethod = clazz.getMethod(methodName, parameterTypeArray);
			
			if (isSerchByAnnotation && !foundMethod.isAnnotationPresent(methodAnnotation)) {
				return null;
			}
			
		} catch (NoSuchMethodException e) {
			return null;
		} catch (SecurityException e) {
			return null;
		}
		
		return foundMethod;
	}

	public static Object executeMethod(Object object, Method method, Object...parameterArgumentArray) {
		try {
			return method.invoke(object, parameterArgumentArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object executeMethod(Object object, Method method) {
		try {
			return method.invoke(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object newInstance(Class<?> clazz, List<ParameterTO<?>> parameterMethodList) {
		
		if (parameterMethodList == null || parameterMethodList.size() <= 0) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			Constructor<?> constructor = clazz.getConstructor(ParameterTO.getTypeArray(parameterMethodList));
			return constructor.newInstance(ParameterTO.getValueArray(parameterMethodList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
