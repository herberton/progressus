package br.com.hcs.progressus.server.ejb.sb.bo.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.MenuBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.process.ApplicationBOLocal;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;

@Slf4j
@NoArgsConstructor
@Stateless
public class ApplicationBO extends ProgressusBOProcess implements ApplicationBOLocal {
	private static final long serialVersionUID = 5377264477230409581L;
	
	@Getter(AccessLevel.PRIVATE)
    @EJB
    private MenuBORemote menuBO;
	@Getter(AccessLevel.PRIVATE)
    @EJB
    private UserBORemote userBO;

	
	@Override
	public void createAdministratorUser() throws ProgressusException {
		
		try {
			
			String login = System.getProperty(Setting.SERVER_KEY_ADMIN_LOGIN.toString());
			
			if (this.getUserBO().select(login) == null) {
				
				UserEntity user = new UserEntity(login);
				user.setPassword("password");
				
				user.setAdministrator(true);
				
				user.setPreference(UserPreferenceEntity.getDefault());
				
				this.getUserBO().save(user);
			}
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void createMenuTree(Set<Class<?>> viewClazzSet) throws ProgressusException {
		
		try {
			
			List<MenuEntity> moduleList = this.selectMenuTreeList();
			
			if (CollectionHelper.isNullOrEmpty(moduleList)) {
				moduleList = new ArrayList<>();
			}
			
			for (Class<?> viewClazz : viewClazzSet) {
				
				if (!viewClazz.isAnnotationPresent(View.class)) {
					continue;
				}
				
				View view = viewClazz.getAnnotation(View.class);
				
				String menu =  
					StringHelper.isNullOrEmpty(view.menu()) ? 
						view.module() :
						view.module().concat(".").concat(view.menu());
				
				MenuEntity module =
					MenuEntity.getInstance(menu, view.name(), view.separator(), view.permissions());
				
				moduleList = 
					MenuEntity.addMenuInList(moduleList, module);
				
			}
			
			this.getMenuBO().saveTreeList(moduleList);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("createMenuTree");
		}
		
	}	

	@Override
	public List<MenuEntity> selectMenuTreeList() throws ProgressusException {
		try {
			return this.getMenuBO().selectTreeList();
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		return new ArrayList<MenuEntity>();
	}	
}
