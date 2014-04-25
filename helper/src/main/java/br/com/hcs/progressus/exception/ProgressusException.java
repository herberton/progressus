package br.com.hcs.progressus.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.contract.Converter;
import br.com.hcs.progressus.enumerator.MessageType;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ParameterTO;

@ToString(callSuper=true)
@Slf4j
public class 
		ProgressusException 
	extends 
		Exception 
	implements 
		Converter<MessageTO>
{
	private static final long serialVersionUID = -6010950482595141978L;

	@Getter
	@Setter
	private MessageTO detail;
	@Setter
	private List<ParameterTO<?>> parameterList; 
	
	
	public List<ParameterTO<?>> getParameterList() {
		try {
			if (this.parameterList == null) {
				this.setParameterList(new ArrayList<ParameterTO<?>>());
			}
		} catch (Exception e) {
			ProgressusException.log.error(e.getMessage(), e);
		}
		return this.parameterList;
	}
	
	
	public ProgressusException() {
		super();
	}
	public ProgressusException(String message) {
		super(message);
		this.setDetail(new MessageTO(MessageType.ERROR, message));
	}
	public ProgressusException(Throwable cause) {
		super(cause);
		try {
			this.setDetail(new MessageTO(cause));
		} catch (ProgressusException e) {
			ProgressusException.log.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public final MessageTO convert() throws ProgressusException {
		try {
			return new MessageTO(MessageType.ERROR, StringHelper.getI18N(this.getClass()), this.getParameterList());
		} catch (Exception e) {
			ProgressusException.log.error(e.getMessage(), e);
		}
		return null;
	}
}