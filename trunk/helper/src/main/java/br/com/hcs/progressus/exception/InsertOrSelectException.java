package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsertOrSelectException extends ProgressusException {
	 
	 private static final long serialVersionUID = -3580227553752904541L;

	 public InsertOrSelectException(String entityName) {
			super();
			try {
				super.getParameterList().add(new ParameterTO<>(0, entityName));
			} catch (ProgressusException e) {
				InsertOrSelectException.log.error(e.getMessage(), e);
			}
		}
		public InsertOrSelectException(Class<?> entityClazz) {
			super();
			try {
				super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
			} catch (ProgressusException e) {
				InsertOrSelectException.log.error(e.getMessage(), e);
			}
		}
		public InsertOrSelectException(String entityName, Throwable cause) {
			super(cause);
			try {
				super.getParameterList().add(new ParameterTO<>(0, entityName));
			} catch (ProgressusException e) {
				InsertOrSelectException.log.error(e.getMessage(), e);
			}
		}
		public InsertOrSelectException(Class<?> entityClazz, Throwable cause) {
			super(cause);
			try {
				super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
			} catch (ProgressusException e) {
				InsertOrSelectException.log.error(e.getMessage(), e);
			}
		}
	 
}
