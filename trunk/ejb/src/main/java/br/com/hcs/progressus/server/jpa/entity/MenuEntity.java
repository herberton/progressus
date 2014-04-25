package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuEntity extends ProgressusEntity<MenuEntity> implements Serializable {

	private static final long serialVersionUID = 8490521196480203372L;

	
	@Getter
	@Column(nullable=false)
	private String name;
	@Setter
	@Column(nullable=false)
	private String icon;
	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	private Separator separator;
	@Getter
	@Setter
	@ManyToOne
	private MenuEntity parentMenu;
	@Setter
	@OneToMany(mappedBy = "parentMenu", cascade=CascadeType.ALL)
	private List<MenuEntity> childMenuList;
	@Setter
	@OneToMany(mappedBy = "parentMenu", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ItemMenuEntity> itemMenuList;
	
	
	public MenuEntity(String name) {
		this();
		this.setName(name);
	}
	
	
	public void setName(String name) {
		
		try {
			
			this.name = name;
			
			if (!StringHelper.isNullOrEmpty(this.getName())) {
				this.setIcon(Setting.WEB_MENU_ICON_FORMAT.toString().replace("[menu_name]", this.getName()));
			}
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
	}
	
	public String getIcon() {
		
		try {
		
			if (StringHelper.isNullOrEmpty(this.icon)) {
				this.setIcon(Setting.WEB_MENU_ICON_FORMAT.toString().replace("[menu_name]", "default"));
			}
			
			return this.icon;
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	public List<MenuEntity> getChildMenuList() {
		if (this.childMenuList == null) {
			this.setChildMenuList(new ArrayList<MenuEntity>());
		}
		return this.childMenuList;
	}
	
	public List<ItemMenuEntity> getItemMenuList() {
		if (this.itemMenuList == null) {
			this.setItemMenuList(new ArrayList<ItemMenuEntity>());
		}
		return this.itemMenuList;
	}
	
	
	public void addChildMenu(MenuEntity childMenu){
		
		try {
			
			if(this.getChildMenu(childMenu) != null) {
				return;
			}
			
			childMenu.setParentMenu(this);
			
			this.getChildMenuList().add(childMenu);
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void addItemMenu(ItemMenuEntity itemMenu){
		
		try {
			
			if (this.getItemMenu(itemMenu) != null) {
				return;
			}
			
			itemMenu.setParentMenu(this);
			
			this.getItemMenuList().add(itemMenu);
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
	}
	
	public MenuEntity getChildMenu(MenuEntity menu){
		
		try {
			
			if (menu == null) {
				return null;
			}
			
			for (MenuEntity child : this.getChildMenuList()) {
				if (menu.equals(child)) {
					return child;
				}
			}
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public ItemMenuEntity getItemMenu(ItemMenuEntity itemMenu){
		
		try {
			
			if (itemMenu == null) {
				return null;
			}
			
			for (ItemMenuEntity child : this.getItemMenuList()) {
				if (itemMenu.equals(child)) {
					return child;
				}
			}
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public void setChildMenu(MenuEntity childMenu, boolean addNew) {
		
		try {
			
			if (this.getChildMenu(childMenu) == null) {
				
				if (addNew) {
					this.addChildMenu(childMenu);
				}
				
				return;
			}
			
			this.getChildMenuList().set(this.getChildMenuList().indexOf(childMenu), childMenu);
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
	}
	
	public void setItemMenu(ItemMenuEntity itemMenu, boolean addNew) {
		
		try {
			
			if (this.getItemMenu(itemMenu) == null) {
				
				if (addNew) {
					this.addItemMenu(itemMenu);
				}
				
				return;
			}
			
			this.getItemMenuList().set(this.getItemMenuList().indexOf(itemMenu), itemMenu);
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
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
			
			if (!(object instanceof MenuEntity)) {
				return false;
			}
			
			MenuEntity other = (MenuEntity)object;
			
			if (StringHelper.isNullOrEmpty(this.getName()) || StringHelper.isNullOrEmpty(other.getName())) {
				return super.equals(object);
			}
			
			return this.getName().equals(other.getName());
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return super.equals(object);
	} 
	
	@Override
	public int compareTo(MenuEntity other) {
		
		try {
			
			if (other == null) {
				return -1;
			}
			
			if (!StringHelper.isNullOrEmpty(other.getName()) && !StringHelper.isNullOrEmpty(this.getName())) {
				return this.getName().compareTo(other.getName());
			}
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return super.compareTo(other);
	}
}
