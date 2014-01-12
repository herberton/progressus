package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.to.ParameterTO;



public class ReflectionHelper implements Serializable {

	private static final long serialVersionUID = 5990122009250665993L;
	private static final Logger logger = LoggerFactory.getLogger(ReflectionHelper.class);
	
	
	public static List<Method> findMethodList(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return new ArrayList<>();
			}
			
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
			
			Method[] methodArray = clazz.getMethods();
			
			for (Method method : methodArray) {
				
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
			
		} catch (SecurityException e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return new ArrayList<>();
	}
	
	
	public static Method findMethod(Class<?> clazz, String methodName) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return ReflectionHelper.findMethod(clazz, methodName, null, null);
			
		} catch (Exception e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public static Method findMethod(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) {
		
		
		if (ObjectHelper.isNullOrEmpty(clazz)) {
			return null;
		}
		
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
			
		} catch (Exception e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return foundMethod;
	}

	
	public static Object executeMethod(Object object, Method method, Object...parameterArgumentArray) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(object)) {
				return null;
			}
			
			if (ObjectHelper.isNullOrEmpty(method)) {
				return null;
			}
			
			return method.invoke(object, parameterArgumentArray);
			
		} catch (Exception e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public static Object executeMethod(Object object, Method method) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(object)) {
				return null;
			}
			
			if (ObjectHelper.isNullOrEmpty(method)) {
				return null;
			}
			
			return method.invoke(object);
			
		} catch (Exception e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}

	
	public static Object newInstance(Class<?> clazz, List<ParameterTO<?>> parameterMethodList) {
		
		if (parameterMethodList == null || parameterMethodList.size() <= 0) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				ReflectionHelper.logger.warn(e.getMessage());
			}
		}
		
		try {
			
			Class<?>[] typeArray = ParameterTO.getTypeArray(parameterMethodList);
			
			if (CollectionHelper.isNullOrEmpty(typeArray)) {
				typeArray = new Class<?>[]{};
			}
			
			Object[] valueArray = ParameterTO.getValueArray(parameterMethodList);
			
			if (valueArray == null) {
				valueArray = new Object[]{};
			}
			
			Constructor<?> constructor = clazz.getConstructor(typeArray);
			
			if (constructor == null) {
				return null;
			}
			
			return constructor.newInstance(valueArray);
			
		} catch (Exception e) {
			ReflectionHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}

}
