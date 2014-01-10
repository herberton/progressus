package br.com.hcs.progressus.to.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import br.com.hcs.progressus.contract.ClassInformer;
import br.com.hcs.progressus.contract.ClassValidator;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;

public class ProgressusTO<T extends ProgressusTO<T>> 
	implements 
		Serializable,
		ClassInformer
{

	private static final long serialVersionUID = -6638017139073613384L;
	
	
	public ProgressusTO() { super(); }

	
	public Object get(String fieldName) {
		return ReflectionHelper.executeMethod(this, ReflectionHelper.findMethod(this.getClass(), StringHelper.getGetter(fieldName)));
	}
	
	public void set(String fieldName, Object value) {
		ReflectionHelper.executeMethod(this, ReflectionHelper.findMethod(this.getClass(), StringHelper.getSetter(fieldName)), value);
	}
	
	@Override
	public List<Field> getPublicFieldList() {
		return this.getPublicFieldList(this);
	}
	@Override
	public List<Field> getPublicFieldList(ClassValidator validator) {
		
		Class<?> clazz = this.getClass();
		
		List<Field> fieldList = new ArrayList<Field>();
		
		do {
			
			
			for (Field field : clazz.getDeclaredFields()) {
				
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
	}
	
	@Override
	public boolean isValidClass(Class<?> clazz) {
		return 
			clazz!= null && 
			clazz.equals(Object.class);
	}
}
