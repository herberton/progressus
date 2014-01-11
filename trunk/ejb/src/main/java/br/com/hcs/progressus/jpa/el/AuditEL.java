package br.com.hcs.progressus.jpa.el;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.hcs.progressus.enumerator.Status;
import br.com.hcs.progressus.jpa.entity.AuditEntity;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;

public class AuditEL implements Serializable {

	private static final long serialVersionUID = 6772878854812949940L;

	
	@PrePersist
	public <T extends ProgressusEntity<T>> void prePersist(T entity) {
		this.println(entity, "prePersist");
		entity.setStatus(Status.ACTIVE);
		if (entity.isAudited()) {
			entity.setAudit(new AuditEntity());
			entity.getAudit().setUserCreation(entity.getLoggedUser());
			entity.getAudit().setCreationAt(Calendar.getInstance());
		}
	}

	@PreUpdate
	public <T extends ProgressusEntity<T>> void preUpdate(T entity) {
		this.println(entity, "preUpdate");
		if (entity.isAudited()) {
			entity.getAudit().setUserModification(entity.getLoggedUser());
			entity.getAudit().setModificationAt(Calendar.getInstance());
		}
	}
	
	private <T extends ProgressusEntity<T>> void println(T entity, String eventName) {
		if (entity == null) { return; }
		System.out.println(String.format("%s - AuditEL.%s", entity.getClass(), eventName));
	}
}
