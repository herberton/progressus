package br.com.hcs.progressus.server.jpa.listener;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.EntityStatus;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;

@Slf4j
public class ProgressusEntityListener implements Serializable {

	private static final long serialVersionUID = -316695133919524607L;

	@PrePersist
	public <T extends ProgressusEntity<T>> void prePersist(T entity) {
		try {
			if (entity.getCreationDate() ==  null) {
				entity.setCreationDate(Calendar.getInstance());	
			}
			entity.setEntityStatus(EntityStatus.ACTIVE);
		} catch (Exception e) {
			ProgressusEntityListener.log.error(e.getMessage(), e);
		}
	}

	@PreUpdate
	public  <T extends ProgressusEntity<T>> void preUpdate(T entity) {
		try {
			entity.setModificationDate(Calendar.getInstance());
		} catch (Exception e) {
			ProgressusEntityListener.log.error(e.getMessage(), e);
		}
	}
}
