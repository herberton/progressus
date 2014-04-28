package br.com.hcs.progressus.ui.jsf.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.ui.jsf.helper.I18NHelper;

@Slf4j
public class DefaultValidator implements Serializable, Validator {
	
	private static final long serialVersionUID = -8293905874918818624L;

	public boolean isNullOrEmpty(Object value) {
		if(value == null) return true;
		return value.toString().trim().equals("");
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)	throws ValidatorException {
		UIInput input = (UIInput) component;

		if(input.isRequired() && isNullOrEmpty(value)) {
			
			FacesMessage facesMessage = null;
			try {
				facesMessage = input.getRequiredMessage() != null ? 
					new FacesMessage(input.getRequiredMessage()) : 
					new FacesMessage(I18NHelper.getText("javax.faces.component.UIInput.REQUIRED"));
			} catch (Exception e) {
				DefaultValidator.log.error(e.getMessage(), e);
			}
			throw new ValidatorException(facesMessage);
		}
	}
	
	public String getValidatorMessage(UIComponent component) {
		return ((UIInput) component).getValidatorMessage();
	}
	
	public FacesMessage getMessage(FacesMessage.Severity severity, String internationalizedMessage) {
		return new FacesMessage(severity, internationalizedMessage, "");
	}
	
	public void throwInternationalizedMessage(String internationalizedMessage) throws ValidatorException {
		throw new ValidatorException(getMessage(FacesMessage.SEVERITY_ERROR, internationalizedMessage));

	}
}
