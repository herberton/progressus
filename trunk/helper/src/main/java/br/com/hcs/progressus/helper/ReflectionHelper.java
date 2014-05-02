package  br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.hcs.progressus.contract.ClassValidator;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.to.ParameterTO;

public final class ReflectionHelper implements Serializable {

	private static final long serialVersionUID = 3631371700571515093L;

	
	public static final List<Method> findMethodList(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) throws ProgressusException {
		
		try {
			
			if (clazz == null) {
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
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
	}
		
	
	public static final Method findMethod(Class<?> clazz, String methodName) throws ProgressusException {
		
		try {
			
			if (clazz == null) {
				return null;
			}
			
			if (StringHelper.isNullOrEmpty(methodName)) {
				return null;
			}
			
			return ReflectionHelper.findMethod(clazz, methodName, null, null);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethod", e);
		}
	}
	
	public static final Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypeArray) throws ProgressusException {
		
		try {
			
			if (clazz == null) {
				return null;
			}
			
			if (StringHelper.isNullOrEmpty(methodName)) {
				return null;
			}
			
			return ReflectionHelper.findMethod(clazz, methodName, null, parameterTypeArray);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethod", e);
		}
	}
	
	public static final Method findMethod(Class<?> clazz, String methodName, Class<? extends Annotation> methodAnnotation, Class<?>[] parameterTypeArray) throws ProgressusException {
		
		
		if (clazz == null) {
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
			
			
			
			try {
				foundMethod = clazz.getMethod(methodName, parameterTypeArray);
			} catch (Exception e) {
				return null;
			}
			
			if (isSerchByAnnotation && !foundMethod.isAnnotationPresent(methodAnnotation)) {
				return null;
			}
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethod", e);
		}
		
		return foundMethod;
	}

	
	public static final Object executeMethod(Object object, Method method, Object...parameterArgumentArray) throws ProgressusException {
		
		try {
			
			if (object == null) {
				return null;
			}
			
			if (method == null) {
				return null;
			}
			
			return method.invoke(object, parameterArgumentArray);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}
	}
	
	public static final Object executeMethod(Object object, Method method) throws ProgressusException {
		
		try {
			
			if (object == null) {
				return null;
			}
			
			if (method == null) {
				return null;
			}
			
			return method.invoke(object);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}
	}

	
	public static final <T> T newInstance(Class<T> clazz) throws ProgressusException {
		try {
			return ReflectionHelper.newInstance(clazz, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newInstance", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T newInstance(Class<T> clazz, List<ParameterTO<?>> parameterMethodList) throws ProgressusException {
		
		if (parameterMethodList == null || parameterMethodList.size() <= 0) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				throw new UnableToCompleteOperationException("newInstance", e);
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
			
			return (T) constructor.newInstance(valueArray);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newInstance", e);
		}
	}
	
	
	public static final List<Field> getPublicFieldList(Class<?> clazz) throws ProgressusException {
		try {
			return ReflectionHelper.getPublicFieldList(clazz, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
	}
	
	public static final List<Field> getPublicFieldList(Class<?> clazz, ClassValidator validator) throws ProgressusException {
		
		try {
			
			if (clazz == null) {
				return null;
			}
			
			List<Field> fieldList = new ArrayList<Field>();
			
			do {
				
				Field[] declaredFieldArray = clazz.getDeclaredFields();
				
				if (CollectionHelper.isNullOrEmpty(declaredFieldArray)) {
					return new ArrayList<>();
				}
				
				for (Field field : declaredFieldArray) {
					
					Method method = ReflectionHelper.findMethod(clazz, StringHelper.getGetter(field.getName()));
					
					if (method == null) {
						continue;
					}
					
					if (!Modifier.isPublic(method.getModifiers())) {
						continue;
					}
					
					fieldList.add(field);
				}
				
				
				clazz = clazz.getSuperclass();
				
				
			} while(validator == null || clazz == null ? clazz != null : validator.isValidClass(clazz));
			
			
			return fieldList;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
	}
	
	
	public static final Object executeGetter(Object object, Field field) throws ProgressusException {
		try {
			
			if (object == null) {
				return null;
			}
			
			if (field == null) {
				return null;
			}
			
			Method method =
				ReflectionHelper
					.findMethod(
						object.getClass(), 
						StringHelper.getGetter(field.getName())
					);
			
			if (method == null) {
				return null;
			}
			
			return 
				ReflectionHelper.executeMethod(object, method);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeGetter", e);
		}
	}
	
	public static final void executeSetter(Object object, Field field, Object value) throws ProgressusException {
		
		try {
			
			if (object == null) {
				return;
			}
			
			if (field == null) {
				return;
			}
			
			Method method =
				ReflectionHelper
					.findMethod(
						object.getClass(), 
						StringHelper.getSetter(field.getName()), 
						new Class<?> [] { field.getType() }
					);
			
			if (method == null) {
				return;
			}
			
			ReflectionHelper.executeMethod(object, method, new Object[] { value });
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeSetter", e);
		}
	}
	
	
	public static final <T> T parse(Object object, Class<T> returnClazz) throws ProgressusException {
		
		try {
			
			if (object == null) { 
				return null; 
			}
			
			if (returnClazz == null) { 
				return null; 
			}
			
			T objectReturn = returnClazz.newInstance();
			
			ClassValidator 
				delegate = 
					object instanceof ClassValidator ? (ClassValidator)object : null,
				returnDelegate =
					objectReturn instanceof ClassValidator ? (ClassValidator)objectReturn : null;
			
			List<Field> returnFieldList = ReflectionHelper.getPublicFieldList(returnClazz, returnDelegate);
			for (Field	returnField : returnFieldList) {
				
				List<Field> fieldList = ReflectionHelper.getPublicFieldList(object.getClass(), delegate);
				for (Field field :  fieldList){
					
					if (field.getName().equals(returnField.getName())) {
						
						Object value = ReflectionHelper.executeGetter(object, field);
						
						if (value == null) {
							break;
						}
						
						Object returnValue = null;
						
						if (ReflectionHelper.isCompatible(value, returnField) || 
							ReflectionHelper.isPrimitiveOrWrapper(value.getClass(), true, true)) {
							returnValue = ReflectionHelper.cast(value, returnField);
						} else {
							returnValue = ReflectionHelper.parse(value, returnField.getType());
						}
						
						ReflectionHelper.executeSetter(objectReturn, returnField, returnValue);	
						break;
					}
				}
			}
			
			return objectReturn;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("parse", e);
		}
	}
	
	
	public static final Object cast(Object value, Field field) throws ProgressusException {
		
		try {
		
			if (value == null) { 
				return null; 
			}
			
			if (field == null) { 
				return null; 
			}
			
			if (ReflectionHelper.isCollectionOrArray(value.getClass()) && ReflectionHelper.isCollectionOrArray(field.getType())) {
				
				Object array =
					value.getClass().isArray() ?
						value :
						((Collection<?>)value).toArray();
				
				if (field.getType().isArray()) {
					
					Object returnArray = Array.newInstance(field.getType().getComponentType(), Array.getLength(array));
					for (int i = 0; i < Array.getLength(array); i++) {
						Array.set(returnArray, i, ReflectionHelper.parse(Array.get(array, i), field.getType().getComponentType()));
					}
					
					return returnArray;
					
				} 

				List<Object> list = (List<Object>) new ArrayList<Object>();
				ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
				
				for (int i = 0; i < Array.getLength(array); i++) {
					((List<Object>)list).add(ReflectionHelper.parse(Array.get(array, i), (Class<?>)parameterizedType.getActualTypeArguments()[0]));
				}
				
				return list;
			}
			
			if (ReflectionHelper.isWrapper(value.getClass(), false) && ReflectionHelper.isPrimitiveOrWrapper(field.getType(), false, false)) {
				return value;
			}
			
		    return field.getType().cast(value);
		    
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("cast", e);
		}
	}
	
	
	public static final boolean isCompatible(Object object, Field field) throws ProgressusException {
		
		try {
			
			if (field == null) { 
				return false; 
			}
			
			if (object == null) { 
				return true; 
			}
			
			if (ReflectionHelper.isPrimitiveOrWrapper(object.getClass(), true, true) && 
				ReflectionHelper.isPrimitiveOrWrapper(field.getType(), true, true)) {
				return ReflectionHelper.cast(object, field) != null;
			}
			
			if (ReflectionHelper.isCollectionOrArray(field.getType()) && 
				ReflectionHelper.isCollectionOrArray(object.getClass())) {
				return true;
			}
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isCompatible", e);
		}
		
		return false;
	}
		
	public static final boolean isPrimitiveOrWrapper(Class<?> clazz, boolean verifyIsString, boolean verifyIsEnum) throws ProgressusException {
		
		try {
			
			if (clazz == null) { 
				return false; 
			}
			
			boolean result = clazz.isPrimitive() || ReflectionHelper.isWrapper(clazz, verifyIsString);
			
			if (verifyIsEnum) {
				result = result || clazz.isEnum();
			}
			
			return result;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPrimitiveOrWrapper", e);
		}
	}
		
	public static final boolean isWrapper(Class<?> clazz, boolean verifyIsString) throws ProgressusException {
		try {
			
			if (clazz == null) { 
				return false; 
			}
			
			List<Class<?>> wrapperList =  new ArrayList<Class<?>>();
			
			wrapperList.add(Boolean.class);
			wrapperList.add(Byte.class);
			wrapperList.add(Character.class);
			wrapperList.add(Short.class);
			wrapperList.add(Integer.class);
			wrapperList.add(Long.class);
			wrapperList.add(Float.class);
			wrapperList.add(Double.class);
			
			if (verifyIsString) {
				wrapperList.add(String.class);
			}
			
			return wrapperList.contains(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isWrapper", e);
		}
	}
	
	public static final boolean isCollectionOrArray(Class<?> clazz) throws ProgressusException {
		try {
			if (clazz == null) { 
				return false; 
			}
			return ReflectionHelper.isCollection(clazz) || clazz.isArray();
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isCollectionOrArray", e);
		}
	}
	
	
	public static final boolean isCollection(Class<?> clazz) throws ProgressusException {
		try {
			if (clazz == null) { 
				return false; 
			}
			if(clazz.getSuperclass() == null) {
				return clazz.equals(Collection.class);
			}
			return clazz.getSuperclass().equals(AbstractList.class) || clazz.getSuperclass().equals(AbstractSet.class);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isCollection", e);
		}
	}

	
	public static final List<Class<?>> getGenericClassList(Class<?> clazz) throws ProgressusException {
		
		List<Class<?>> genericClasLsist = new ArrayList<>();
		
		try {
			
			if (clazz == null) {
				return genericClasLsist;
			}
			
			Type[] actualTypeArgumentArray = ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments();
			
			for (Type actualTypeArgument : actualTypeArgumentArray) {
				genericClasLsist.add((Class<?>)actualTypeArgument);
			}
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGenericClassList", e);
		}
		
		return genericClasLsist;	
	}

	
	@SafeVarargs
	public static final <T> T[] newArray(Class<T> clazz, int length, T...valueArray) throws ProgressusException {
		try {
			
			@SuppressWarnings("unchecked")
			T[] array = (T[])Array.newInstance(clazz, length);
			
			if (CollectionHelper.isNullOrEmpty(valueArray)) {
				return array;	
			}
			
			for (int i = 0; i < valueArray.length; i++) {
				array[i] = valueArray[i];
			}
			
			return array;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newArray", e);
		}
	}
}
