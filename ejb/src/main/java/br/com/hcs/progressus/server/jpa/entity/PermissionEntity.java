package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PermissionEntity extends ProgressusEntity<PermissionEntity> implements Serializable {

	private static final long serialVersionUID = -5523880444300781930L;

	
	@Getter
	@Setter
	@Column(nullable=false)
	private String name;
	@Getter
	@Setter
	@ManyToOne(fetch=FetchType.LAZY)
	private ViewEntity view;
	
	
	public PermissionEntity(String name) {
		this();
		this.setName(name);
	}
	
	
	public String getFullName() {
		
		try {
			
			String name = 
				StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
				
			name = name.replaceAll("\\.", Setting.SEPARATOR.toString()); 
			
			if (this.getView() == null) {
				return name;
			}
			
			String viewName = 
				StringHelper.isNullOrEmpty(this.getView().getFullName()) ? "" : this.getView().getFullName();
			
			
			viewName = viewName.replaceAll("\\.", Setting.SEPARATOR.toString());
			
			return viewName.concat(Setting.SEPARATOR.toString()).concat(name).replaceAll("\\.", Setting.SEPARATOR.toString());
		
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	
	@Override
	public boolean equals(Object object) {
		
		try {
			
			if (this == object){ 
				return true;
			}
			
			if (object == null) {
				return false;
			}
			
			if (!this.getClass().equals(object.getClass())) {
				return false;
			}
			
			PermissionEntity other = (PermissionEntity)object;
			
			if (StringHelper.isNullOrEmpty(this.getFullName()) || StringHelper.isNullOrEmpty(other.getFullName())) {
				return super.equals(object);
			}
			
			return this.getFullName().equals(other.getFullName());
			
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		
		return super.equals(object);
	}
	
	@Override
	public int compareTo(PermissionEntity other) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(this.getName()) ||  StringHelper.isNullOrEmpty(other.getName()) ||
				this.getView() == null || other.getView() == null) {
				
				return super.compareTo(other);
				
			}
			
			return this.getFullName().compareTo(other.getFullName());
			
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		
		return super.compareTo(other);
	}

}
