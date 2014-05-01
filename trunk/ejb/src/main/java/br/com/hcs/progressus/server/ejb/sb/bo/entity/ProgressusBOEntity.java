package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.client.ejb.sb.dao.ProgressusDAOLocal;
import br.com.hcs.progressus.client.helper.EJBHelper;
import br.com.hcs.progressus.exception.CountException;
import br.com.hcs.progressus.exception.DeleteException;
import br.com.hcs.progressus.exception.InsertOrSelectException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.RemoveException;
import br.com.hcs.progressus.exception.SaveException;
import br.com.hcs.progressus.exception.SelectException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.server.ejb.sb.bo.process.ProgressusBOProcess;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;

@Slf4j
@NoArgsConstructor
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProgressusBOEntity<T extends ProgressusEntity<?>>
	extends
		ProgressusBOProcess
	implements 
		ProgressusBOEntityRemote<T> {

    private static final long serialVersionUID = -6079133477965729028L;

    
    // SAVE...
    
    @Override    
    public T save(T entity) throws ProgressusException {
    	try {
    		ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
			return this.getDAO().save(entity);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SaveException(this.getEntityClass(), e);
		}
    }
    
    
    // SAVE LIST...
    
    @Override    
    public List<T> saveList(List<T> entityList) throws ProgressusException {
		try {
			return this.getDAO().saveList(entityList);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SaveException(this.getEntityClass(), e);
		}
    }
	
	
    // DELETE...
    
	@Override
	public void delete(T entity) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
			this.getDAO().delete(entity);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new DeleteException(this.getEntityClass(), e);
		}
	}
	
	
    // DELETE LIST...
	
	@Override
	public void deleteList(List<T> entityList) throws ProgressusException {
		try {
			this.getDAO().deleteList(entityList);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new DeleteException(this.getEntityClass(), e);
		}
	}
	
	
    // REMNOVE...
	
	@Override
	public void remove(T entity) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
			this.getDAO().remove(entity);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new RemoveException(this.getEntityClass(), e);
		}
	}
	
	
    // REMOVE LIST...
	
	@Override
	public void removeList(List<T> entityList) throws ProgressusException {
		try {
			this.getDAO().removeList(entityList);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new RemoveException(this.getEntityClass(), e);
		}
	}
	
	
    // INSERT OR SELECT...
	
	@Override
	public T insertOrSelect(T entity) throws ProgressusException {
		try {
    		ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
    		return this.getDAO().insertOrSelect(entity);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new InsertOrSelectException(this.getEntityClass(), e);
		}
	}
	
	
    // INSERT OR SELECT LIST...
	
	@Override
	public List<T> insertOrSelectList(List<T> entityList) throws ProgressusException {
		try {
    		ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entityList);
			return this.getDAO().insertOrSelectList(entityList);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new InsertOrSelectException(this.getEntityClass(), e);
		}
	}
	
	
    // SELECT...
	
	@Override
	public T select(T entity) throws ProgressusException {
		
		try {
			
			List<T> list = this.selectList(entity);
			
			return list != null && list.size() > 0 ? list.get(0) : null;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}
	
	@Override
	public T select(Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			List<T> list = this.selectList(parameterMap);
			
			return list != null && list.size() > 0 ? list.get(0) : null;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	
    // SELECT LIST...
	
	@Override
	public List<T> selectList() throws ProgressusException {
		try {
			return this.selectList(new HashMap<String, Object>());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(T entity) throws ProgressusException {
		try {
			return this.selectList(entity, 0, null, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(T entity, Integer firstResult, Integer maxResult) throws ProgressusException {
		try {
			return this.selectList(entity, firstResult, maxResult, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(T entity, OrderByTO orderBy) throws ProgressusException {
		try {
			return this.selectList(entity, 0, null, orderBy);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(T entity, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
			return this.selectList(entity.toParameterMap(), firstResult, maxResult, orderBy);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(Map<String, Object> parameterMap) throws ProgressusException {
		try {
			return this.selectList(parameterMap, 0, null, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult) throws ProgressusException {
		try {
			return this.selectList(parameterMap, firstResult, maxResult, null);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(Map<String, Object> parameterMap, OrderByTO orderBy) throws ProgressusException {
		try {
			return this.selectList(parameterMap, 0, null, orderBy);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	@Override
	public List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			return this.getDAO().selectList(parameterMap, firstResult, maxResult, orderBy);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new SelectException(this.getEntityClass("select"), e);
		}
	}

	
    // COUNT...
	
	@Override
	public int count() throws ProgressusException {
		try {
			return this.count(new HashMap<String, Object>());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new CountException(this.getEntityClass("count"), e);
		}
	}

	@Override
	public int count(T entity) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClass()), entity);
			return this.count(entity.toParameterMap());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new CountException(this.getEntityClass("count"), e);
		}
	}

	@Override
	public int count(Map<String, Object> parameterMap) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling("parameterMap", parameterMap);
			return this.getDAO().count(parameterMap);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new CountException(this.getEntityClass("count"), e);
		}
	}
	
	
    // GET ENTITY CLASS...
	
	@Override
	public Class<T> getEntityClass() throws ProgressusException {
		try {
			return this.getEntityClass("getEntityClass");
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getEntityClass");
		}
	}
		
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass(String operation) throws ProgressusException {
		try {
			
			List<Class<?>> genericClassList = ReflectionHelper.getGenericClassList(this.getClass());
			
			if (CollectionHelper.isNullOrEmpty(genericClassList)) {
				throw new UnableToCompleteOperationException(operation);
			}
			
			return (Class<T>) genericClassList.get(0);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException(operation);
		}
	}
	
	
    // GET DAO...
	
	protected ProgressusDAOLocal<T> getDAO() {
		try {
			return EJBHelper.getDAO(this.getEntityClass());
		} catch (Exception e) {
			ProgressusBOEntity.log.error(e.getMessage(), e);
		}
		return null;
	}
}
