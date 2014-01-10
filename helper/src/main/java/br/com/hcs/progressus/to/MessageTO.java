package br.com.hcs.progressus.to;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;

public class MessageTO extends ProgressusTO<MessageTO> {
	
	
	private static final long serialVersionUID = -7411047647290080089L;
	
	
	@Getter
	@Setter
	private String text;
	@Setter
	private List<ParameterTO<String>> parameterList;
	
	
	public List<ParameterTO<String>> getParameterList() {
		if (this.parameterList == null) {
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
		if (throwable instanceof ProgressusException) {
			ProgressusException ProgressusException = (ProgressusException)throwable;
			this.setText(ProgressusException.convert().getText());
			this.setParameterList(ProgressusException.convert().getParameterList());
		}
		this.setText(StringHelper.getI18N(throwable.getClass()));
	}
}
