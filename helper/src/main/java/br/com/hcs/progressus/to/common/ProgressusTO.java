package br.com.hcs.progressus.to.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.contract.ClassInformer;
import br.com.hcs.progressus.contract.ClassValidator;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;

import com.google.gson.Gson;

public class ProgressusTO<T extends ProgressusTO<T>> 
	implements 
		Serializable,
		ClassInformer, 
		Comparable<T> 
{
	
	private static final long serialVersionUID = -6638017139073613384L;
	private static Logger logger = LoggerFactory.getLogger(ProgressusTO.class);

	
	public ProgressusTO() { super(); }
	
	
	public Object get(String fieldName) {
		
		try {
			
			fieldName =
				fieldName == null ?
					fieldName :
					fieldName.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			String getterMethodName = StringHelper.getGetter(fieldName);
			
			if (StringHelper.isNullOrEmpty(getterMethodName)) {
				return null;
			}
			
			Method method = ReflectionHelper.findMethod(this.getClass(), getterMethodName);
			
			if (method == null) {
				return null;
			}
			
			return ReflectionHelper.executeMethod(this, method);
		
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public void set(String fieldName, Object value) {
		try {
			
			fieldName =
				fieldName == null ?
					fieldName :
					fieldName.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return;
			}
			
			String setterMethodName = StringHelper.getSetter(fieldName);
			
			if (StringHelper.isNullOrEmpty(setterMethodName)) {
				return;
			}
			
			Method method = ReflectionHelper.findMethod(this.getClass(), setterMethodName);
			
			if (method == null) {
				return;
			}
					
			ReflectionHelper.executeMethod(this, method, value);
			
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
	}
	
	@Override
	public List<Field> getPublicFieldList() {
		
		try {
			
			return this.getPublicFieldList(this);
			
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return new ArrayList<>();
		
	}
	
	@Override
	public List<Field> getPublicFieldList(ClassValidator validator) {
		
		try {
			
			Class<?> clazz = this.getClass();
			
			if (clazz == null) {
				return new ArrayList<>();
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
			
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return new ArrayList<>();
	}
	
	@Override
	public boolean isValidClass(Class<?> clazz) {
		
		try {
		
			return  clazz!= null &&  clazz.equals(Object.class);
		
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return false;
	}

	@Override
	public String toString() {
		
		try {
			
			return new Gson().toJson(this);
			
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return super.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(object)) {
				return false;
			}
			
			if (!(object instanceof ProgressusTO)) {
				return false;
			}
			
			return this.toString().equals(object.toString());

		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		
		try {
			return this.toString().hashCode();
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return super.hashCode();
	}
	
	@Override
	public int compareTo(T other) {
		
		try {
			
			return this.equals(other) ? 0 : this.toString().compareTo(other.toString());
		
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return -1;
	}

	
	@SuppressWarnings("unchecked")
	public static <X extends ProgressusTO<X>> X getInstance(Class<X> clazz) {
		
		try {
			
			return (X) ReflectionHelper.newInstance(clazz);
			
		} catch (Exception e) {
			ProgressusTO.logger.warn(e.getMessage());
		}
		
		return null;
		
	}
}
