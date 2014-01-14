package br.com.hcs.progressus.jpa.entity.common;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.annotation.Audited;
import br.com.hcs.progressus.annotation.Display;
import br.com.hcs.progressus.contract.ClassInformer;
import br.com.hcs.progressus.enumerator.Status;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.jpa.el.AuditEL;
import br.com.hcs.progressus.jpa.entity.AuditEntity;
import br.com.hcs.progressus.jpa.entity.UserEntity;
import br.com.hcs.progressus.to.common.ProgressusTO;

@Audited
@MappedSuperclass
@EntityListeners({AuditEL.class})
public class ProgressusEntity<T extends ProgressusEntity<T>> 
	extends 
		ProgressusTO<T> 
	implements
		ClassInformer
{

	
	private static final long serialVersionUID = 1873055400152243177L;

	
	@Getter
	@Setter
	@Display
	@Id
	@GeneratedValue
	private long id;
	@Getter
	@Setter
	@Column(nullable=false)
	@Enumerated
	private Status status;
	@Getter
	@Setter
	@OneToOne(optional=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private AuditEntity audit;
	
	@Getter
	@Setter
	@Transient
	private UserEntity loggedUser;
	
	
	public ProgressusEntity() { super(); }
	
	
	public boolean haveId() {
		return this.getId() > 0L;
	}
	
	public Map<String, Object> toParameterMap() throws ProgressusException {
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		if (this.haveId()) {
			parameterMap.put("id", this.getId());
			return parameterMap;
		}
		
		List<Field> fieldList = this.getPublicFieldList();
		
		for (Field field : fieldList) {
			
			Object value = null;
			
			value = this.get(field.getName());
			
			if (ObjectHelper.isNullOrEmpty(value) || value instanceof Collection) {
				continue;
			}
			
			if (value instanceof ProgressusEntity) {
				
				@SuppressWarnings("unchecked")
				ProgressusEntity<? extends ProgressusEntity<? extends ProgressusEntity<?>>> child = 
					(ProgressusEntity<? extends ProgressusEntity<? extends ProgressusEntity<?>>>)value;
				
				Map<String, Object> subParameterMap = child.toParameterMap();
				
				for (String key : subParameterMap.keySet()) {
					parameterMap.put(field.getName().concat(".").concat(key), subParameterMap.get(key));
				}
				
				continue;
			}
			
			parameterMap.put(field.getName(), value);
		}
		
		return parameterMap;
	}
	
	public boolean isAudited() {
		return 
			this.getClass().isAnnotationPresent(Audited.class) && 
			this.getClass().getAnnotation(Audited.class).value();
	}
	
	@Override
	public boolean isValidClass(Class<?> clazz) {
		return 
			clazz != null &&
			clazz.isAnnotationPresent(Entity.class) ||
			clazz.isAnnotationPresent(MappedSuperclass.class);
	}
}