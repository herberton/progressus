package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum OrderByType {
	
	ASC(true),
	DESC(false);
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private boolean asc;
	
	private OrderByType(boolean asc) {
		this.setAsc(asc);
	}
	
	public boolean isDesc() {
		return !this.isAsc();
	}
	
	@Override
	public String toString() {
		return this.isAsc() ? "ASC" : "DESC";
	}
}
