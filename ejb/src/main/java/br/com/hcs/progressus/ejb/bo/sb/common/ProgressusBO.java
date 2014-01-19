package br.com.hcs.progressus.ejb.bo.sb.common;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import br.com.hcs.progressus.client.bo.sb.common.ProgressusBORemote;
import br.com.hcs.progressus.client.dao.sb.common.ProgressusDAOLocal;
import br.com.hcs.progressus.exception.BeginTransactionException;
import br.com.hcs.progressus.exception.CommitTransactionException;
import br.com.hcs.progressus.exception.GetDAOException;
import br.com.hcs.progressus.exception.RollbackTransactionException;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.EJBHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ProgressusBO implements ProgressusBORemote {

	private static final long serialVersionUID = -8263350772481901387L;
	private static final Logger logger = LoggerFactory.getLogger(ProgressusBO.class);
	
	
	@Getter(value=AccessLevel.PROTECTED)
    @Resource
	private UserTransaction userTransaction;
	
	
    public ProgressusBO() { super(); }

    
    protected void beginTransaction() throws ProgressusException {
		try {
			if (this.getUserTransaction().getStatus() == Status.STATUS_NO_TRANSACTION) {
				this.printTransaction();
				this.getUserTransaction().begin();
				this.printTransaction();
			}	
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
			throw new BeginTransactionException(e);
		}
	}
    protected void commitTransaction() throws ProgressusException {
    	try {
			if (this.getUserTransaction().getStatus() != Status.STATUS_NO_TRANSACTION) {
				this.printTransaction();
				this.getUserTransaction().commit();
				this.printTransaction();
			}
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
			this.rollbackTransaction();
			throw new CommitTransactionException(e);
		}
	}
	protected void rollbackTransaction() throws ProgressusException {
		try {
			if (this.getUserTransaction().getStatus() != Status.STATUS_NO_TRANSACTION) {
				this.printTransaction();
				this.getUserTransaction().rollback();
				this.printTransaction();
			}
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
			throw new RollbackTransactionException(e);
		}
	}
    
	
	@SuppressWarnings("unchecked")
	protected <T extends ProgressusEntity<? extends ProgressusEntity<?>>> ProgressusDAOLocal<T> getDAO(Class<T> entityClazz) throws ProgressusException {
		
		ValidatorHelper.validateFilling("entityClazz", entityClazz);
		
		try {
			
			return (ProgressusDAOLocal<T>) 
				new InitialContext()
					.lookup(
						EJBHelper
							.getJNDIForLookup(
								this.getDAOName(entityClazz, true), 
								this.getDAOName(entityClazz, false)
							)
					);
			
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
			throw new GetDAOException(StringHelper.getI18N(entityClazz), e);
		}
	}
	
	
	private <T extends ProgressusEntity<? extends ProgressusEntity<?>>> String getDAOName(Class<T> entityClazz, boolean isGetSimpleName) throws ProgressusException {
		
		try {
			
			ValidatorHelper.validateFilling("entityClazz", entityClazz);
			
			String daoName = isGetSimpleName ? entityClazz.getSimpleName() : entityClazz.getName();
			
			return daoName.replace("jpa.entity", "ejb.dao.sb").replace("Entity", "DAO");
			
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
		}
		
		return "";
	}	
	
	private String getUserTransactionStatus() {
		
    	try {
    		
			switch (this.getUserTransaction().getStatus()) {
				case Status.STATUS_ACTIVE: return "STATUS_ACTIVE";
				case Status.STATUS_COMMITTED: return "STATUS_COMMITTED";
				case Status.STATUS_COMMITTING: return "STATUS_COMMITTING";
				case Status.STATUS_MARKED_ROLLBACK: return "STATUS_MARKED_ROLLBACK";
				case Status.STATUS_NO_TRANSACTION: return "STATUS_NO_TRANSACTION";
				case Status.STATUS_PREPARED: return "STATUS_PREPARED";
				case Status.STATUS_PREPARING: return "STATUS_PREPARING";
				case Status.STATUS_ROLLEDBACK: return "STATUS_ROLLEDBACK";
				case Status.STATUS_ROLLING_BACK: return "STATUS_ROLLING_BACK";
				case Status.STATUS_UNKNOWN: return "STATUS_UNKNOWN";
				default: return "-";
			}
			
		} catch (Exception e) {
			ProgressusBO.logger.warn(e.getMessage());
		}
    	
    	return "";
    	
    }
	
	private void printTransaction() throws ProgressusException {
		System.out.println("Progressus.Transaction:\t" + this.getUserTransactionStatus());
	}
}
