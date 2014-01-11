package br.com.hcs.progressus.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.hcs.progressus.jpa.entity.common.NameDescriptionEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class ItemMenuEntity extends NameDescriptionEntity<ItemMenuEntity> {

	private static final long serialVersionUID = 6987473803498918793L;

	
	@Getter
	@Setter
	@ManyToOne
	private MenuEntity menu;
	
	@Getter
	@Setter
	@OneToOne
	private ViewEntity view;
	
	
	public ItemMenuEntity() {
		super();
	}
   
}
