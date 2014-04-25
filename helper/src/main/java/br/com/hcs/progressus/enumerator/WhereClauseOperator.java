package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum WhereClauseOperator {

	EQUAL("="),
	DIFFERENT("<>"),
	GREATER(">"),
	GREATER_OR_EQUAL(">="),
	LESS("<"),
	LESS_OR_EQUAL("<="),
	LIKE("LIKE"),
	NOT_LIKE("NOT LIKE"),
	IS_NULL("IS NULL"),
	IS_NOT_NULL("IS NOT NULL"),
	IS_EMPTY("IS EMPTY"),
	IS_NOT_EMPTY("IS NOT EMPTY");
	
	@Getter
	@Setter(value=AccessLevel.PRIVATE)
	private String operator;
	
	private WhereClauseOperator(String operator) {
		this.setOperator(operator);
	}
	
	@Override
	public String toString() {
		return this.getOperator();
	}
	
}
