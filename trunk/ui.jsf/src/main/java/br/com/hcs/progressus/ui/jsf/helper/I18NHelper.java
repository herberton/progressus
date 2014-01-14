package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.enumerator.Language;
import br.com.hcs.progressus.helper.ConfigurationHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ParameterTO;

public class I18NHelper implements Serializable {

	
	private static final long serialVersionUID = 1000445796467032107L;
	private static final Logger logger = LoggerFactory.getLogger(I18NHelper.class);

	
	private static ResourceBundle getI18NResourceBundle(Locale locale) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(locale)) {
				
				locale = Language.getDefault().getLocale();
				
				if (ObjectHelper.isNullOrEmpty(locale)) {
					return null;
				}
				
			}
			
			return ResourceBundle.getBundle(ConfigurationHelper.UI_WEB_PACKAGE_I18N_PROPERTIES_BASE_NAME, locale);
			
		} catch (Exception e) {
			I18NHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public static String getText(MessageTO message){
		
		try {
		
			if (ObjectHelper.isNullOrEmpty(message)) {
				return "";
			}
			
			return I18NHelper.getText(JSFHelper.getLocale(), message);
		
		} catch (Exception e) {
			I18NHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	public static String getText(ParameterTO<String> parameter){
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(parameter)) {
				return "";
			}
			
			return I18NHelper.getText(JSFHelper.getLocale(), parameter);
		
		} catch (Exception e) {
			I18NHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	public static String getText(String i18n){
		
		try {
		
			i18n = i18n == null ? i18n : i18n.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(i18n)) {
				return "";
			}
			
			return I18NHelper.getText(JSFHelper.getLocale(), i18n);
		
		} catch (Exception e) {
			I18NHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	private static String getText(Locale locale, MessageTO message) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(locale)) {
				return "";
			}
			
			if (ObjectHelper.isNullOrEmpty(message)) {
				return "";
			}
			
			List<Object> parameterList = new ArrayList<>();
			
			for (ParameterTO<String> parameter : message.getParameterList()) {
				parameterList.add(I18NHelper.getText(locale, parameter));
			}
			
			return MessageFormat.format(I18NHelper.getText(locale, message.getText()), parameterList.toArray());
			
		} catch(Exception e){
			
			try {
				
				return locale.toString().concat(".").concat(message.getText());
				
			} catch (Exception e1) {
				I18NHelper.logger.warn(e.getMessage());
				I18NHelper.logger.warn(e1.getMessage());
			}
			
		}
		
		return "";
		
	}
	private static String getText(Locale locale, ParameterTO<String> parameter) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(parameter)) {
				return "";
			}
			
			return I18NHelper.getText(locale, parameter.getValue());
			
		} catch(Exception e){
			
			try {
				
				return locale.toString().concat(".").concat(parameter.getValue());
				
			} catch (Exception e1) {
				I18NHelper.logger.warn(e.getMessage());
				I18NHelper.logger.warn(e1.getMessage());
			}
		}
		
		return "";
	}
	private static String getText(Locale locale, String i18n) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(locale)) {
				
				locale = Language.getDefault().getLocale();
				
				if (ObjectHelper.isNullOrEmpty(locale)) {
					return "";
				}
				
			}
			
			ResourceBundle resourceBundle = I18NHelper.getI18NResourceBundle(locale);
			
			if (ObjectHelper.isNullOrEmpty(resourceBundle)) {
				return "";
			}
			
			i18n = i18n == null ? i18n : i18n.replaceAll(" ", ""); 
			
			if (StringHelper.isNullOrEmpty(i18n)) {
				return "";
			}
			
			return resourceBundle.getString(i18n);
			
		} catch(Exception e){
			
			try {
				
				return locale.toString().concat(".").concat(i18n);
				
			} catch (Exception e1) {
				
				I18NHelper.logger.warn(e.getMessage());
				I18NHelper.logger.warn(e1.getMessage());
			}
			
		}
		
		return "";
	}
}
