package br.com.hcs.progressus.enumerator;

import java.util.Locale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum Language {
	
	PT_BR(new Locale("pt", "BR"), "Português - Brasil", "icoUS.png"),
	EN_US(Locale.US, "English - US", "icoBR.png"),
	ES_ES(new Locale("es", "ES"), "Español - ES", "icoES.png");
	
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private Locale locale;
	
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private String text;
	
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private String icon;
	
	
	private Language(Locale locale, String text, String icon) {
		this.setLocale(locale);
		this.setText(text);
	}
	
	
	@Override
	public String toString() {
		return this.getText();
	}
	
	
	public static Language getDefault() {
		return Language.PT_BR;
	}
}
