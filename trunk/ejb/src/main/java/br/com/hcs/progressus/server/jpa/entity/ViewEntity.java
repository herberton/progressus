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
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ViewEntity extends ProgressusEntity<ViewEntity> implements Serializable {

	private static final long serialVersionUID = 482314050877548896L;

	
	@Getter
	@Setter
	@Column(nullable=false)
	private String name;
	@Getter
	@Setter
	@Column
	private String description;
	@Getter
	@OneToOne(mappedBy="view", fetch=FetchType.LAZY)
	private ItemMenuEntity itemMenu;
	@Getter
	@ManyToOne
	private ViewEntity parentView;
	@Setter
	@OneToMany(mappedBy="parentView", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ViewEntity> childViewList;
	@Setter
	@OneToMany(mappedBy="view", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<PermissionEntity> permissionList;

	
	public ViewEntity(Long id) {
		this();
		this.setId(id);
	}
	
	public ViewEntity(String name) {
		this();
		this.setName(name);
	}
	
	public ViewEntity(String name, String description) {
		this(name);
		this.setDescription(description);
	}
	
	public ViewEntity(String name, String description, PermissionEntity...permissionArray) {
	   this(name, description);
	   this.addPermision(permissionArray);
   }
	
		
	public void setItemMenu(ItemMenuEntity itemMenu) {
		
		try {
			
			if (this.itemMenu == null && itemMenu == null) {
				return;
			}	
			
			if(this.itemMenu != null && itemMenu == null) {
				this.itemMenu = null;
				return;
			}		
			
			if (!itemMenu.equals(this.itemMenu)) {
				
				this.itemMenu = itemMenu;
				
				if (this.getItemMenu() != null) {
					this.getItemMenu().setView(this);	
				}
			}
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
	}
		
	public void setParentView(ViewEntity parentView) {
		
		try {
			
			this.parentView = parentView;
			
			if (this.getParentView() == null) {
				return;
			}
			
			if (!this.getParentView().getChildViewList().contains(this)) {
				this.getParentView().getChildViewList().add(this);
			}
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
	}
	
	public List<ViewEntity> getChildViewList() {
		if (this.childViewList == null) {
			this.setChildViewList(new ArrayList<ViewEntity>());
		}
		return this.childViewList;
	}

	public List<PermissionEntity> getPermissionList() {
		if (this.permissionList == null) {
			this.setPermissionList(new ArrayList<PermissionEntity>());
		}
		return this.permissionList;
	}
	
	
	public void addPermision(PermissionEntity permission) {
		
		try {
			
			if(this.getPermission(permission) != null) {
				return;
			}
			
			permission.setView(this);
			
			this.getPermissionList().add(permission);
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		} 
	}
	
	public void addPermision(PermissionEntity...permissionArray) {
		try {
			for (PermissionEntity permission : permissionArray) {
				this.addPermision(permission);
			}
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
	}
		
	public PermissionEntity getPermission(PermissionEntity permission) {
		
		try {
			
			if (permission == null) {
				return null;
			}
			
			for (PermissionEntity child : this.getPermissionList()) {
				if (child.equals(permission)) {
					return child;
				}
			}
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public void setPermission(PermissionEntity permission, boolean addNew) {
		
		try {
			
			if (this.getPermission(permission) == null) {
				
				if (addNew) {
					this.addPermision(permission);
				}
				
				return;
			}
			
			this.getPermissionList().set(this.getPermissionList().indexOf(permission), permission);
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
	}
		
	public void addChildView(ViewEntity childView) {
		
		try {
			
			if(this.getChildView(childView) != null) {
				return;
			}
			
			childView.setParentView(this);
			
			this.getChildViewList().add(childView);
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
	}
	
	
	public ViewEntity getChildView(ViewEntity childView) {
		
		try {
			
			if (childView == null) {
				return null;
			}
			
			for (ViewEntity child : this.getChildViewList()) {
				if (child.equals(childView)) {
					return child;
				}
			}
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public void setChildView(ViewEntity childView, boolean addNew) {
		
		try {
			
			if (this.getChildView(childView) == null) {
				
				if (addNew) {
					this.addChildView(childView);
				}
				
				return;
			}
			
			this.getChildViewList().set(this.getChildViewList().indexOf(childView), childView);
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
	}
	
	public String getFullName() {
		
		try {
			
			String fullName = this.getName(); 
			
			ViewEntity parentView = this.getParentView();
			
			while (parentView != null) {
				
				fullName = parentView.getName().concat(Setting.SEPARATOR.toString()).concat(fullName);
				
				parentView = parentView.getParentView();
			}
			
			fullName = StringHelper.isNullOrEmpty(fullName) ? "" : fullName; 
			
			return fullName.replaceAll("\\.", Setting.SEPARATOR.toString());
		
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return "";
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
			
			if (!(object instanceof ViewEntity)) {
				return false;
			}
			
			ViewEntity other = (ViewEntity)object;
			
			if (StringHelper.isNullOrEmpty(this.getFullName()) || StringHelper.isNullOrEmpty(other.getFullName())) {
				return super.equals(object);
			}
			
			return this.getFullName().equals(other.getFullName());
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return super.equals(object);
	}
	
	@Override
	public int compareTo(ViewEntity other) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(this.getName()) || StringHelper.isNullOrEmpty(other.getName())) {
				
				return super.compareTo(other);
				
			}
			
			return this.getName().compareTo(other.getName());
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return super.compareTo(other);
	}
}
