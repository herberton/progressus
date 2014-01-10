package br.com.hcs.progressus.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.hcs.progressus.jpa.entity.common.NameDescriptionEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PermissionEntity extends NameDescriptionEntity<PermissionEntity> {

	private static final long serialVersionUID = -2804057138731381299L;
	
	
	@Getter
	@Setter
	@ManyToOne
	private ViewEntity view;
	
	
	public PermissionEntity() {
		super();
	}
}
