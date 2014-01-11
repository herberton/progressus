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
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidateHelper;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;


@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProgressusDAO<T extends ProgressusEntity<? extends ProgressusEntity<?>>> implements ProgressusDAOLocal<T> {

	private static final long serialVersionUID = 6836058369612935358L;
	
	
	@Getter
	@PersistenceContext(name="jpapu")
	private EntityManager entityManager;
	
	
    public ProgressusDAO() { super(); }

	
   	@Override
	public T insert(T entity) throws ProgressusException {
		
		ValidateHelper.validateFilling("entity", entity);
		
		try {
			
			this.getEntityManager().persist(entity);
			return entity;
			
		} catch (Exception e) {
			throw new InsertException(StringHelper.getI18N(entity.getClass()), e);
		}
	}

	@Override
	public T update(T entity) throws ProgressusException {

		ValidateHelper.validateFilling("entity", entity);
		
		try {
			
			entity.setStatus(Status.ACTIVE);
			
			return this.getEntityManager().merge(entity);
			
		} catch (Exception e) {
			throw new UpdateException(StringHelper.getI18N(entity.getClass()), e);
		}
	}

	@Override
	public void delete(T entity) throws ProgressusException {

		ValidateHelper.validateFilling("entity", entity);
		
		try {
			
			if (entity.getStatus().isActive()) {
				entity.setStatus(Status.INACTIVE);
			}
			
			this.getEntityManager().merge(entity);
			
		} catch (Exception e) {
			throw new DeleteException(StringHelper.getI18N(entity.getClass()), e);
		}

	}
	
	@Override
	public void remove(T entity) throws ProgressusException {
		
		ValidateHelper.validateFilling("entity", entity);
		
		try {
			this.getEntityManager().remove(entity);
		} catch (Exception e) {
			throw new RemoveException(StringHelper.getI18N(entity.getClass()), e);
		}
		
	}
	
	@Override
	public List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		ValidateHelper.validateFilling("clazz", clazz);
		ValidateHelper.validateFilling("parameterMap", parameterMap);
		
		try {
			
			parameterMap.put("status", Status.ACTIVE);
			
			String jpql = JPQLHelper.getSelect(clazz, parameterMap, orderBy);

			List<T> list = this.createTypedQuery(clazz, parameterMap, jpql, firstResult, maxResult).getResultList();
			
			if (list == null) {
				list = new ArrayList<>();
			}
			
			return list;
			
		} catch (Exception e) {
			throw new SelectException(StringHelper.getI18N(clazz), e);
		}
	}
	
	@Override
	public int count(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			parameterMap.put("status", Status.ACTIVE);
			
			String jpql = JPQLHelper.getSelectCount(clazz, parameterMap);
			
			return ((Long) this.createQuery(clazz, parameterMap, jpql).getSingleResult()).intValue();
			
		} catch (Exception e) {
			throw new CountException(StringHelper.getI18N(clazz), e);
		}
	}
	
	protected <X extends ProgressusEntity<? extends ProgressusEntity<?>>> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		return this.createTypedQuery(clazz, parameterMap, jpql, 0, -1);
	}
	@SuppressWarnings("unchecked")
	protected <X extends ProgressusEntity<? extends ProgressusEntity<?>>> TypedQuery<X> createTypedQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql, Integer firstResult, Integer maxResult)  throws ProgressusException {
		return (TypedQuery<X>) this.createQuery(clazz, parameterMap, jpql, firstResult, maxResult);
	}
	
	protected <X extends ProgressusEntity<? extends ProgressusEntity<?>>> Query createQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql)  throws ProgressusException {
		return this.createQuery(clazz, parameterMap, jpql, 0, -1);
	}
	protected <X extends ProgressusEntity<? extends ProgressusEntity<?>>> Query createQuery(Class<X> clazz, Map<String, Object> parameterMap, String jpql, Integer firstResult, Integer maxResult) throws ProgressusException {
		
		JPQLHelper.println(jpql, parameterMap);
		
		Query query = JPQLHelper.bindParameter(this.getEntityManager().createQuery(jpql, clazz), parameterMap);
			
		if (firstResult != null && firstResult > -1) {
			query.setFirstResult(firstResult);	
		}
		
		if(maxResult != null && maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		
		return query;
	}
}
