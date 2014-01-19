package br.com.hcs.progressus.to;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;

public class MessageTO 
	extends 
		ProgressusTO<MessageTO> 
{
	
	private static final long serialVersionUID = -7411047647290080089L;
	private static final Logger logger = LoggerFactory.getLogger(MessageTO.class);
	
	
	@Getter
	@Setter
	private String text;
	@Setter
	private List<ParameterTO<String>> parameterList;
	
	
	public List<ParameterTO<String>> getParameterList() {
		if (CollectionHelper.isNullOrEmpty(this.parameterList)) {
			this.setParameterList(new ArrayList<ParameterTO<String>>());
		}
		return this.parameterList;
	}
	
	
	public MessageTO() { super(); }
	public MessageTO(String text) {
		this();
		this.setText(text);
	}
	public MessageTO(String text, List<ParameterTO<String>> parameterList) {
		this(text);
		this.setParameterList(parameterList);
	}
	public MessageTO(Throwable throwable) throws ProgressusException {
		
		this();
		
		try {
			
			if (throwable == null) {
				return;
			}
			
			if (throwable instanceof ProgressusException) {
				ProgressusException ProgressusException = (ProgressusException)throwable;
				this.setText(ProgressusException.convert().getText());
				this.setParameterList(ProgressusException.convert().getParameterList());
			}
			
			this.setText(StringHelper.getI18N(throwable.getClass()));
			
		} catch (Exception e) {
			MessageTO.logger.warn(e.getMessage());
		}
	}
}
