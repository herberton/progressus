package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy=InheritanceType.JOINED)
@Entity
public class ViewEntity extends ProgressusEntity<ViewEntity> implements Serializable {

	private static final long serialVersionUID = 482314050877548896L;

	
	public ViewEntity(String name) throws ProgressusException {
		this.setName(name);
	}
	
	
	@Getter
	@Column(nullable=false)
	protected String name;
	@Getter
	@Setter
	@Column
	private String description;
	@Getter
	@ManyToOne
	private ViewEntity parentView;
	@OneToMany(mappedBy="parentView", cascade=CascadeType.ALL, orphanRemoval=true)
	protected List<ViewEntity> childViewList;
	@OneToMany(mappedBy="view", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<PermissionEntity> permissionList;
	
	
	public void setName(String name) throws ProgressusException {
		this.name = name;
	}
	
	public void setParentView(ViewEntity parentView) throws ProgressusException {
		this.parentView = parentView;
		try {
			if (this.getParentView() == null) {
				return;
			}
			if (this.getParentView().getChildViewList().contains(this)) {
				return;
			}
			this.getParentView().getChildViewList().add(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("setParentView", e);
		}
	}
	
	public List<ViewEntity> getChildViewList() throws ProgressusException {
		try {
			if (this.childViewList == null) {
				this.setChildViewList(new ArrayList<ViewEntity>());
			}
			return this.childViewList;
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("getChildViewList", e);
		}
	}
	
	public void setChildViewList(List<ViewEntity> childViewList) throws ProgressusException {
		try {
			this.childViewList = childViewList;
			for (ViewEntity childView : this.getChildViewList()) {
				if (childView.getParentView() == null) {
					childView.setParentView(this);
					continue;
				}
				if (childView.getParentView().equals(this)) {
					continue;
				}
				childView.setParentView(this);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("setChildViewList", e);
		}
	}
	
	public List<PermissionEntity> getPermissionList() throws ProgressusException {
		try {
			if (this.permissionList == null) {
				this.setPermissionList(new ArrayList<PermissionEntity>());
			}
			return this.permissionList;
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("getPermissionList", e);
		}
	}
	
	public void setPermissionList(List<PermissionEntity> permissionList) throws ProgressusException {
		try {
			this.permissionList = permissionList;
			for (PermissionEntity permission : this.getPermissionList()) {
				if (permission.getView() == null) {
					permission.setView(this);
					continue;
				}
				if (permission.getView().equals(this)) {
					continue;
				}
				permission.setView(this);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch(Exception e) {
			throw new UnableToCompleteOperationException("setPermissionList", e);
		}
	}
	
	
	public void addChildView(ViewEntity childView) throws ProgressusException {
		ValidatorHelper.validateFilling(ViewEntity.class, childView);
		if (childView instanceof ItemMenuEntity) {
			throw new UnableToCompleteOperationException("addChildView", "childViewIsAnItemMenuEntity");
		}
		try {
			this.getChildViewList().add(childView);
			childView.setParentView(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addChildView", e);
		}
	}
	
	public void addPermission(PermissionEntity permission) throws ProgressusException {
		ValidatorHelper.validateFilling(PermissionEntity.class, permission);
		try {
			this.getPermissionList().add(permission);
			permission.setView(this);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addPermission", e);
		}
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(this.getParentView())) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			String string = this.getParentView().toString();
			
			if (StringHelper.isNullOrEmpty(string)) {
				return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
			}
			
			if (StringHelper.isNullOrEmpty(this.getName())) {
				return string;
			}
			
			return string.concat(".").concat(this.getName());
			
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			if (!(object instanceof ViewEntity)) {
				return false;
			}
			return 
				new EqualsBuilder()
					.append(this.getClass(), object.getClass())
					.append(this.toString(), object.toString())
					.isEquals();
		} catch (Exception e) {
			ViewEntity.log.error(e.getMessage(), e);
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
			ViewEntity.log.error(e.getMessage(), e);
		}
		return super.hashCode();
	}

	
	public static ViewEntity getInstance(String view, String...permissionArray) throws ProgressusException {

		try {
			
			if (StringHelper.isNullOrEmpty(view)) {
				return null;
			}
			
			String[] viewArray = view.split("\\.");
			
			if (CollectionHelper.isNullOrEmpty(viewArray)) {
				return null;
			}
			
			ViewEntity entity = new ViewEntity(viewArray[0]);
			
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
	
	public static List<ViewEntity> addViewInList(List<ViewEntity> viewList, ViewEntity view) throws ProgressusException{
		
		try {
			
			if (viewList == null) {
				viewList = new ArrayList<>();
			}
			
			if (view == null) {
				return viewList;
			}
			
			if (viewList.contains(view)) {
				
				int index = viewList.indexOf(view);
				
				for (ViewEntity childView : view.getChildViewList()) {
					
					viewList.get(index).setChildViewList(ViewEntity.addViewInList(viewList.get(index).getChildViewList(), childView));
					
				}
				
				for (PermissionEntity permission : view.getPermissionList()) {
				
					viewList.get(index).setPermissionList(PermissionEntity.addPermissionInList(viewList.get(index).getPermissionList(), permission));
					
				}
				
				return viewList;
				
			}
			
			viewList.add(view);
			
			return viewList;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addViewInList", e);
		}
	}
}
