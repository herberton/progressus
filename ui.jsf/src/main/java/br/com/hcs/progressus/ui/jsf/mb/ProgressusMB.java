package br.com.hcs.progressus.ui.jsf.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.primefaces.model.menu.DefaultMenuModel;

import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.enumerator.MenuLevel;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.enumerator.Template;
import br.com.hcs.progressus.enumerator.Theme;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.to.ColumnTO;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ProgressusTO;
import br.com.hcs.progressus.ui.jsf.helper.DefaultMenuModelHelper;
import br.com.hcs.progressus.ui.jsf.helper.I18NHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFMessageHelper;

@Slf4j
@NoArgsConstructor
public abstract class ProgressusMB<T extends ProgressusMB<T>> 
	extends 
		ProgressusTO<T> 
	implements 
		Serializable
{

	private static final long serialVersionUID = -8223454400928020781L;
		
	
	@Setter
	private List<ColumnTO> columnList;
	
	
	public final List<ColumnTO> getColumnList() {
		if (this.columnList == null) {
			this.setColumnList(new ArrayList<ColumnTO>());
		}
		return this.columnList;
	}
		
	
	protected abstract void init() throws ProgressusException;
	
	
	@PostConstruct
	public final void preInit() {
		
		try {
			
			this.init();
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("preInit", e));
		}
		
	}
	
	
	public UserEntity getLoggedInUser() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getLoggedInUser();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getLoggedInUser", e));
		}
		return null;
	}
	
	public void setLoggedInUser(UserEntity loggedInUser) {
		try {
			ProgressusMB.getInstance(SessionMB.class).setLoggedInUser(loggedInUser);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setLoggedInUser", e));
		}
	}
	
	
	public SupportedLocale getSupportedLocale() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getSupportedLocale();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getSupportedLocale", e));
		}
		return SupportedLocale.getDefault();
	}
	
	public void setSupportedLocale(SupportedLocale supportedLocale) {
		try {
			ProgressusMB.getInstance(SessionMB.class).setSupportedLocale(supportedLocale);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setSupportedLocale", e));
		}
	}
	
	public Template getTemplate() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getTemplate();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getTemplate", e));
		}
		return Template.getDefault();
	}
	
	public void setTemplate(Template template) {
		try {
			ProgressusMB.getInstance(SessionMB.class).setTemplate(template);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setTemplate", e));
		}
	}
	
	public Theme getTheme() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getTheme();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getTheme", e));
		}
		return this.getTemplate().getDefaultTheme();
	}
	
	public void setTheme(Theme theme) {
		try {
			ProgressusMB.getInstance(SessionMB.class).setTheme(theme);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setTheme", e));
		}
	}
	
	
	public Locale getLocale() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getLocale();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getLocale", e));
		}
		return SupportedLocale.getDefault().getLocale();
	}
	
	public String getThemeName() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getThemeName();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setLocale", e));
		}
		return Template.getDefault().getDefaultTheme().toString();
	}
	
	
	public String getTemplatePage() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getTemplatePage();
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getTemplatePage", e));
		}
		return Template.getDefault().toString();
	}
	
	
	
	
	public final void refresh() {
		try {
			this.refresh(true);
			JSFMessageHelper.showMessage(MessageTO.getInstance("refreshedSuccessfully"));
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("refresh", e));
		}
	}
	
	
	public final void refresh(boolean isInvokeInitMethod) {
		try {
			this.refresh(JSFHelper.getUIViewRoot());
			
			if (isInvokeInitMethod) {
				this.preInit();
			}
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("refresh", e));
		}
	}
	
	
	public final void refresh(UIComponent uiComponent) {
		try {
			
			if (uiComponent == null) {
				return;
			}
			
			if (uiComponent instanceof EditableValueHolder) {
				
				EditableValueHolder editableValueHolder = (EditableValueHolder)uiComponent;
				editableValueHolder.setSubmittedValue(null);
				editableValueHolder.setValue(null);
				editableValueHolder.setLocalValueSet(false);
				
				this.setValid(uiComponent);
				
			} 
			
			for(UIComponent uiComponentChild : uiComponent.getChildren()) {
				this.refresh(uiComponentChild);
			}
			
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("refresh", e));
		}
	}	
	
	
	
	
	public final void setValid() {
		try {
			this.setValid(JSFHelper.getUIViewRoot());
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setValid", e));
		}
	}
	
	
	public final void setValid(UIComponent uiComponent) {
		
		try {
			
			if (uiComponent == null) {
				return;
			}
			
			if (uiComponent instanceof EditableValueHolder) {
				EditableValueHolder editableValueHolder = (EditableValueHolder)uiComponent;
				editableValueHolder.setValid(true);
			}
			
			for (UIComponent uiComponentChild : uiComponent.getChildren()) {
				this.setValid(uiComponentChild);
			}
			
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setValid", e));
		}
	}
	
	
	
	
	public final boolean checkPermission(String name) {
		try {
			return true;
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("checkPermission", e));
		}
		return false;
	}
	
	

	
	public final String getVersion() {
		return "1.0.0.0";
	}
	
	
	
	
	public final String logout() {
		try {
			this.setLoggedInUser(null);
			JSFHelper.invalidateSession();
			return JSFHelper.getURL("login");
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("logout", e));
		}
		return "";
	}
	
	

	
	public final DefaultMenuModel getDefaultMenuModel() {
		
		try {
			
			DefaultMenuModel defaultMenuModel = 
				JSFHelper.getSessionAtribute(Setting.WEB_SESSION_DEFAULT_MENU_MODEL.toString(), DefaultMenuModel.class);
			
			if (defaultMenuModel == null) {
				
				@SuppressWarnings("unchecked")
				List<MenuEntity> menuList = 
					JSFHelper.getApplicationAttribute(Setting.WEB_APPLICATION_MENU_TREE_LIST.toString(), List.class);
				
				if (menuList == null) {
					return null;
				}
				
				defaultMenuModel = 
					DefaultMenuModelHelper.getDefaultMenuModel(this.getLoggedInUser(), menuList, MenuLevel.MENU_ITEM);
				
				if (defaultMenuModel == null) {
					return null;
				}
				
				this.setDefaultMenuModel(defaultMenuModel);
			}
			
			return defaultMenuModel;
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getDefaultMenuModel", e));
		}
		
		return null;
	}
	
	
	public final void setDefaultMenuModel(DefaultMenuModel defaultMenuModel) {
		try {
			JSFHelper.putSessionAttribute(Setting.WEB_SESSION_DEFAULT_MENU_MODEL.toString(), defaultMenuModel);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setDefaultMenuModel", e));
		}
	}
	
	
	
	
	public final String getUrl(String page) {
		try {
			return JSFHelper.getURL(page);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getUrl", e));
		}
		return "#";
	}
	
	
	
	
	public final View getView() {
		try {
			if (this.getClass().isAnnotationPresent(View.class)) {
				return this.getClass().getAnnotation(View.class);
			}
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getView", e));
		}
		return null;
	}
		
	public final <TO extends ProgressusTO<TO>> void loadColumnList(Class<TO> clazz, boolean isClearColumnList) throws ProgressusException {
		try {
			if (isClearColumnList) {
				this.getColumnList().clear();
			}
			
			TO to = ReflectionHelper.newInstance(clazz);
			
			for (String description : to.getDescriptionList()) {	
				try {
					this.getColumnList().add(new ColumnTO(I18NHelper.getText(description), description));
				} catch (Exception e) {
					this.getColumnList().add(new ColumnTO(description, description));
				}			
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("loadColumnList", e);
		}
	}
	
	
	public final <TO extends ProgressusTO<TO>> void loadColumnList(Class<TO> clazz) throws ProgressusException {
		try {
			this.loadColumnList(clazz, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("loadColumnList", e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public final <TO extends ProgressusTO<TO>> void loadColumnList(Class<TO>...clazzArray) throws ProgressusException {
		try {
			this.getColumnList().clear();
			for (Class<TO> clazz : clazzArray) {
				this.loadColumnList(clazz, false);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("loadColumnList", e);
		}
	}
	
	
	public final <TO extends ProgressusTO<TO>> void loadColumnList(List<Class<TO>> clazzList) throws ProgressusException {
		try {
			this.getColumnList().clear();
			for (Class<TO> clazz : clazzList) {
				this.loadColumnList(clazz, false);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("loadColumnList", e);
		}
	}
	
	
	
		
	public static final <X extends ProgressusMB<X>> String getManagedBeanExpression(Class<X> clazz) {
		try {
			return "#{" + StringHelper.getI18N(clazz) + "}";
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final <X extends ProgressusMB<X>> X getInstance(Class<X> clazz) {
		try {
			return JSFHelper.executeExpressionLanguage(ProgressusMB.getManagedBeanExpression(clazz), clazz);
		} catch (Exception e) {
			ProgressusMB.log.error(e.getMessage(), e);
		}
		return null;
	}
}
