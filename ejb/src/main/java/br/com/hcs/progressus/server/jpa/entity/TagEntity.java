package br.com.hcs.progressus.server.jpa.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class TagEntity extends ProgressusEntity<TagEntity> {

	private static final long serialVersionUID = -3816236001741961476L;
	
	
	@Getter
	@Setter
	@Column
	private String epc;
	@Getter
	@Setter
	@Column
	private String situation;
	@Getter
	@Setter
	@Column
	private String data;
	@Getter
	@Setter
	@Column
	private Double rssi;
	@Getter
	@Setter
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar readingDate;
	
}
