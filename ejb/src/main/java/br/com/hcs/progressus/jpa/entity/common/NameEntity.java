package br.com.hcs.progressus.jpa.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class NameEntity<T extends NameEntity<T>> extends ProgressusEntity<T> {

	
	private static final long serialVersionUID = -7851023524301897159L;
	
	
	@Getter
	@Setter
	@Column
	private String name;
	
	public NameEntity() {
		super();
	}
   
}
