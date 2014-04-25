package br.com.hcs.progressus.server.jpa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.annotation.Display;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Entity
public class UserEntity extends ProgressusEntity<UserEntity> implements Serializable {

	private static final long serialVersionUID = -8047519884850858607L;

	@Display
	@Getter
	@Setter
	@Column
	private boolean administrator;
	@Display
	@Getter
	@Setter
	@NonNull
	@Column(nullable=false, updatable=false, unique=true)
	private String login;
	@Getter
	@Setter
	@Column(nullable=false)
	private String password;
	@Display
	@Getter
	@Setter
	private String name;
	@Display
	@Getter
	@Setter
	@Column
	private boolean emailReciever;
	@Setter
	@OneToOne(optional=false, cascade=CascadeType.ALL, orphanRemoval=true)
	private UserPreferenceEntity preference;
	@Getter
	@Setter
	@OneToOne(optional=true, cascade=CascadeType.ALL, orphanRemoval=true)
	private EmailEntity email;
	@Getter
	@Setter
	@OneToOne(optional=true, cascade=CascadeType.ALL, orphanRemoval=true)
	private TagEntity tag;
	@Setter
	@ManyToMany(mappedBy = "userList")
	private List<RoleEntity> roleList;
	
	
	public UserPreferenceEntity getPreference() {
		if (this.preference == null) {
			this.setPreference(UserPreferenceEntity.getDefault());
		}
		return this.preference;
	}
	public List<RoleEntity> getRoleList() {
		if (this.roleList == null) {
			this.setRoleList(new ArrayList<RoleEntity>());
		}
		return this.roleList;
	}
	
	
	public boolean havePermission(ViewEntity view) {
		
		try {
			
			if (this.isAdministrator()) {
				return true;
			}
			
			for (RoleEntity role : this.getRoleList()) {
				for (PermissionEntity permission : role.getPermissionList()) {
					
					if (view.equals(permission.getView())) {
						return true;
					}
				}
			}
			
			for (ViewEntity childView : view.getChildViewList()) {
				return this.havePermission(childView);
			}
			
		} catch (Exception e) {
			UserEntity.log.error(e.getMessage(), e);
		}
		
		return false;
	}
	
	public boolean havePermission(ItemMenuEntity itemMenu) {
		try {
			return this.havePermission(itemMenu.getView());
		} catch (Exception e) {
			UserEntity.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	public boolean havePermission(MenuEntity menu) {
		try {
			for (ItemMenuEntity itemMenu : menu.getItemMenuList()) {
				if (this.havePermission(itemMenu)) {
					return true;
				}
			}
			for (MenuEntity childMenu : menu.getChildMenuList()) {
				return this.havePermission(childMenu);
			}
		} catch (Exception e) {
			UserEntity.log.error(e.getMessage(), e);
		}
		return false;
	}

}
