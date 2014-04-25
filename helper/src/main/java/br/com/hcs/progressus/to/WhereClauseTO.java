package br.com.hcs.progressus.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@AllArgsConstructor
public class WhereClauseTO extends ProgressusTO<WhereClauseTO> {

	private static final long serialVersionUID = -7179092103491131997L;

	@Getter
	@Setter
	private String field;
	@Getter
	@Setter
	private WhereClauseOperator operator;
	@Getter
	@Setter
	private boolean addEntityPrefix = false;
	
	
	private String getCorrectedField() {
		
		try {
			
			if (StringHelper.isNullOrEmpty(this.getField())) {
				return "";
			}
			
			if (this.getField().startsWith("entity.") && !this.isAddEntityPrefix()) {
				
				return this.getField().replace("entity.", "");
			
			}else if (!this.getField().startsWith("entity.") && this.isAddEntityPrefix()) {
				
				return String.format("entity.%s", this.getField());
			
			}
			
			return this.getField();
			
		} catch (Exception e) {
			WhereClauseTO.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (this.getOperator() == null) {
				return "";
			}
			
			if (StringHelper.isNullOrEmpty(this.getField())) {
				return "";
			}
			
			String correctedField = this.getCorrectedField();
			
			if (StringHelper.isNullOrEmpty(correctedField)) {
				return "";
			}
			
			if (this.getOperator().equals(WhereClauseOperator.IS_NULL) || 
				this.getOperator().equals(WhereClauseOperator.IS_NOT_NULL) ||
				this.getOperator().equals(WhereClauseOperator.IS_EMPTY) ||
				this.getOperator().equals(WhereClauseOperator.IS_NOT_EMPTY)) {
				
				return String.format("%s %s", correctedField, this.getOperator().toString());
				
			}
			
			return String.format("%s %s :%s", correctedField, this.getOperator().toString(), this.getField().replace(".", "_"));
		
		} catch (Exception e) {
			WhereClauseTO.log.error(e.getMessage(), e);
		}
		
		return "";
	}

	@Override
	public boolean equals(Object object) {
		
		try {
			
			if (object == null) {
				return false;
			}
			
			if (!(object instanceof WhereClauseTO)) {
				return false;
			}
			
			WhereClauseTO other = (WhereClauseTO)object;
			
			return
				other.getField().equals(this.getField()) &&
				other.getOperator().equals(this.getOperator());
			
		} catch (Exception e) {
			
			WhereClauseTO.log.error(e.getMessage(), e);
		
		}
		
		return false;
	}
}
