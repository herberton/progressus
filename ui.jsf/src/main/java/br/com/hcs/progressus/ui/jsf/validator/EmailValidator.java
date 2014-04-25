package br.com.hcs.progressus.ui.jsf.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import br.com.hcs.progressus.enumerator.REGEX;


@FacesValidator("emailValidator")
public class EmailValidator extends DefaultValidator {

	private static final long serialVersionUID = -9176144814221476407L;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		super.validate(context, component, value);
		Matcher matcher = Pattern.compile(REGEX.EMAIL.toString()).matcher(value.toString());
		if(!matcher.matches()) {
			throwInternationalizedMessage(getValidatorMessage(component));
		}
	}
}
