package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PermissionEntity extends ProgressusEntity<PermissionEntity> implements Serializable {

	private static final long serialVersionUID = -5523880444300781930L;

	
	public PermissionEntity(String name) {
		this.setName(name);
	}
	
	
	@Getter
	@Setter
	@Column(nullable=false)
	private String name;
	@Getter
	@ManyToOne(fetch=FetchType.LAZY)
	private ViewEntity view;
	
	
	public void setView(ViewEntity view) throws ProgressusException {
		this.view = view;
		try {
			if (this.getView() == null) {
				return;
			}
			if (this.getView().getPermissionList().contains(this)) {
				return;
			}
			this.getView().getPermissionList().add(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("setView");
		}
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(this.getView())) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			String string = this.getView().toString();
			
			if (StringHelper.isNullOrEmpty(string)) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			if (StringHelper.isNullOrEmpty(this.getName())) {
				return string;
			}
			
			return string.concat(".").concat(this.getName());
			
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			if (!(object instanceof PermissionEntity)) {
				return false;
			}
			return 
				new EqualsBuilder()
					.append(this.getClass(), object.getClass())
					.append(this.toString(), object.toString())
					.isEquals();
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		try {
			return 
				new HashCodeBuilder()
					.append(this.getClass())
					.append(this.toString())
					.toHashCode();
		} catch (Exception e) {
			PermissionEntity.log.error(e.getMessage(), e);
		}
		return super.hashCode();
	}
	
	
	public static List<PermissionEntity> addPermissionInList(List<PermissionEntity> permissionList, PermissionEntity permission) throws ProgressusException {
		
		try {
			
			if (permissionList == null) {
				permissionList = new ArrayList<>();
			}
			
			if (permission == null) {
				return permissionList;
			}
			
			if (!permissionList.contains(permission)) {
				permissionList.add(permission);
			}
			
			return permissionList;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addPermissionInList");
		}
	}
}
