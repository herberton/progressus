package br.com.hcs.progressus.ejb.dao.sb.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.client.dao.sb.common.ProgressusDAOLocal;
import br.com.hcs.progressus.enumerator.Status;
import br.com.hcs.progressus.exception.CountException;
import br.com.hcs.progressus.exception.DeleteException;
import br.com.hcs.progressus.exception.InsertException;
import br.com.hcs.progressus.exception.RemoveException;
import br.com.hcs.progressus.exception.SelectException;
import br.com.hcs.progressus.exception.UpdateException;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.JPQLHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProgressusDAO<T extends ProgressusEntity<? extends ProgressusEntity<?>>> implements ProgressusDAOLocal<T> {

	private static final long serialVersionUID = 6836058369612935358L;
	private static final Logger logger = LoggerFactory.getLogger(ProgressusDAO.class);
	
	
	@Getter
	@PersistenceContext(name="progressus.pu")
	private EntityManager entityManager;
	
	
    public ProgressusDAO() { super(); }

	
   	@Override
	public T insert(T entity) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			
			this.getEntityManager().persist(entity);
			return entity;
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new InsertException(StringHelper.getI18N(entity.getClass()), e);
		}
	}

	@Override
	public T update(T entity) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			
			entity.setStatus(Status.ACTIVE);
			
			return this.getEntityManager().merge(entity);
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new UpdateException(StringHelper.getI18N(entity.getClass()), e);
		}
	}

	@Override
	public void delete(T entity) throws ProgressusException {

		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			
			if (entity.getStatus().isActive()) {
				entity.setStatus(Status.INACTIVE);
			}
			
			this.getEntityManager().merge(entity);
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new DeleteException(StringHelper.getI18N(entity.getClass()), e);
		}

	}
	
	@Override
	public void remove(T entity) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entity", entity);
		
		try {
			this.getEntityManager().remove(entity);
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new RemoveException(StringHelper.getI18N(entity.getClass()), e);
		}
		
	}
	
	@Override
	public List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		ValidatorHelper.validateFilling("clazz", clazz);
		ValidatorHelper.validateFilling("parameterMap", parameterMap);
		
		try {
			
			parameterMap.put("status", Status.ACTIVE);
			
			String jpql = JPQLHelper.getSelect(clazz, parameterMap, orderBy);

			this.print(jpql);
			
			List<T> list = this.createTypedQuery(clazz, parameterMap, jpql, firstResult, maxResult).getResultList();
			
			if (list == null) {
				list = new ArrayList<>();
			}
			
			return list;
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new SelectException(StringHelper.getI18N(clazz), e);
		}
	}
	
	@Override
	public long count(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			parameterMap.put("status", Status.ACTIVE);
			
			String jpql = JPQLHelper.getSelectCount(clazz, parameterMap);
			
			this.print(jpql);
			
			Object count = this.createQuery(Long.class, parameterMap, jpql).getSingleResult();
			
			if (ObjectHelper.isNullOrEmpty(count)) {
				return 0; 
			}
			
			return Long.parseLong(count.toString());
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
			throw new CountException(StringHelper.getI18N(clazz), e);
		}
	}
	
	
	protected <X> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return this.createTypedQuery(clazz, parameterMap, jpql, 0, -1);
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <X> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql, Integer firstResult, Integer maxResult)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return (TypedQuery<X>) this.createQuery(clazz, parameterMap, jpql, firstResult, maxResult);
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
		}
		return null;
	}
	
	protected <X> Query createQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return null;
			}
			
			return this.createQuery(clazz, parameterMap, jpql, 0, -1);
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
		}
		return null;
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
			
		} catch (Exception e) {
			ProgressusDAO.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	
	protected void print(String jpql) {
		ProgressusDAO.logger.info("\n\nProgressus.JPQL\n[\n\t" + jpql + "\n]\n");
	}
}
