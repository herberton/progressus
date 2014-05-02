package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuEntity extends ProgressusEntity<MenuEntity> implements Serializable {

	private static final long serialVersionUID = 8490521196480203372L;

	
	public MenuEntity(String name) throws ProgressusException {
		this();
		this.setName(name);
	}
	
	
	@Getter
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private String icon;
	@Getter
	@ManyToOne
	private MenuEntity parentMenu;
	@OneToMany(mappedBy = "parentMenu", cascade=CascadeType.ALL)
	private List<MenuEntity> childMenuList;
	@OneToMany(mappedBy = "parentMenu", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ItemMenuEntity> itemMenuList;

	
	public void setName(String name) throws ProgressusException {
		this.name = name;
		this.updateIcon();
	}
	
	
	public String getIcon() throws ProgressusException {
		this.updateIcon();
		return this.icon;
	}

	protected void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	public void setParentMenu(MenuEntity parentMenu) throws ProgressusException {
		this.parentMenu = parentMenu;
		try {
			if (this.getParentMenu() == null) {
				return;
			}
			if (this.getParentMenu().getChildMenuList().contains(this)) {
				return;
			}
			this.getParentMenu().getChildMenuList().add(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("setParentMenu", e);
		}
	}
	
	
	public List<MenuEntity> getChildMenuList() throws ProgressusException {
		try {
			if (this.childMenuList == null) {
				this.setChildMenuList(new ArrayList<MenuEntity>());
			}
			return this.childMenuList;
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("getChildMenuList", e);
		}
	}
	
	public void setChildMenuList(List<MenuEntity> childMenuList) throws ProgressusException {
		try {
			this.childMenuList = childMenuList;
			for (MenuEntity childMenu : this.getChildMenuList()) {
				if (childMenu.getParentMenu() == null) {
					childMenu.setParentMenu(this);
					continue;
				}
				if (childMenu.getParentMenu().equals(this)) {
					continue;
				}
				childMenu.setParentMenu(this);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("setChildMenuList", e);
		}
	}
	
	
	public List<ItemMenuEntity> getItemMenuList() throws ProgressusException {
		if (this.itemMenuList == null) {
			this.setItemMenuList(new ArrayList<ItemMenuEntity>());
		}
		return this.itemMenuList;
	}
	
	public void setItemMenuList(List<ItemMenuEntity> itemMenuList) throws ProgressusException {
		try {
			this.itemMenuList = itemMenuList;
			for (ItemMenuEntity itemMenu : this.getItemMenuList()) {
				if (itemMenu.getParentMenu() == null) {
					itemMenu.setParentMenu(this);
					continue;
				}
				if (itemMenu.getParentMenu().equals(this)) {
					continue;
				}
				itemMenu.setParentMenu(this);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("setChildMenuList", e);
		}
	}
	
	
	private void updateIcon() throws ProgressusException {
		if (StringHelper.isNullOrEmpty(this.icon)) {
			if (StringHelper.isNullOrEmpty(this.getName())) {
				this.setIcon(Setting.WEB_MENU_ICON_FORMAT.toString().replace("[menu_name]", "default"));
			} else {
				this.setIcon(Setting.WEB_MENU_ICON_FORMAT.toString().replace("[menu_name]", this.getName()));
			}
		}
	}
	
	
	public void addChildMenu(MenuEntity childMenu) throws ProgressusException {
		ValidatorHelper.validateFilling(MenuEntity.class, childMenu);
		try {
			this.getChildMenuList().add(childMenu);
			childMenu.setParentMenu(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addChildMenu", e);
		}
	}
	
	public void addItemMenu(ItemMenuEntity itemMenu) throws ProgressusException {
		ValidatorHelper.validateFilling(ItemMenuEntity.class, itemMenu);
		try {
			this.getItemMenuList().add(itemMenu);
			itemMenu.setParentMenu(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addItemMenu", e);
		}
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(this.getParentMenu())) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			String string = this.getParentMenu().toString();
			
			if (StringHelper.isNullOrEmpty(this.getName())) {
				return StringHelper.isNullOrEmpty(string) ? "" : string;
			}
			
			if (StringHelper.isNullOrEmpty(string)) {
				return this.getName();
			}
			
			return string.concat(".").concat(this.getName());
			
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			if (!(object instanceof MenuEntity)) {
				return false;
			}
			return 
				new EqualsBuilder()
					.append(this.getClass(), object.getClass())
					.append(this.toString(), object.toString())
					.isEquals();
		} catch (Exception e) {
			MenuEntity.log.error(e.getMessage(), e);
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
			MenuEntity.log.error(e.getMessage(), e);
		}
		return super.hashCode();
	}

	
	public static MenuEntity getInstance(String menu, String view, Separator separator, String...permissionArray) throws ProgressusException {
		
		try {
		
			if (StringHelper.isNullOrEmpty(menu)) {
				return null;
			}
			
			String[] menuArray = menu.split("\\.");
			
			if (CollectionHelper.isNullOrEmpty(menuArray)) {
				return null;
			}
			
			MenuEntity entity = new MenuEntity(menuArray[0]);
			
			menu = menu.replace(entity.getName(), "");
			
			if (menu.length() > 0 && menu.charAt(0) == '.') {
				menu = menu.substring(1);
			}
			
			if (!StringHelper.isNullOrEmpty(menu)) {
				entity.addChildMenu(MenuEntity.getInstance(menu, view, separator, permissionArray));
				return entity;
			}
			
			if (StringHelper.isNullOrEmpty(view)) {
				return entity;
			}
			
			entity.addItemMenu(ItemMenuEntity.getInstance(view, separator, permissionArray));
			return entity;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getInstance", e);
		}
	}
	
	public static List<MenuEntity> addMenuInList(List<MenuEntity> menuList, MenuEntity menu) throws ProgressusException {	
		
		try {
		
			if (menuList == null) {
				menuList = new ArrayList<>();
			}
			
			if (menu == null) {
				return menuList;
			}
			
			if (menuList.contains(menu)) {
				
				int index = menuList.indexOf(menu);
				
				for (MenuEntity childMenu : menu.getChildMenuList()) {
					
					menuList.get(index).setChildMenuList(MenuEntity.addMenuInList(menuList.get(index).getChildMenuList(), childMenu));
					
				}
				
				for (ItemMenuEntity itemMenu : menu.getItemMenuList()) {
					
					menuList.get(index).setItemMenuList(ItemMenuEntity.addItemMenuInList(menuList.get(index).getItemMenuList(), itemMenu));
					
				}
				
				return menuList;
				
			}
			
			menuList.add(menu);
			
			return menuList;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("addMenuInList", e);
		}
	}
}