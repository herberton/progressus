package br.com.hcs.progressus.server.jpa.entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.hcs.progressus.annotation.Display;
import br.com.hcs.progressus.enumerator.EntityStatus;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.server.jpa.listener.ProgressusEntityListener;
import br.com.hcs.progressus.to.ProgressusTO;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({ProgressusEntityListener.class})
public class ProgressusEntity<T extends ProgressusEntity<T>> 
	extends 
		ProgressusTO<T> 
{

	private static final long serialVersionUID = 8095783625381901237L;
   
	@Display
	@Getter
	@Setter
	@NonNull
	@Id
	@GeneratedValue
	private Long id;
	@Getter
	@Setter
	@NonNull
	@Enumerated
	private EntityStatus entityStatus;
	
	@Getter
	@Setter
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	@Getter
	@Setter
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar modificationDate;
	@Getter
	@Setter
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar deletionDate;


	public final boolean hasId(){
		return
			this.getId() !=  null && 
			this.getId() != 0;
	}
	
	
	public Map<String, Object> toParameterMap(String...fieldKeyArray) {
		
		try {
			
			if (fieldKeyArray == null) {
				fieldKeyArray = new String[]{};
			}
			
			List<String> fieldKeyList = Arrays.asList(fieldKeyArray);
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			
			if (this.hasId()) {
				
				String parameter = "id";
				Object value = this.getId();
				
				if (CollectionHelper.isNullOrEmpty(fieldKeyList)) {
					parameterMap.put(parameter, value);
					return parameterMap;
				}
				
				if (fieldKeyList.contains(parameter)) {
					parameterMap.put(parameter, value);
					return parameterMap;
				}
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
						
						String parameter = field.getName().concat(".").concat(key);
						
						if (CollectionHelper.isNullOrEmpty(fieldKeyList)) {
							parameterMap.put(parameter, subParameterMap.get(key));
							continue;
						}
						
						if (fieldKeyList.contains(parameter)) {
							parameterMap.put(parameter, subParameterMap.get(key));
							continue;
						}
					}
					
					continue;
				}
				
				String parameter = field.getName();
				
				if (CollectionHelper.isNullOrEmpty(fieldKeyList)) {
					parameterMap.put(parameter, value);
					continue;
				}
				
				if (fieldKeyList.contains(parameter)) {
					parameterMap.put(parameter, value);
					continue;
				}
			}
			
			return parameterMap;
			
		} catch (Exception e) {
			ProgressusEntity.log.error(e.getMessage(), e);
		}
		
		return new HashMap<>();
	}


	@Override
	public boolean isValidClass(Class<?> clazz) {
		try {
			return 
				clazz != null && (clazz.isAnnotationPresent(Entity.class) || clazz.isAnnotationPresent(MappedSuperclass.class));
		} catch (Exception e) {
			ProgressusEntity.log.error(e.getMessage(), e);
		}
		return false; 
	}

	
	public final boolean isActive() {
		return this.getEntityStatus().equals(EntityStatus.ACTIVE);
	}
	
	public final boolean isInactive() {
		return this.getEntityStatus().equals(EntityStatus.INACTIVE);
	}

	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object object) {
		try {
			if (!(object instanceof ProgressusEntity)) {
				return false;
			} 
			return 
				new EqualsBuilder()
					.append(this.getClass(), object.getClass())
					.append(this.getId(), ((ProgressusEntity)object).getId())
					.isEquals();
		} catch (Exception e) {
			ProgressusEntity.log.error(e.getMessage(), e);
		}
		return super.equals(object);
	}
	
	
	@Override
	public int hashCode() {
		try {
			return 
				new HashCodeBuilder(17, 37)
					.append(this.getClass())
					.append(this.getId())
					.toHashCode();
		} catch (Exception e) {
			ProgressusEntity.log.error(e.getMessage(), e);
		}
		return super.hashCode();
	}
}
