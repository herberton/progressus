package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
public enum REGEX {

	EMAIL("^\\s*(([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5}){1,25})+([;.](([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5}){1,25})+)*\\s*$");
	
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String value;
	
	
	private REGEX(String value) {
		this.setValue(value);
	}
	
	
	@Override
	public String toString() {
		try {
			return StringHelper.isNullOrEmpty(this.getValue()) ? "" : this.getValue();
		} catch (Exception e) {
			REGEX.log.error(e.getMessage(), e);
		}
		return "";
	}
}
