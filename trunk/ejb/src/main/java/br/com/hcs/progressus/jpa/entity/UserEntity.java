package br.com.hcs.progressus.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Setter;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.jpa.entity.common.NameEntity;

@Entity
public class UserEntity extends NameEntity<UserEntity> {

	
	private static final long serialVersionUID = -7848518728566550187L;
	
	
	@Setter
	@ManyToMany
	private List<RoleEntity> roleList;
	
	
	
	public UserEntity() {
		super();
	}
   
	
	public List<RoleEntity> getRoleList() {
		if (CollectionHelper.isNullOrEmpty(this.roleList)) {
			this.setRoleList(new ArrayList<RoleEntity>());
		}
		return roleList;
	}
}
