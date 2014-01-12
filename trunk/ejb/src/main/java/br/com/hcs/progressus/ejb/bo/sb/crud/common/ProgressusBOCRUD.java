package br.com.hcs.progressus.ejb.bo.sb.crud.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.client.bo.sb.crud.common.ProgressusBOCRUDRemote;
import br.com.hcs.progressus.ejb.bo.sb.common.ProgressusBO;
import br.com.hcs.progressus.enumerator.Status;
import br.com.hcs.progressus.exception.EntityNotFoundException;
import br.com.hcs.progressus.exception.SaveException;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;

@Stateless
@LocalBean
public class ProgressusBOCRUD<T extends ProgressusEntity<T>> 
	extends 
		ProgressusBO 
	implements 
		ProgressusBOCRUDRemote<T> 
{

    private static final long serialVersionUID = 7682571187985486249L;
    private static final Logger logger = LoggerFactory.getLogger(ProgressusBOCRUD.class);
    
    public ProgressusBOCRUD() { super(); }
    
    
    public T save(T entity) throws ProgressusException {
    	return this.save(entity, true);
    }
	@SuppressWarnings("unchecked")
	@Override
	public T save(T entity, boolean isCommitTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
		
		entity.setStatus(Status.ACTIVE);
		
		this.beginTransaction();
		
		try {
			
			if (entity.haveId()) {
				
				T beforeEntity = this.select(entity);
				
				if (beforeEntity == null) {
					throw new EntityNotFoundException(StringHelper.getI18N(entity.getClass()));
				}
				
				entity = (T) this.getDAO(entity.getClass()).update(entity);
				
			} else {
				
				entity = (T) this.getDAO(entity.getClass()).insert(entity);
				
			}
			
			if (isCommitTransaction) {
				this.commitTransaction();
			}
			
			return entity;
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
			this.rollbackTransaction();
			throw new SaveException(StringHelper.getI18N(entity.getClass()), e);
		}
	}
	
	public void delete(T entity) throws ProgressusException {
		try {
			this.delete(entity, true);
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delete(T entity, boolean isCommitTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			
			this.beginTransaction();
			
			T beforeEntity = this.select(entity);
			
			if (beforeEntity == null) {
				throw new EntityNotFoundException(StringHelper.getI18N(entity.getClass()));
			}
			
			entity.setStatus(Status.INACTIVE);
			
			this.getDAO(entity.getClass()).delete(entity);

			if (isCommitTransaction) {
				this.commitTransaction();
			}
			
		} catch (ProgressusException e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
			this.rollbackTransaction();
			throw e;
		}
	}

	public void remove(T entity) throws ProgressusException {
		try {
			this.remove(entity, true);
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void remove(T entity, boolean isCommitTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			
			this.beginTransaction();
			
			this.getDAO(entity.getClass()).remove(this.select(entity));
			
			if (isCommitTransaction) {
				this.commitTransaction();
			}
			
		} catch (ProgressusException e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
			this.rollbackTransaction();
			throw e;
		}
	}

	@Override
	public T select(T entity) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("entity", entity);
			
			List<T> list = this.selectList(entity);
			
			return list != null && list.size() > 0 ? list.get(0) : null;
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		
		return null;
	}
	@Override
	public T select(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("clazz", clazz);
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			
			List<T> list = this.selectList(clazz, parameterMap);
			return list != null && list.size() > 0 ? list.get(0) : null;
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		
		return null;
	}

	@Override
	public List<T> selectList(T entity) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("entity", entity);
			
			return this.selectList(entity, 0, null, null);
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		
		return new ArrayList<T>();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectList(T entity, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		try {
			ValidatorHelper.validateFilling("entity", entity);
			
			return this.selectList((Class<T>)entity.getClass(), entity.toParameterMap(), firstResult, maxResult, orderBy);
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		return new ArrayList<T>();
	}
	@Override
	public List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			ValidatorHelper.validateFilling("clazz", clazz);
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			
			return this.selectList(clazz, parameterMap, 0, null, null);
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		return new ArrayList<T>();
	}
	@Override
	public List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		try {
			ValidatorHelper.validateFilling("clazz", clazz);
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			
			return this.getDAO(clazz).selectList(clazz, parameterMap, firstResult, maxResult, orderBy);
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		return new ArrayList<T>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int count(T entity) throws ProgressusException {

		try {
			ValidatorHelper.validateFilling("entity", entity);

			return this.count((Class<T>)entity.getClass(), entity.toParameterMap());
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		return 0;
	}
	@Override
	public int count(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			ValidatorHelper.validateFilling("clazz", clazz);
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			
			return this.getDAO(clazz).count(clazz, parameterMap);
			
		} catch (Exception e) {
			ProgressusBOCRUD.logger.warn(e.getMessage());
		}
		return 0;
	}
}
