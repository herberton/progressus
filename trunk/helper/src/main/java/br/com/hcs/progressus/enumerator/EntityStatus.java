package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
public enum EntityStatus {
	
	ACTIVE("active"),
	INACTIVE("inactive");
	
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String value;
	
	private EntityStatus(String value) {
		this.setValue(value);
	}
	
	
	@Override
	public String toString() {
		try {
			return StringHelper.isNullOrEmpty(this.getValue()) ? "" : this.getValue();
		} catch (Exception e) {
			EntityStatus.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public boolean isActive() {
		try {
			return this.equals(EntityStatus.ACTIVE);
		} catch (Exception e) {
			EntityStatus.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	public boolean isInactive() {
		try {
			return this.equals(EntityStatus.INACTIVE);
		} catch (Exception e) {
			EntityStatus.log.error(e.getMessage(), e);
		}
		return false;
	}
}
