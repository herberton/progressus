package br.com.hcs.progressus.ui.jsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.hcs.progressus.enumerator.SupportedLocale;

@FacesConverter(forClass= SupportedLocale.class, value = "supportedLocaleConverter")
public class SupportedLocaleConverter implements Converter, Serializable {
	
	private static final long serialVersionUID = -4644258890195516494L;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		
		if (value == null || value.trim().isEmpty()) {
			return SupportedLocale.getDefault();
		}
		
		try {
			return SupportedLocale.get(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		
		if (value == null || !(value instanceof SupportedLocale)) {
			return SupportedLocale.getDefault().toString();
		}
		
		try {
			return ((SupportedLocale)value).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
