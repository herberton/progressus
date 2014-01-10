package br.com.hcs.progressus.ejb.bo.sb.common;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import lombok.AccessLevel;
import lombok.Getter;
import br.com.hcs.progressus.client.bo.sb.common.NAOBORemote;
import br.com.hcs.progressus.client.dao.sb.common.NAODAOLocal;
import br.com.hcs.progressus.exception.BeginTransactionException;
import br.com.hcs.progressus.exception.CommitTransactionException;
import br.com.hcs.progressus.exception.GetDAOException;
import br.com.hcs.progressus.exception.RollbackTransactionException;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.EJBHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.helper.ValidateHelper;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class NAOBO implements NAOBORemote {

	private static final long serialVersionUID = -8263350772481901387L;
	
	
	@Getter(value=AccessLevel.PROTECTED)
    @Resource
	private UserTransaction userTransaction;
	
	
    public NAOBO() { super(); }

    
    protected void beginTransaction() throws ProgressusException {
		try {
			if (this.getUserTransaction().getStatus() == Status.STATUS_NO_TRANSACTION) {
				this.getUserTransaction().begin();
				this.printTransaction();
			}	
		} catch (Exception e) {
			throw new BeginTransactionException(e);
		}
	}
    protected void commitTransaction() throws ProgressusException {
    	try {
			if (this.getUserTransaction().getStatus() != Status.STATUS_NO_TRANSACTION) {
				this.getUserTransaction().commit();
				this.printTransaction();
			}
		} catch (Exception e) {
			this.rollbackTransaction();
			throw new CommitTransactionException(e);
		}
	}
	protected void rollbackTransaction() throws ProgressusException {
		try {
			if (this.getUserTransaction().getStatus() != Status.STATUS_NO_TRANSACTION) {
				this.getUserTransaction().rollback();
				this.printTransaction();
			}
		} catch (Exception e) {
			throw new RollbackTransactionException(e);
		}
	}
    
	@SuppressWarnings("unchecked")
	protected <T extends ProgressusEntity<? extends ProgressusEntity<?>>> NAODAOLocal<T> getDAO(Class<T> entityClazz) throws ProgressusException {
		
		ValidateHelper.validateFilling("entityClazz", entityClazz);
		
		try {
			
			return (NAODAOLocal<T>) 
				new InitialContext()
					.lookup(
						EJBHelper
							.getJNDIForLookup(
								this.getDAOName(entityClazz, true), 
								this.getDAOName(entityClazz, false)
							)
					);
			
		} catch (Exception e) {
			throw new GetDAOException(StringHelper.getI18N(entityClazz), e);
		}
	}
	
	private <T extends ProgressusEntity<? extends ProgressusEntity<?>>> String getDAOName(Class<T> entityClazz, boolean isGetSimpleName) throws ProgressusException {
		ValidateHelper.validateFilling("entityClazz", entityClazz);
		String daoName = isGetSimpleName ? entityClazz.getSimpleName() : entityClazz.getName();
		return daoName.replace("br.com.nao.jpa.entity", "br.com.nao.ejb.sb.dao").replace("Entity", "DAO");
	}	
	private String getUserTransactionStatus() throws ProgressusException {
		
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
			throw new ProgressusException(ProgressusException.class, e);
		}
    	
    }
	private void printTransaction() throws ProgressusException {
		System.out.println("NAO.Transaction:\t" + this.getUserTransactionStatus());
	}
}
