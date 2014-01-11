package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import br.com.hcs.progressus.helper.ConfigurationHelper;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ParameterTO;

public class I18NHelper implements Serializable {

	private static final long serialVersionUID = 1000445796467032107L;

	private static ResourceBundle getI18NResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(ConfigurationHelper.UI_WEB_PACKAGE_I18N_PROPERTIES_BASE_NAME, locale);
	}
	
	public static String getText(MessageTO message){
		return I18NHelper.getText(JSFHelper.getLocale(), message);
	}
	public static String getText(ParameterTO<String> parameter){
		return I18NHelper.getText(JSFHelper.getLocale(), parameter);
	}
	public static String getText(String i18n){
		return I18NHelper.getText(JSFHelper.getLocale(), i18n);
	}
	private static String getText(Locale locale, MessageTO message) {
		try {
			
			List<Object> parameterList = new ArrayList<>();
			
			for (ParameterTO<String> parameter : message.getParameterList()) {
				parameterList.add(I18NHelper.getText(locale, parameter));
			}
			
			return MessageFormat.format(I18NHelper.getText(locale, message.getText()), parameterList.toArray());
			
		} catch(Exception e){
			return locale.toString().concat(".").concat(message.getText());
		}
	}
	private static String getText(Locale locale, ParameterTO<String> parameter){
		try {
			return I18NHelper.getText(locale, parameter.getValue());
		} catch(Exception e){
			return locale.toString().concat(".").concat(parameter.getValue());
		}
	}
	private static String getText(Locale locale, String i18n){
		try {
			return I18NHelper.getI18NResourceBundle(locale).getString(i18n);
		} catch(Exception e){
			return locale.toString().concat(".").concat(i18n);
		}
	}
}
