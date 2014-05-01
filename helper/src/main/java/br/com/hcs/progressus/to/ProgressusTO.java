package br.com.hcs.progressus.to;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.CompareToBuilder;

import br.com.hcs.progressus.annotation.Display;
import br.com.hcs.progressus.contract.ClassInformer;
import br.com.hcs.progressus.contract.ClassValidator;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.JSONSerializerHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Slf4j
@NoArgsConstructor
public abstract class ProgressusTO<T extends ProgressusTO<T>> 
	implements
		Comparable<T>,
		ClassInformer 
{
	
	private static final long serialVersionUID = -6944548484149684319L;
	
	@Setter
	@Transient
	private Map<String, Object> descriptionMap;
	
	
	private final Map<String, Object> getDescriptionMap() {
		try {
			
			if (this.descriptionMap == null) {
				this.setDescriptionMap(this.newDescriptionMap());
			}
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		
		return this.descriptionMap; 
	}
	
	
	public final Object get(String fieldName) {
		
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
			ProgressusTO.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public final void set(String fieldName, Object value) {
		
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
			ProgressusTO.log.error(e.getMessage(), e);
		}
	}
		
	
	@Override
	public int compareTo(T other) {
		try {
			if (other == null) {
				return 1;
			}
			return 
				new CompareToBuilder()
					.append(this.toString(), other.toString())
					.toComparison();
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return 1;
	}
	
	
	@Override
	public boolean isValidClass(Class<?> clazz) {
		try {
			return clazz!= null && clazz.equals(Object.class);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	
	@Override
	public final List<Field> getPublicFieldList() {
		try {
			return this.getPublicFieldList(this);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}
	
	@Override
	public final List<Field> getPublicFieldList(ClassValidator validator) {
		try {
			return ReflectionHelper.getPublicFieldList(this.getClass(), validator);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

	
	@SuppressWarnings("unchecked")
	private final Map<String, Object> newDescriptionMap() throws ProgressusException {
		
		try {
			
			Map<String, Object> selectColumnMap = new HashMap<>();
			
			Class<?> clazz = ((T)this).getClass();
			
			while (clazz != null) {
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (!field.isAnnotationPresent(Display.class)) {
						continue;
					}
					
					selectColumnMap.put(field.getName(), ReflectionHelper.executeGetter(this, field));
				}
				
				if (this.isValidClass(clazz.getSuperclass())) {
					clazz = clazz.getSuperclass();
					continue;
				}
				
				clazz = null;
			}
			
			return selectColumnMap;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("newDescriptionMap");
		} 
	}
	
	
	public final Object getDescriptionValue(String descriptionFieldName) throws ProgressusException {
		try {
			return this.getDescriptionMap().get(descriptionFieldName);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getDescriptionValue");
		}
	}
	
	
	public final List<String> getDescriptionList() throws ProgressusException {
		try {
			return new ArrayList<>(this.getDescriptionMap().keySet());
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getDescriptionList");
		}
	}
	
	
	public final <X> X parse(Class<X> clazz) {
		try {
			return ReflectionHelper.parse(this, clazz);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return null;
	}

	
	public final GsonBuilder getGsonBuilder() {
		try {
			return new GsonBuilder().registerTypeAdapter(this.getClass(), new JSONSerializerHelper<T>());
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public final Gson getGson() {
		try {
			return this.getGsonBuilder().create();
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		try {
			return this.getGson().toJson(this);
		} catch (Exception e) {
			ProgressusTO.log.error(e.getMessage(), e);
		}
		return super.toString();
	}
}
