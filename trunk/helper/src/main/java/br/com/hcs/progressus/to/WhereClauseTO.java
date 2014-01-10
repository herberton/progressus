package br.com.hcs.progressus.to;

import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.to.common.ProgressusTO;
import lombok.Getter;
import lombok.Setter;

public class WhereClauseTO extends ProgressusTO<WhereClauseTO>  implements Comparable<WhereClauseTO> {
	
	private static final long serialVersionUID = -5114677701570112411L;
	
	@Getter
	@Setter
	private String field;
	@Getter
	@Setter
	private WhereClauseOperator operator;
	@Getter
	@Setter
	private boolean addEntityPrefix = false;
	
	public WhereClauseTO(String field, WhereClauseOperator operator, boolean addEntityPrefix) {
		super();
		this.setField(field);
		this.setOperator(operator);
		this.setAddEntityPrefix(addEntityPrefix);
	}
	
	private String getCorrectedField(String field) {
		if (field.startsWith("entity.") && !this.isAddEntityPrefix()) {
			return this.getField().replace("entity.", "");
		}else if (!field.startsWith("entity.") && this.isAddEntityPrefix()) {
			return String.format("entity.%s", this.getField());
		}
		return field;
	}
	
	@Override
	public String toString() {
		
		if (this.getOperator().equals(WhereClauseOperator.IS_NULL) || 
			this.getOperator().equals(WhereClauseOperator.IS_NOT_NULL) ||
			this.getOperator().equals(WhereClauseOperator.IS_EMPTY) ||
			this.getOperator().equals(WhereClauseOperator.IS_NOT_EMPTY)) {
			
			return String.format("%s %s", this.getCorrectedField(this.getField()), this.getOperator().toString());
			
		}
		
		return String.format("%s %s :%s", this.getCorrectedField(this.getField()), this.getOperator().toString(), this.getField().replace(".", "_"));
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof WhereClauseTO)) {
			return false;
		}
		
		WhereClauseTO other = (WhereClauseTO)obj;
		
		return 
			other.getField().equals(this.getField()) &&
			other.getOperator().equals(this.getOperator());
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public int compareTo(WhereClauseTO other) {
		return this.equals(other) ? 0 : this.toString().compareTo(other.toString());
	}
}
