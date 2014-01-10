package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import br.com.hcs.progressus.enumerator.Language;



public class LocaleHelper implements Serializable {

	private static final long serialVersionUID = -3478588183633298887L;
	
	private Language language;
	private String textlanguage;
	private String iconName;
	private Locale locale;

	public LocaleHelper(Language language) { 
		this.setLanguage(language); 
	}

	public String getTextlanguage() {
		return textlanguage;
	}
	public void setFullTextlanguage(String fullTextlanguage) {
		this.textlanguage = fullTextlanguage;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String imgLanguage) {
		this.iconName = imgLanguage;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language == null ? Language.PT_BR : language;

		switch (this.getLanguage()) {
			case ES_ES:
				this.setFullTextlanguage("Espa�ol - ES");
				this.setIconName("icoES.png");
				this.setLocale(new Locale("es", "ES"));
				break;
			case EN_US:
				this.setFullTextlanguage("English - US");
				this.setIconName("icoUS.png");
				this.setLocale(Locale.US);
				break;
			default:
				this.setFullTextlanguage("Portugu�s - Brasil");
				this.setIconName("icoBR.png");
				this.setLocale(new Locale("pt_BR"));
				break;
		}
	}
	
	
	public static Collection<LocaleHelper> getSupportedLocalecCollection() {
		Collection<LocaleHelper> supportedLocaleCollection = new ArrayList<LocaleHelper>();
		supportedLocaleCollection.add(new LocaleHelper(Language.PT_BR));
		supportedLocaleCollection.add(new LocaleHelper(Language.EN_US));
		supportedLocaleCollection.add(new LocaleHelper(Language.ES_ES));
		return supportedLocaleCollection;
	}
	public static LocaleHelper getInstence(Language language) {
		return new LocaleHelper(language);
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			return this.getLanguage().equals(((LocaleHelper)object).getLanguage());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return super.equals(object);
	}
	@Override
	public int hashCode() {
		return this.getLanguage() != null ? this.getLanguage().toString().hashCode() : super.hashCode(); 
	}

}
