package br.com.hcs.progressus.ui.jsf.mb;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.client.helper.EJBHelper;
import br.com.hcs.progressus.enumerator.Template;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.ui.jsf.helper.JSFMessageHelper;
import br.com.hcs.progressus.ui.jsf.ldm.LDM;
import br.com.hcs.progressus.ui.jsf.to.CountMethodTO;
import br.com.hcs.progressus.ui.jsf.to.SelectListMethodTO;

@Slf4j
public abstract class ProgressusCRUDMB<K extends ProgressusCRUDMB<K, V>, V extends ProgressusEntity<V>> extends ProgressusMB<K> {

	private static final long serialVersionUID = -2141923930270676136L;

	
	@Getter
	@Setter
	private LDM<V> ldm;
	@Setter
	protected V entity;
	@Getter
	private V[] selectedArray;
	@Getter
	@Setter(AccessLevel.PRIVATE)
	private ProgressusBOEntityRemote<V> bo; 
	
	public ProgressusCRUDMB() { super(); }
	
	
	public abstract void load() throws ProgressusException;
	
	
	public V getEntity() {
		if (this.entity == null) {
			try {
				this.setEntity(ReflectionHelper.newInstance(this.getEntityClass()));
			} catch (ProgressusException e) {
				JSFMessageHelper.showMessage(e);
			}
		}
		return this.entity;
	}
	
	
	public SelectListMethodTO<V> getSelectListMethod() throws ProgressusException {
		return new SelectListMethodTO<>();
	}
	
	public CountMethodTO getCountMethod() throws ProgressusException {
		return new CountMethodTO();
	}
	
	
	public void setSelectedArray(V[] selectedArray) {
		
		try {
			
			this.selectedArray = selectedArray;
			
			if(this.getSelectedArray() == null) {
				return;
			}
			
			if(this.getSelectedArray().length == 1) {
				this.setEntity(this.getSelectedArray()[0]);
				return;
			}
			
			this.setEntity(ReflectionHelper.newInstance(this.getEntityClass()));
			
		} catch (ProgressusException e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
		}
	}
		
	@SuppressWarnings("unchecked")
	public final Class<V> getEntityClass() throws ProgressusException {
		try {
			
			List<Class<?>> genericClassList = ReflectionHelper.getGenericClassList(this.getClass());
			
			if (CollectionHelper.isNullOrEmpty(genericClassList)) {
				throw new UnableToCompleteOperationException("getEntityClass");
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
			throw new UnableToCompleteOperationException("getEntityClass");
		}
	}
	
		
	@Override
	public void init() {
		
		try {
			
			Class<V> clazz = this.getEntityClass();
			
			if (clazz == null) {
				this.setLdm(null);
			}
			
			this.setBo(EJBHelper.getBOEntity(clazz));
			
			this.setLdm(new LDM<>(this, this.getBo(), this.getSelectListMethod(), this.getCountMethod()));
			
			this.setEntity(ReflectionHelper.newInstance(this.getEntityClass()));
			
			this.setSelectedArray(null);
			
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
		}
		
		try {
			
			this.load();
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("load"));
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
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("save"));
		}
	}
	
	public void save() throws ProgressusException {
		this.getBo().save(this.getEntity());
	}
	
	
	public final void preDelete() {
		try {
			this.delete();
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			ProgressusCRUDMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("delete"));
		}
	}
	
	public void delete() throws ProgressusException {
		this.getBo().delete(this.getEntity());
	}
		
	
	
}
