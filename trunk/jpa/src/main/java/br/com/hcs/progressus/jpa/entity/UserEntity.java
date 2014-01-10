package br.com.hcs.progressus.jpa.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import br.com.hcs.progressus.jpa.entity.common.NameEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UserEntity extends NameEntity<UserEntity> {

	
	private static final long serialVersionUID = 4226577378191382053L;

	@Getter
	@Setter
	@ManyToMany
	private List<RoleEntity> roleList;
	
	public UserEntity() {
		super();
	}
   
}
