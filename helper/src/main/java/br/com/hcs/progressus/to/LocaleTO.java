package br.com.hcs.progressus.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.enumerator.Language;
import br.com.hcs.progressus.helper.ObjectHelper;



public class LocaleTO implements Serializable {

	private static final long serialVersionUID = -3478588183633298887L;
	private static final Logger logger = LoggerFactory.getLogger(LocaleTO.class);
	
	
	@Getter
	private Language language;
	@Getter
	@Setter
	private String textlanguage;
	@Getter
	@Setter
	private String iconName;
	@Getter
	@Setter
	private Locale locale;

	
	public LocaleTO(Language language) { 
		this.setLanguage(language); 
	}

	
	public void setLanguage(Language language) {
		
		try {
			
			this.language = language == null ? Language.PT_BR : language;

			switch (this.getLanguage()) {
				case ES_ES:
					this.setTextlanguage("Español - ES");
					this.setIconName("icoES.png");
					this.setLocale(new Locale("es", "ES"));
					break;
				case EN_US:
					this.setTextlanguage("English - US");
					this.setIconName("icoUS.png");
					this.setLocale(Locale.US);
					break;
				default:
					this.setTextlanguage("Português - Brasil");
					this.setIconName("icoBR.png");
					this.setLocale(new Locale("pt_BR"));
					break;
			}
			
		} catch (Exception e) {
			LocaleTO.logger.warn(e.getMessage());
		}
	}
	
	
	public static Collection<LocaleTO> getSupportedLocalecCollection() {
		
		try {
			
			Collection<LocaleTO> supportedLocaleCollection = new ArrayList<>();
			
			supportedLocaleCollection.add(new LocaleTO(Language.PT_BR));
			supportedLocaleCollection.add(new LocaleTO(Language.EN_US));
			supportedLocaleCollection.add(new LocaleTO(Language.ES_ES));
		
			return supportedLocaleCollection;
		
		} catch (Exception e) {
			LocaleTO.logger.warn(e.getMessage());
		}
		
		return new ArrayList<>();
	}
	
	public static LocaleTO newInstance(Language language) {
		
		try {
			
			return new LocaleTO(language);
			
		} catch (Exception e) {
			LocaleTO.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	
	@Override
	public boolean equals(Object object) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(object)) {
				return false;
			}
			
			if (!(object instanceof LocaleTO)) {
				return false;
			}
			
			return this.getLanguage().equals(((LocaleTO)object).getLanguage());
			
		} catch (Exception e) {
			LocaleTO.logger.warn(e.getMessage());
		}	
		
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		
		try {
			
			return this.getLanguage() != null ? this.getLanguage().toString().hashCode() : super.hashCode();
			
		} catch (Exception e) {
			LocaleTO.logger.warn(e.getMessage());
		} 
		
		return super.hashCode();
	}

}
