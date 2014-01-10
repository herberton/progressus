package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum Status {
	
	ACTIVE(true),
	INACTIVE(false);
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private boolean active;
	
	private Status(boolean active) {
		this.setActive(active);
	}
}
