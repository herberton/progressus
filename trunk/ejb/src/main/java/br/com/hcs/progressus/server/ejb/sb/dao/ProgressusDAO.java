package br.com.hcs.progressus.server.ejb.sb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.client.ejb.sb.dao.ProgressusDAOLocal;
import br.com.hcs.progressus.client.helper.EJBHelper;
import br.com.hcs.progressus.enumerator.EntityStatus;
import br.com.hcs.progressus.exception.BeginTransactionException;
import br.com.hcs.progressus.exception.CommitTransactionException;
import br.com.hcs.progressus.exception.CountException;
import br.com.hcs.progressus.exception.DeleteException;
import br.com.hcs.progressus.exception.EntityNotFoundException;
import br.com.hcs.progressus.exception.InsertException;
import br.com.hcs.progressus.exception.InsertOrSelectException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.RemoveException;
import br.com.hcs.progressus.exception.RollbackTransactionException;
import br.com.hcs.progressus.exception.SaveException;
import br.com.hcs.progressus.exception.SelectException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.exception.UpdateException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.JPQLHelper;
import br.com.hcs.progressus.helper.MapHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.ProgressusTO;

@NoArgsConstructor
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ProgressusDAO<T extends ProgressusEntity<?>> implements ProgressusDAOLocal<T> {

	private static final long serialVersionUID = 3603846302496627366L;
	
	@Getter(AccessLevel.PROTECTED)
	@Resource 
	private EJBContext ejbContext;
	
	@Getter(AccessLevel.PROTECTED)
	@PersistenceContext(name="progressus.pu")
	private EntityManager entityManager;
	
	
	// INSERT...
	
	@Override
	public T insert(T entity) throws ProgressusException {
		try {
			return this.insert(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertException(this.getEntityClazz("insert"), e);
		}
	}

	protected T insert(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClazz()), entity);
			return this.insertEntity(entity, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertException(this.getEntityClazz("insert"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> X insertEntity(X entity, boolean isControlTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
   		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
   		UserTransaction userTransaction = null;
		
   		try {
   			
   			if (isControlTransaction) {
   				userTransaction = this.beginTransaction();	
			}
			
			this.getEntityManager().persist(entity);
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			return entity;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new InsertException(clazz, e);
			
		}
	}

	
	// INSERT LIST...
	
	@Override
	public List<T> insertList(List<T> entityList) throws ProgressusException {
		try {
			return this.insertList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertException(this.getEntityClazz("insert"), e);
		}
	}

	protected List<T> insertList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			return this.insertEntityList(entityList, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertException(this.getEntityClazz("insert"), e);
		}
	}

	protected <X extends ProgressusEntity<?>> List<X> insertEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entityList", entityList);
   		
		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return entityList;
		}
   		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				entityList.set(i, this.insertEntity(iterator.next(), false));
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);	
			}
			
			return entityList;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new InsertException(clazz, e);
		}
	}
	
	
	// UPDATE...
	
	@Override
	public T update(T entity) throws ProgressusException {
		try {
			return this.update(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UpdateException(this.getEntityClazz("update"), e);
		}
	}

	protected T update(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClazz()), entity);
			return this.updateEntity(entity, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UpdateException(this.getEntityClazz("update"), e);
		}
	}

	protected <X extends ProgressusEntity<?>> X updateEntity(X entity, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
		UserTransaction userTransaction = null;
		
   		try {
			
   			if (isControlTransaction) {
   				userTransaction = this.beginTransaction();
			}
			
			entity.setEntityStatus(EntityStatus.ACTIVE);
			
			entity = this.getEntityManager().merge(entity);
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			
			return entity;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw new UpdateException(clazz, e);
		}
	}
	
	
	// UPDATE LIST...
	
	@Override
	public List<T> updateList(List<T> entityList) throws ProgressusException {
		try {
			return this.updateList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UpdateException(this.getEntityClazz("update"), e);
		}
	}

	protected List<T> updateList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			return this.updateEntityList(entityList, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UpdateException(this.getEntityClazz("update"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> List<X> updateEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entityList", entityList);
   		
   		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return entityList;
		}
   		
   		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
   		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				entityList.set(i, this.updateEntity(iterator.next(), false));
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);	
			}
			
			return entityList;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new UpdateException(clazz, e);
		}
	}

	
	// SAVE...
	
	@Override
	public T save(T entity) throws ProgressusException {
		try {
			return this.save(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SaveException(this.getEntityClazz("save"), e);
		}
	}

	protected T save(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClazz()), entity);
			return this.saveEntity(entity, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SaveException(this.getEntityClazz("save"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> X saveEntity(X entity, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
		UserTransaction userTransaction = null;
		
		try {
			
			entity.setEntityStatus(EntityStatus.ACTIVE);
		
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();	
			}
			
			if (entity.hasId()) {
				
				List<X> beforeEntityList = this.selectEntityList(clazz, entity.toParameterMap(), 0, 1, null);
				
				if (CollectionHelper.isNullOrEmpty(beforeEntityList)) {
					throw new EntityNotFoundException(clazz);
				}
				
				entity = this.updateEntity(entity, false);
				
			} else {
				
				entity = this.insertEntity(entity, false);
				
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			
			return entity;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) { 
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		}catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new SaveException(clazz, e);
		}
	}

	
	// SAVE LIST...
	
	@Override
	public List<T> saveList(List<T> entityList) throws ProgressusException {
		try {
			return this.saveList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SaveException(this.getEntityClazz("save"), e);
		}
	}

	protected List<T> saveList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			return this.saveEntityList(entityList, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SaveException(this.getEntityClazz("save"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> List<X> saveEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entityList", entityList);
   		
   		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return entityList;
		}
   		
   		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
   		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				entityList.set(i, this.saveEntity(iterator.next(), false));
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);	
			}
			
			return entityList;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new SaveException(clazz, e);
		}
	}

	
	// DELETE...
	
	@Override
	public void delete(T entity) throws ProgressusException {
		try {
			this.delete(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new DeleteException(this.getEntityClazz("delete"), e);
		}
	}

	protected void delete(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClazz()), entity);
			this.deleteEntity(entity, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new DeleteException(this.getEntityClazz("delete"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> void deleteEntity(X entity, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
		UserTransaction userTransaction = null;
		
   		try {
   			
   			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			if (entity.getEntityStatus().isActive()) {
				entity.setEntityStatus(EntityStatus.INACTIVE);
			}
			
			this.getEntityManager().merge(entity);
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw new DeleteException(clazz, e);
		}
	}

	
	// DELETE LIST...
	
	@Override
	public void deleteList(List<T> entityList) throws ProgressusException {
		try {
			this.deleteList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new DeleteException(this.getEntityClazz("delete"), e);
		}
		
	}

	protected void deleteList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			this.deleteEntityList(entityList, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new DeleteException(this.getEntityClazz("delete"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> void deleteEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entityList", entityList);
   		
		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return;
		}
   		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			while (iterator.hasNext()) {
				this.deleteEntity(iterator.next(), false);
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);	
			}
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new DeleteException(clazz, e);
		}
	}

	
	// REMOVE...
	
	@Override
	public void remove(T entity) throws ProgressusException {
		try {
			this.remove(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new RemoveException(this.getEntityClazz("remove"), e);
		}
	}

	protected void remove(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling(StringHelper.getI18N(this.getEntityClazz()), entity);
			this.removeEntity(entity, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new RemoveException(this.getEntityClazz("remove"), e);
		}
	}

	protected <X extends ProgressusEntity<?>> void removeEntity(X entity, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
		UserTransaction userTransaction = null;
		
   		try {
   			
   			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
   			
			this.getEntityManager().remove(entity);
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw new RemoveException(clazz, e);
		}
	}
	
	
	// REMOVE LIST...
	
	@Override
	public void removeList(List<T> entityList) throws ProgressusException {
		try {
			this.removeList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new RemoveException(this.getEntityClazz("remove"), e);
		}
	}

	protected void removeList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			this.removeEntityList(entityList, isControlTransaction);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new RemoveException(this.getEntityClazz("remove"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> void removeEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {

		ValidatorHelper.validateFilling("entityList", entityList);
   		
		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return;
		}
   		
		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			while (iterator.hasNext()) {
				this.removeEntity(iterator.next(), false);
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);	
			}
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);	
			}
			throw new RemoveException(clazz, e);
		}
	}

	
	// INSERT OR SELECT...
	
	@Override
	public T insertOrSelect(T entity) throws ProgressusException {
		try {
			return this.insertOrSelect(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertOrSelectException(this.getEntityClazz("insertOrSelect"), e);
		}
	}
	
	protected T insertOrSelect(T entity, boolean isControlTransaction) throws ProgressusException {
		try {
			return this.insertOrSelectEntity(entity, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertOrSelectException(this.getEntityClazz("insertOrSelect"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> X insertOrSelectEntity(X entity, boolean isControlTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
   		
		if (ObjectHelper.isNullOrEmpty(entity)) {
			return entity;
		}
   		
   		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entity.getClass();
		
   		try {
			
			if (entity.hasId()) {
				
				entity = this.getBO(clazz).select(entity);
				
				if (ObjectHelper.isNullOrEmpty(entity)) {
					throw new EntityNotFoundException(clazz);
				}
				
				return entity;
			}
			
			return this.insertEntity(entity, isControlTransaction);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertOrSelectException(clazz, e);
		}
	}
	
	
	// INSERT OR SELECT LIST...
	
	@Override
	public List<T> insertOrSelectList(List<T> entityList) throws ProgressusException {
		try {
			return this.insertOrSelectList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertOrSelectException(this.getEntityClazz("insertOrSelectList"), e);
		}
	}
	
	protected List<T> insertOrSelectList(List<T> entityList, boolean isControlTransaction) throws ProgressusException {
		try {
			return this.insertOrSelectEntityList(entityList, true);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new InsertOrSelectException(this.getEntityClazz("insertOrSelectList"), e);
		}
	}
	
	protected <X extends ProgressusEntity<?>> List<X> insertOrSelectEntityList(List<X> entityList, boolean isControlTransaction) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entityList", entityList);
   		
		if (CollectionHelper.isNullOrEmpty(entityList)) {
			return entityList;
		}
   		
   		@SuppressWarnings("unchecked")
		Class<X> clazz = (Class<X>) entityList.get(0).getClass();
		
   		UserTransaction userTransaction = null;
		
		try {
			
			if (isControlTransaction) {
				userTransaction = this.beginTransaction();
			}
			
			Iterator<X> iterator = entityList.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				entityList.set(i, this.insertOrSelectEntity(iterator.next(), false));
			}
			
			if (isControlTransaction) {
				this.commitTransaction(userTransaction);
			}
			
			return entityList;
			
		} catch (ProgressusException pe) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw pe;
		} catch (Exception e) {
			if (isControlTransaction) {
				this.rollbackTransaction(userTransaction);
			}
			throw new InsertOrSelectException(clazz, e);
		}
	}

	
	// SELECT LIST...
	
	
	@Override
	public List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		Class<T> clazz = this.getEntityClazz("select");
		
		try {
			return this.selectEntityList(clazz, parameterMap, firstResult, maxResult, orderBy);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SelectException(clazz, e);
		}
	}
	
	
	
	protected <X extends ProgressusEntity<?>> List<X> selectEntityList(Class<X> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {

		ValidatorHelper.validateFilling("parameterMap", parameterMap);
		ValidatorHelper.validateFilling("clazz", clazz);
		
		try {
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			parameterMap.put("entityStatus", EntityStatus.ACTIVE);
			
			String jpql = JPQLHelper.getSelect(clazz, parameterMap, orderBy);

			List<X> list = this.createTypedQuery(clazz, parameterMap, jpql, firstResult, maxResult).getResultList();
			
			if (list == null) {
				list = new ArrayList<>();
			}
			
			return list;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new SelectException(clazz, e);
		}
	}
	
	
	
	// COUNT...
	
	
	
	@Override
	public int count(Map<String, Object> parameterMap) throws ProgressusException {

		Class<T> clazz = this.getEntityClazz("count");
		
		try {
			return this.countEntity(clazz, parameterMap);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new CountException(clazz, e);
		}
	}
	
	
	
	protected <X extends ProgressusEntity<?>> int countEntity(Class<X> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		ValidatorHelper.validateFilling("clazz", clazz);
		
		try {
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			parameterMap.put("entityStatus", EntityStatus.ACTIVE);
			
			String jpql = JPQLHelper.getSelectCount(clazz, parameterMap);
			
			return ((Long) this.createQuery(Long.class, parameterMap, jpql).getSingleResult()).intValue();
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new CountException(clazz, e);
		}
	}
	
	
	
	// TRANSACTION...
	
	
		
	protected UserTransaction beginTransaction() throws ProgressusException {
		try {
			UserTransaction userTransaction = this.getEjbContext().getUserTransaction();
			userTransaction.begin();
			return userTransaction;
		} catch (Exception e) {
			throw new BeginTransactionException(e);
		}
		
	}
    
	
	protected void commitTransaction(UserTransaction userTransaction) throws ProgressusException {
    	try {
    		userTransaction.commit();
		} catch (Exception e) {
			this.rollbackTransaction(userTransaction);
			throw new CommitTransactionException(e);
		}
	}
	
    
	protected void rollbackTransaction(UserTransaction userTransaction) throws ProgressusException {
		try {
			userTransaction.rollback();
		} catch (Exception e) {
			throw new RollbackTransactionException(e);
		}
	}
	
	
	
	// GET ENTITY CLASS...
	
    
	
	protected Class<T> getEntityClazz() throws ProgressusException {
		try {
			return this.getEntityClazz("getEntityClazz");
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getEntityClazz", e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClazz(String operation) throws ProgressusException {
		try {
			
			List<Class<?>> genericClassList = ReflectionHelper.getGenericClassList(this.getClass());
			
			if (CollectionHelper.isNullOrEmpty(genericClassList)) {
				throw new UnableToCompleteOperationException(operation, "entityClassNotFound");
			}
			
			return (Class<T>) genericClassList.get(0);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException(operation, e);
		}
	}
	
	
	
	// GET BO...
	
	
	
	protected ProgressusBOEntityRemote<T> getBO() throws ProgressusException {
		try {
			return this.getBO(this.getEntityClazz());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getBO", e);
		}
	}
	
	
	
	protected <X extends ProgressusEntity<?>> ProgressusBOEntityRemote<X> getBO(Class<X> clazz) throws ProgressusException {
		try {
			return EJBHelper.getBOEntity(clazz);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getBO", e);
		}
	}
	
	
	
	// CREATE TYPED QUERY...
	
	
	
	
	protected <X extends ProgressusTO<?>> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return this.createTypedQuery(clazz, parameterMap, jpql, 0, -1);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("createTypedQuery", e);
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	protected <X extends ProgressusTO<?>> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql, Integer firstResult, Integer maxResult)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return (TypedQuery<X>) this.createQuery(clazz, parameterMap, jpql, firstResult, maxResult);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("createTypedQuery", e);
		}
	}
	
	
	
	// CREATE QUERY...
	

	
	
	protected <X> Query createQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return this.createQuery(clazz, parameterMap, jpql, 0, -1);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("createQuery", e);
		}
	}
	
	
	
	
	protected <X> Query createQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql, Integer firstResult, Integer maxResult) throws ProgressusException {
		
		try {
			
			Query query = JPQLHelper.bindParameter(this.getEntityManager().createQuery(jpql, clazz), parameterMap);
			
			if (ObjectHelper.isNullOrEmpty(query)) {
				return null;
			}
			
			if (firstResult != null && firstResult > -1) {
				query.setFirstResult(firstResult);	
			}
			
			if(maxResult != null && maxResult > 0) {
				query.setMaxResults(maxResult);
			}
			
			return query;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("createQuery", e);
		}
	}
	
}