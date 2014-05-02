package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.ui.jsf.mb.ProgressusMB;
import br.com.hcs.progressus.ui.jsf.mb.SessionMB;

@Slf4j
public final class I18NHelper implements Serializable {

	private static final long serialVersionUID = 7274889604582319546L;

	
	public static final ResourceBundle getResourceBundle(Locale locale) {
		
		try {
			
			locale = 
				locale == null ? 
					SupportedLocale.getDefault().getLocale() :
					locale;
			
			return ResourceBundle.getBundle(Setting.WEB_PACKAGE_I18N_RESOURCE_BUNDLE.toString(), locale);
			
		} catch (Exception e) {
			I18NHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	public static final String getText(Locale locale, String key, Object...argumentArray) {
		
		try {
			if (argumentArray == null || argumentArray.length <= 0) {
				return I18NHelper.getText(locale, key);
			}
		} catch (Exception e) {
			return key;
		}
		
		try {
			
			for (int i = 0; i < argumentArray.length; i++) {
				
				if (argumentArray[i] == null) {
					argumentArray[i] = "";
				}
				
				if (argumentArray[i] instanceof String) {
					
					String argumentMessageBundle = "";
					
					if (StringHelper.isNullOrEmpty(argumentArray[i].toString())) {
						argumentArray[i] = argumentMessageBundle;
						continue;
					}
					
					try {
						argumentMessageBundle = I18NHelper.getText(locale, argumentArray[i].toString());
					} catch (Exception e) {
						argumentMessageBundle = argumentArray[i].toString();
						continue;
					}
					
					argumentArray[i] = argumentMessageBundle;
					continue;
				}
				
				if (argumentArray[i] instanceof Class<?>) {
					
					String argumentMessageBundle = "";
					
					try {
						argumentMessageBundle = I18NHelper.getText(locale, StringHelper.getI18N((Class<?>)argumentArray[i]));
					} catch (Exception e) {
						argumentMessageBundle = ((Class<?>)argumentArray[i]).getSimpleName();
						continue;
					}
					
					argumentArray[i] = argumentMessageBundle;
					continue;
				}
			}
		} catch (Exception e) {
			return key;
		}
		
		try {
			
			return MessageFormat.format(I18NHelper.getResourceBundle(locale).getString(key), argumentArray);
			
		} catch(Exception e) {
			I18NHelper.log.error(e.getMessage(), e);
		}
		
		return key;
	}
	
	public static final String getText(Locale locale, String key) {
		try {
			return I18NHelper.getResourceBundle(locale).getString(key);
		} catch(Exception e){
			return key;
		}
	}
	
	public static final String getText(String key, Object...argumentArray) {
		Locale locale = null;
		
		try {
			
			SessionMB session = ProgressusMB.getInstance(SessionMB.class);
			
			locale =
				session == null ? 
					SupportedLocale.getDefault().getLocale() : 
					session.getLocale();
					
			return I18NHelper.getText(locale, key, argumentArray);
			
		} catch (Exception e) {
			return key;
		}
	}
}
