package br.com.hcs.progressus.jpa.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import br.com.hcs.progressus.jpa.entity.common.NameDescriptionEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class RoleEntity extends NameDescriptionEntity<RoleEntity> {
	
	private static final long serialVersionUID = -3824555279233824994L;

	
	@Getter
	@Setter
	@ManyToMany
	private List<PermissionEntity> permissionList;
	
	
	public RoleEntity() {
		super();
	}
   
}
