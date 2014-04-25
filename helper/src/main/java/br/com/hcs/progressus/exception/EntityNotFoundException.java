package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class EntityNotFoundException extends ProgressusException {
	
	private static final long serialVersionUID = -1820383596020989219L;
	
	public EntityNotFoundException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			EntityNotFoundException.log.error(e.getMessage(), e);
		}
	}
	public EntityNotFoundException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			EntityNotFoundException.log.error(e.getMessage(), e);
		}
	}
}
