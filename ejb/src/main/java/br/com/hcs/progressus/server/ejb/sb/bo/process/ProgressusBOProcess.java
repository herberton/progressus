package br.com.hcs.progressus.server.ejb.sb.bo.process;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.process.ProgressusBOProcessRemote;

@NoArgsConstructor
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProgressusBOProcess implements ProgressusBOProcessRemote {

	private static final long serialVersionUID = 6155539834478168302L;

	
	@Getter(AccessLevel.PROTECTED)
	@Resource 
	private EJBContext ejbContext;
}
