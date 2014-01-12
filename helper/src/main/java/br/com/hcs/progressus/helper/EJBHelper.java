package br.com.hcs.progressus.helper;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EJBHelper implements Serializable {
	
	private static final long serialVersionUID = 4795043018126971318L;
	private static final Logger logger = LoggerFactory.getLogger(EJBHelper.class);
	
	public static String getJNDIForLookup(Class<?> ejbClass) {
		
		try {
		
			if (ObjectHelper.isNullOrEmpty(ejbClass)) {
				return "";
			}
			
			return 
				EJBHelper.getJNDIForLookup(ejbClass.getSimpleName(), ejbClass.getName());
		
		} catch (Exception e) {
			EJBHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	
	public static String getJNDIForLookup(String ejbClassSimpleName, String ejbClassName) {
		
		try {
			
			if (StringHelper.isNullOrEmpty(ejbClassSimpleName) || StringHelper.isNullOrEmpty(ejbClassName)) {
				return "";
			}
		
			return 
				ConfigurationHelper.JNDI_EJB_LOOKUP
					.replace("[class_simpleName]", ejbClassSimpleName.replace("Local", "").replace("Remote", ""))
					.replace("[class_name]", ejbClassName.replace("Local", "").replace("Remote", ""));
			
		} catch (Exception e) {
			EJBHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	
}
