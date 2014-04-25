package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.CollectionHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoleEntity extends ProgressusEntity<RoleEntity> implements Serializable {

	private static final long serialVersionUID = -3589734081088659255L;

	
	@Getter
	@Setter
	@Column(nullable=false)
	private String name;
	@Getter
	@Setter
	@Column(nullable=true)
	private String description;
	@Setter
	@ManyToMany
	private List<UserEntity> userList;
	@Setter
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	private List<PermissionEntity> permissionList;
	
	
	public List<UserEntity> getUserList() {
		if (this.userList == null) {
			this.setUserList(new ArrayList<UserEntity>());
		}
		return this.userList;
	}
	
	public List<PermissionEntity> getPermissionList() {
		if (this.permissionList == null) {
			this.setPermissionList(new ArrayList<PermissionEntity>());
		}
		return this.permissionList;
	}
	
	
	public void addUser(UserEntity user){
		try {
			CollectionHelper.add(this.getUserList(), user);
			CollectionHelper.add(user.getRoleList(), this);
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void addUser(List<UserEntity> userList){
		try {
			if (userList != null) {
				for (UserEntity User : userList) {
					this.addUser(User);
				}
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void addUser(UserEntity...userArray){
		try {
			if (userArray != null) {
				this.addUser(Arrays.asList(userArray));
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	
	public void addPermission(PermissionEntity permission){
		try {
			CollectionHelper.add(true, this.getPermissionList(), permission);
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void addPermission(List<PermissionEntity> permissionList){
		try {
			for (PermissionEntity permission : permissionList) {
				this.addPermission(permission);
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void addPermission(PermissionEntity...permissionArray){
		try {
			for (PermissionEntity permission : permissionArray) {
				this.addPermission(permission);
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	
	public void removeUser(UserEntity user){
		try {
			CollectionHelper.remove(this.getUserList(), user);
			CollectionHelper.remove(user.getRoleList(), this);
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void removeUser(List<UserEntity> userList){
		try {
			if (userList != null) {
				for (UserEntity User : userList) {
					this.removeUser(User);
				}
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void removeUser(UserEntity...userArray){
		try {
			if (userArray != null) {
				this.removeUser(Arrays.asList(userArray));
			}
		} catch (Exception e) {
			RoleEntity.log.error(e.getMessage(), e);
		}
	}
}
