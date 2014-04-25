package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.MessageType;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public final class JSFMessageHelper implements Serializable {
	
	private static final long serialVersionUID = -2187139040679183965L;

	
	public static final void showMessage(ProgressusException ProgressusException) {
		try {
			
			if (ProgressusException == null) {
				return;
			}
			
			JSFMessageHelper
				.showMessage(
					ProgressusException.convert(), 
					ProgressusException.getDetail()
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, ProgressusException ProgressusException) {
		try {
			
			if (ProgressusException == null) {
				return;
			}
			
			JSFMessageHelper
				.showMessage(
					facesContext,
					ProgressusException.convert(), 
					ProgressusException.getDetail()
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(MessageTO summary) {
		try {
			
			JSFMessageHelper
				.showMessage(
					JSFMessageHelper.getSeverity(summary.getType()), 
					JSFMessageHelper.getText(summary),
					null
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, MessageTO summary) {
		try {
			
			JSFMessageHelper
				.showMessage(
					facesContext,
					JSFMessageHelper.getSeverity(summary.getType()), 
					JSFMessageHelper.getText(summary),
					null
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(MessageTO summary, MessageTO detail) {
		try {
			
			JSFMessageHelper
				.showMessage(
					JSFMessageHelper.getSeverity(summary.getType()), 
					JSFMessageHelper.getText(summary), 
					JSFMessageHelper.getText(detail)
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, MessageTO summary, MessageTO detail) {
		try {
			
			JSFMessageHelper
				.showMessage(
					facesContext,
					JSFMessageHelper.getSeverity(summary.getType()), 
					JSFMessageHelper.getText(summary), 
					JSFMessageHelper.getText(detail)
				);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(String summary) {
		try {
			JSFMessageHelper.showMessage(new FacesMessage(summary));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, String summary) {
		try {
			JSFMessageHelper.showMessage(facesContext, new FacesMessage(summary));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(String summary, String detail) {
		try {
			JSFMessageHelper.showMessage(new FacesMessage(summary, detail));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, String summary, String detail) {
		try {
			JSFMessageHelper.showMessage(facesContext, new FacesMessage(summary, detail));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(Severity severity, String summary, String detail) {
		try {
			JSFMessageHelper.showMessage(new FacesMessage(severity, summary, detail));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, Severity severity, String summary, String detail) {
		try {
			JSFMessageHelper.showMessage(facesContext, new FacesMessage(severity, summary, detail));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesMessage facesMessage) {
		try {
			JSFMessageHelper.showMessage(JSFHelper.getFacesContext(), facesMessage);
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void showMessage(FacesContext facesContext, FacesMessage facesMessage) {
		try {
			
			if (facesContext == null) {
				return;
			}
			
			if (facesMessage == null) {
				return;
			}
			
			facesContext.addMessage(null, facesMessage);
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
	}

	
	public static final Severity getSeverity(MessageType messageType) {
		try {
			if (messageType == null) {
				return FacesMessage.SEVERITY_INFO;
			}
			switch (messageType) {
				case ERROR: return FacesMessage.SEVERITY_ERROR;
				case FATAL: return FacesMessage.SEVERITY_FATAL;
				case INFORMATION: return FacesMessage.SEVERITY_INFO;
				case WARN: return FacesMessage.SEVERITY_WARN;
			}
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
		return FacesMessage.SEVERITY_INFO;
	}
	
	
	public static final String getText(MessageTO message) {
		try {
			if (message == null) {
				return "";
			}
			return I18NHelper.getText(message.getKey(), JSFMessageHelper.getArgumentArray(message.getParameterList()));
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final Object[] getArgumentArray(List<ParameterTO<?>> parameterList) {
		try {
			if (CollectionHelper.isNullOrEmpty(parameterList)) {
				return new Object[]{};
			}
			
			List<ParameterTO<Object>> parameterListObject = new ArrayList<>();
			for (ParameterTO<?> parameter : parameterList) {
				ParameterTO<Object> parameterObject = 
					new ParameterTO<Object>(parameter.getIndex(), Object.class, parameter.getName(), parameter.getValue());
				parameterListObject.add(parameterObject);
			}
			
			Collections.sort(parameterListObject);
			
			List<Object> argumentList = new ArrayList<>();
			for (ParameterTO<Object> parameterObject : parameterListObject) {
				argumentList.add(parameterObject.getValue());
			}
			
			return argumentList.toArray();
			
		} catch (Exception e) {
			JSFMessageHelper.log.error(e.getMessage(), e);
		}
		return new Object[]{};
	}

	
}
