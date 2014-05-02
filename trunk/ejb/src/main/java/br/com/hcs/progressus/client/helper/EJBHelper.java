package br.com.hcs.progressus.client.helper;

import java.io.Serializable;

import javax.naming.InitialContext;

import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.client.ejb.sb.dao.ProgressusDAOLocal;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;

public final class EJBHelper implements Serializable {

	private static final long serialVersionUID = 1695533750798681853L;

	
	public static final String getJNDIForLookup(Class<?> ejbClass) throws ProgressusException {
		
		try {
		
			if (ObjectHelper.isNullOrEmpty(ejbClass)) {
				return "";
			}
			
			return 
				EJBHelper.getJNDIForLookup(ejbClass.getSimpleName(), ejbClass.getName());
		
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getJNDIForLookup", e);
		}
	}
	
	public static final String getJNDIForLookup(String ejbClassSimpleName, String ejbClassName) throws ProgressusException {
		
		try {
			
			if (StringHelper.isNullOrEmpty(ejbClassSimpleName) || StringHelper.isNullOrEmpty(ejbClassName)) {
				return "";
			}
		
			return 
				Setting.EJB_JNDI_LOOKUP.toString()
					.replace("[class_simpleName]", ejbClassSimpleName.replace("Local", "").replace("Remote", ""))
					.replace("[class_name]", ejbClassName);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getJNDIForLookup", e);
		}
	}


	public static final <T extends ProgressusEntity<?>> String getDAOName(Class<T> entityClazz, boolean isGetSimpleName) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("entityClazz", entityClazz);
			
			String daoName = isGetSimpleName ? entityClazz.getSimpleName() : entityClazz.getName();
			
			return daoName.replace("server.jpa.entity", "client.ejb.sb.dao").replace("Entity", "DAOLocal");
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDAOName", e);
		}
	}
	
	public static final <T extends ProgressusEntity<?>> String getBOEntityName(Class<T> entityClazz, boolean isGetSimpleName) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("entityClazz", entityClazz);
			
			String boName = isGetSimpleName ? entityClazz.getSimpleName() : entityClazz.getName();
			
			return boName.replace("server.jpa.entity", "client.ejb.sb.bo.entity").replace("Entity", "BORemote");
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getBOEntityName", e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static final <T extends ProgressusEntity<?>> ProgressusDAOLocal<T> getDAO(Class<T> entityClazz) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entityClazz", entityClazz);
		
		try {
			
			return (ProgressusDAOLocal<T>) 
				new InitialContext()
					.lookup(
						EJBHelper
							.getJNDIForLookup(
								EJBHelper.getDAOName(entityClazz, true), 
								EJBHelper.getDAOName(entityClazz, false)
							)
					);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDAO", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static final <T extends ProgressusEntity<?>> ProgressusBOEntityRemote<T> getBOEntity(Class<T> entityClazz) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entityClazz", entityClazz);
		
		try {
			
			return (ProgressusBOEntityRemote<T>) 
				new InitialContext()
					.lookup(
						EJBHelper
							.getJNDIForLookup(
								EJBHelper.getBOEntityName(entityClazz, true), 
								EJBHelper.getBOEntityName(entityClazz, false)
							)
					);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getBOEntity", e);
		}
	}
}
