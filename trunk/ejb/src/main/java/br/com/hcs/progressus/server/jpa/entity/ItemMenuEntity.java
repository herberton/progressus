package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemMenuEntity extends ViewEntity implements Serializable {

	private static final long serialVersionUID = -464900830512690449L;

	
	public ItemMenuEntity(String name, Separator separator) throws ProgressusException {
		super(name);
		this.setSeparator(separator);
	}
	
	
	@Column(nullable=false)
	private String icon;
	@Setter
	@Enumerated
	private Separator separator;
	@Getter
	@ManyToOne
	private MenuEntity parentMenu;
	
	
	@Override
	@Deprecated
	public ViewEntity getParentView() {
		return null;
	}
	
	@Override
	@Deprecated
	public void setParentView(ViewEntity parentView) throws ProgressusException{
		throw new UnableToCompleteOperationException("setParentView", "itemMenuEntityDoesNotHaveParentView");
	}
	
	
	public void setParentMenu(MenuEntity parentMenu) throws ProgressusException {
		this.parentMenu = parentMenu;
		try {
			if (this.getParentMenu() == null) {
				return;
			}
			if (this.getParentMenu().getItemMenuList().contains(this)) {
				return;
			}
			this.getParentMenu().getItemMenuList().add(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("setParentMenu", e);
		}
	}
	
	
	@Override
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
	
	
	public Separator getSeparator() {
		if (this.separator == null) {
			this.separator = Separator.NONE;
		}
		return this.separator;
	}
	
	
	private void updateIcon() throws ProgressusException {
		if (StringHelper.isNullOrEmpty(this.icon)) {
			if (StringHelper.isNullOrEmpty(this.getName())) {
				this.setIcon(Setting.WEB_ITEM_MENU_ICON_FORMAT.toString().replace("[item_menu_name]", "default"));
			} else {
				this.setIcon(Setting.WEB_ITEM_MENU_ICON_FORMAT.toString().replace("[item_menu_name]", this.getName()));
			}
		}
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(this.getParentMenu())) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			String string = this.getParentMenu().toString();
			
			if (StringHelper.isNullOrEmpty(string)) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			if (StringHelper.isNullOrEmpty(this.getName())) {
				return string;
			}
			
			return string.concat(".").concat(this.getName());
			
		} catch (Exception e) {
			ItemMenuEntity.log.error(e.getMessage(), e);
		}
		
		return "";		
	}

	
	public static ItemMenuEntity getInstance(String view, Separator separator, String...permissionArray) throws ProgressusException {

		try {
			
			if (StringHelper.isNullOrEmpty(view)) {
				return null;
			}
			
			String[] viewArray = view.split("\\.");
			
			if (CollectionHelper.isNullOrEmpty(viewArray)) {
				return null;
			}
			
			ItemMenuEntity entity = new ItemMenuEntity(viewArray[0], separator);
			
			view = view.replace(entity.getName(), "");
			
			if (view.length() > 0 && view.charAt(0) == '.') {
				view = view.substring(1);
			}
			
			if (!StringHelper.isNullOrEmpty(view)) {
				entity.addChildView(ViewEntity.getInstance(view, permissionArray));
				return entity;
			}
			
			if (CollectionHelper.isNullOrEmpty(permissionArray)) {
				return entity;
			}
			
			for (String permission : permissionArray) {
				entity.addPermission(new PermissionEntity(permission));
			}
			
			return entity;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getInstance", e);
		}
		
	}
	
	public static List<ItemMenuEntity> addItemMenuInList(List<ItemMenuEntity> itemMenuList, ItemMenuEntity itemMenu) throws ProgressusException {
		
		try {
			
			if (itemMenuList == null) {
				itemMenuList = new ArrayList<>();
			}
			
			if (itemMenu == null) {
				return itemMenuList;
			}
			
			if (itemMenuList.contains(itemMenu)) {
				
				int index = itemMenuList.indexOf(itemMenu);
				
				for (ViewEntity view : itemMenu.getChildViewList()) {
					
					itemMenuList.get(index).setChildViewList(ViewEntity.addViewInList(itemMenuList.get(index).getChildViewList(), view));
					
				}
				
				for (PermissionEntity permission : itemMenu.getPermissionList()) {
					
					itemMenuList.get(index).setPermissionList(PermissionEntity.addPermissionInList(itemMenuList.get(index).getPermissionList(), permission));
					
				}
				
				return itemMenuList;
			}
			
			itemMenuList.add(itemMenu);
			
			return itemMenuList;
		
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addItemMenuInList", e);
		}
	}
}
