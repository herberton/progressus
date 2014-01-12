package br.com.hcs.progressus.exception.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.contract.Converter;
import br.com.hcs.progressus.helper.CollectionHelper;
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
	private static final Logger logger = LoggerFactory.getLogger(ProgressusException.class);
	
	
	@Getter
	@Setter
	private MessageTO detail;
	@Setter
	private List<ParameterTO<String>> parameterList; 
	
	
	public List<ParameterTO<String>> getParameterList() {
		return CollectionHelper.isNullReplaceByNewArrayList(this.parameterList);
	}
	
	
	public ProgressusException() {
		super();
	}
	public ProgressusException(String message) {
		super(message);
		this.setDetail(new MessageTO(message));
	}
	public ProgressusException(Throwable cause) throws ProgressusException {
		super(cause);
		this.setDetail(new MessageTO(cause));
	}
	
	
	@Override
	public MessageTO convert() throws ProgressusException {
		try {
			return new MessageTO(StringHelper.getI18N(this.getClass()), this.getParameterList());
		} catch (Exception e) {
			ProgressusException.logger.warn(e.getMessage());
		}
		return null;
	}
}
