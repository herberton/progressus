package br.com.hcs.progressus.server.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class EmailEntity extends ProgressusEntity<EmailEntity> {

	private static final long serialVersionUID = 9074321889683260301L;

	@Getter
	@Setter
	@Column
	private String description;
}
