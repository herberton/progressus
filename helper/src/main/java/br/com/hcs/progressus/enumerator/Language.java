package br.com.hcs.progressus.enumerator;

import java.util.Locale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum Language {
	
	PT_BR(new Locale("pt", "BR")),
	EN_US(Locale.US),
	ES_ES(new Locale("es", "ES"));
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private Locale locale;
	
	private Language(Locale locale) {
		this.setLocale(locale);
	}
	
	public static Language getDefault() {
		return Language.PT_BR;
	}
}
