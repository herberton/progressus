package br.com.hcs.progressus.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.contract.Converter;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.MessageTO;
import br.com.hcs.progressus.to.ParameterTO;

public class ProgressusException 
	extends 
		Exception 
	implements 
		Converter<MessageTO>
{
	
	
	private static final long serialVersionUID = -6010950482595141978L;
	
	
	@Getter
	@Setter
	private MessageTO detail;
	@Getter
	@Setter
	private Class<? extends ProgressusException> clazz;
	@Setter
	private List<ParameterTO<String>> parameterList; 
	
	
	public List<ParameterTO<String>> getParameterList() {
		if (this.parameterList == null) {
			this.setParameterList(new ArrayList<ParameterTO<String>>());
		}
		return parameterList;
	}
	
	
	public ProgressusException(Class<? extends ProgressusException> clazz) {
		super();
		this.setClazz(clazz);
	}
	public ProgressusException(Class<? extends ProgressusException> clazz, String message) {
		super(message);
		this.setClazz(clazz);
		this.setDetail(new MessageTO(message));
	}
	public ProgressusException(Class<? extends ProgressusException> clazz, Throwable cause) throws ProgressusException {
		super(cause);
		this.setClazz(clazz);
		this.setDetail(new MessageTO(cause));
	}
	
	
	@Override
	public MessageTO convert() throws ProgressusException {
		return new MessageTO(StringHelper.getI18N(this.getClazz()), this.getParameterList());
	}
}
