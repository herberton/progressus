package br.com.hcs.progressus.jpa.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.hcs.progressus.jpa.entity.common.NameDescriptionEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class MenuEntity extends NameDescriptionEntity<MenuEntity> {

	private static final long serialVersionUID = -254966210404674046L;

	@Getter
	@Setter
	@OneToMany(mappedBy="menu")
	private List<ItemMenuEntity> itemMenuList;
	
	@Getter
	@Setter
	@ManyToOne
	private MenuEntity parent;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="parent")
	private List<MenuEntity> childList;
	
	public MenuEntity() {
		super();
	}
}
