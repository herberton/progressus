package br.com.hcs.progressus.enumerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
public enum SupportedLocale {
	
	PT_BR(new Locale("pt", "BR"), "Português - BR"),
	EN_US(Locale.US, "English - US"),
	ES_ES(new Locale("es", "ES"), "Español - ES");
		
	
	@Getter
	@Setter(AccessLevel.PRIVATE)
	private Locale locale;
	@Getter
	@Setter(AccessLevel.PRIVATE)
	private String text;
		
	
	private SupportedLocale(Locale locale, String text) {
		this.setLocale(locale);
		this.setText(text);
	}
	
	
	public static final SupportedLocale getDefault() {
		return SupportedLocale.PT_BR;
	}
	public static final List<SupportedLocale> getSupportedLocalecList() {
		try {
			List<SupportedLocale> supportedLocaleList = new ArrayList<SupportedLocale>();
			supportedLocaleList.add(SupportedLocale.PT_BR);
			supportedLocaleList.add(SupportedLocale.EN_US);
			supportedLocaleList.add(SupportedLocale.ES_ES);
			return supportedLocaleList;
		} catch (Exception e) {
			SupportedLocale.log.error(e.getMessage(), e);
		}
		return new ArrayList<SupportedLocale>(); 
	}

	
	public String getIconName() {
		try {
			if (this.getLocale() == null) {
				return "pt.BB.png";
			}
			return this.getLocale().getLanguage() + "." + this.getLocale().getCountry() + ".png";
		} catch (Exception e) {
			SupportedLocale.log.error(e.getMessage(), e);
		}
		return "pt.BB.png";
	}
	
	
	@Override
	public String toString() {
		return this.getText();
	}
	
	
	public static final SupportedLocale get(String value) {
		try {
			if (StringHelper.isNullOrEmpty(value)) {
				return SupportedLocale.getDefault();
			}
			for (SupportedLocale supportedLocale : SupportedLocale.values()) {
				if (supportedLocale.toString().equals(value)) {
					return supportedLocale;
				}
			}
		} catch (Exception e) {
			SupportedLocale.log.error(e.getMessage(), e);
		}
		return SupportedLocale.getDefault();
	}
}
