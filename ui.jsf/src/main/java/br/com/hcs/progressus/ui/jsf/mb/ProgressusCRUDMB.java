package br.com.hcs.progressus.ui.jsf.mb;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.client.helper.EJBHelper;
import br.com.hcs.progressus.enumerator.Template;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.ui.jsf.helper.JSFMessageHelper;
import br.com.hcs.progressus.ui.jsf.ldm.LDM;
import br.com.hcs.progressus.ui.jsf.to.CountMethodTO;
import br.com.hcs.progressus.ui.jsf.to.SelectListMethodTO;

@Slf4j
@NoArgsConstructor
public abstract class ProgressusCRUDMB<K extends ProgressusCRUDMB<K, V>, V extends ProgressusEntity<V>> extends ProgressusMB<K> {

	private static final long serialVersionUID = -2141923930270676136L;

	
	@Getter
	@Setter
	private LDM<V> ldm;
	protected V entity;
	@Getter
	private V[] selectedArray;
	@Getter(AccessLevel.PROTECTED)
	@Setter(AccessLevel.PRIVATE)
	private ProgressusBOEntityRemote<V> bo; 
	
	
	public V getEntity() {
		try {
			if (this.entity == null) {
				this.setEntity(ReflectionHelper.newInstance(this.getEntityClass()));
			}
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getEntity", e));
		}
		return this.entity;
	}
	
	public final void setEntity(V entity) {
		try {
			
			this.entity = entity;
			
			if (ObjectHelper.isNullOrEmpty(this.getEntity())) {
				this.setSelectedArray(null);
				return;
			}
			
			if (this.getEntity().hasId()) {
				
				if (CollectionHelper.isNullOrEmpty(this.getSelectedArray())) {
					this.setSelectedArray(ReflectionHelper.newArray(this.getEntityClass(), 1, this.getEntity()));
					return;
				}
				
				if (this.getSelectedArray().length > 1) {
					this.entity = null;
					return;
				}
				
				if (!this.getSelectedArray()[0].getId().equals(this.getEntity().getId())) {
					this.setSelectedArray(ReflectionHelper.newArray(this.getEntityClass(), 1, this.getEntity()));
					return;
				}
				
				return;
			}
			
			this.setSelectedArray(null);
			return;
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setEntity", e));
		}
	}
	
	public void setSelectedArray(V[] selectedArray) {
		
		try {
			
			this.selectedArray = selectedArray;
			
			if(CollectionHelper.isNullOrEmpty(this.getSelectedArray())) {
				return;
			}
			
			if(this.getSelectedArray().length == 1) {
				this.setEntity(this.getSelectedArray()[0]);
				return;
			}
			
			this.setEntity(null);
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("setSelectedArray", e));
		}
	}
	
	public int getSelectedArrayLenth() {
		try {
			return CollectionHelper.isNullOrEmpty(this.getSelectedArray()) ? 0 : this.getSelectedArray().length;
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getSelectedArrayLenth", e));
		}
		return 0;
	}
	
	public boolean isDeleteDisabled() {
		try {
			return this.getSelectedArrayLenth() != 1;
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getSelectedArrayLenth", e));
		}
		return true;
	}
	
	
	protected abstract void load() throws ProgressusException;
		
	@Override
	protected void init() throws ProgressusException {
		
		try {
			
			Class<V> clazz = this.getEntityClass();
			
			if (clazz == null) {
				this.setLdm(null);
			}
			
			this.setBo(EJBHelper.getBOEntity(clazz));
			
			this.setLdm(new LDM<>(this, this.getBo(), this.getSelectListMethod(), this.getCountMethod()));
			
			this.setEntity(null);
			
			this.setSelectedArray(null);
			
			this.load();
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("init", e);
		}
		
	}
	
	
	@Override
	public String getTemplatePage() {
		try {
			return ProgressusMB.getInstance(SessionMB.class).getTemplateCrudPage();
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault().getCrudPage();
	}
	

	public final void preSave() {
		try {
			this.save();
			super.preInit();
			JSFMessageHelper.showMessage(MessageTO.getInstance("savedSuccessfully"));
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("save", e));
		}
	}
	
	protected void save() throws ProgressusException {
		this.getBo().save(this.getEntity());
	}
	
	
	
	
	public final void preDelete() {
		try {
			this.delete();
			super.preInit();
			JSFMessageHelper.showMessage(MessageTO.getInstance("deletedSuccessfully"));
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("delete", e));
		}
	}
	
	protected void delete() throws ProgressusException {
		this.getBo().delete(this.getEntity());
	}

	
	protected SelectListMethodTO<V> getSelectListMethod() throws ProgressusException {
		return new SelectListMethodTO<>();
	}
	
	protected CountMethodTO getCountMethod() throws ProgressusException {
		return new CountMethodTO();
	}
	
	
	@SuppressWarnings("unchecked")
	protected final Class<V> getEntityClass() throws ProgressusException {
		try {
			
			List<Class<?>> genericClassList = ReflectionHelper.getGenericClassList(this.getClass());
			
			if (CollectionHelper.isNullOrEmpty(genericClassList)) {
				throw new UnableToCompleteOperationException("getEntityClass", "entityClassNotFound");
			}
			
			for (Class<?> clazz : genericClassList) {
				if (clazz.isAnnotationPresent(Entity.class) || 
					clazz.isAnnotationPresent(MappedSuperclass.class)) {
					return (Class<V>)clazz;
				}
			}
			
			return null;
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getEntityClass", e);
		}
	}
}