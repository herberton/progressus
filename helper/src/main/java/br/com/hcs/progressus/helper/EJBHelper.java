package br.com.hcs.progressus.helper;

public class EJBHelper {
	
	public static String getJNDIForLookup(Class<?> ejbClass) {
		return 
			ConfigurationHelper.JNDI_EJB_LOOKUP
				.replace("[class_simpleName]", ejbClass.getSimpleName().replace("Local", "").replace("Remote", ""))
				.replace("[class_name]", ejbClass.getName().replace("Local", "").replace("Remote", ""));
	}
	public static String getJNDIForLookup(String ejbClassSimpleName, String ejbClassName) {
		return 
			ConfigurationHelper.JNDI_EJB_LOOKUP
				.replace("[class_simpleName]", ejbClassSimpleName.replace("Local", "").replace("Remote", ""))
				.replace("[class_name]", ejbClassName.replace("Local", "").replace("Remote", ""));
	}
	
}
