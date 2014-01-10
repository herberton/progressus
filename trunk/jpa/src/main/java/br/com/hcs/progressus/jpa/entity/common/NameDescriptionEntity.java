package br.com.hcs.progressus.jpa.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class NameDescriptionEntity<T extends NameDescriptionEntity<T>> extends NameEntity<T> {
	
	private static final long serialVersionUID = 3742799029183018437L;

	@Getter
	@Setter
	@Column
	private String description;
	
	public NameDescriptionEntity() {
		super();
	}
   
}
