package br.com.hcs.progressus.to;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.MessageType;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class MessageTO extends ProgressusTO<MessageTO> {

	private static final long serialVersionUID = -1861271656563151750L;
	
	@Getter
	@Setter
	@NonNull
	private MessageType type;
	@Getter
	@Setter
	@NonNull
	private String key;
	@Getter
	@Setter
	private List<ParameterTO<?>> parameterList;
	
	
	public MessageTO(Throwable throwable) throws ProgressusException {
		
		this();
		
		try {
			
			if (throwable == null) {
				return;
			}
			
			if (throwable instanceof ProgressusException) {
				ProgressusException exception = (ProgressusException)throwable;
				MessageTO to = exception.convert();
				this.setType(to.getType());
				this.setKey(to.getKey());
				this.setParameterList(to.getParameterList());
			}
			
			this.setKey(StringHelper.getI18N(throwable.getClass()));
			
		} catch (Exception e) {
			MessageTO.log.error(e.getMessage(), e);
		}
	}
	
	
	public static final MessageTO getInstance(String key) {
		try {
			return MessageTO.getInstance(MessageType.INFORMATION, key);
		} catch (Exception e) {
			MessageTO.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final MessageTO getInstance(MessageType type, String key, ParameterTO<?>...parameterArray) {
		try {
			if (type == null) {
				type = MessageType.INFORMATION;
			}
			if (StringHelper.isNullOrEmpty(key)) {
				return null;
			}
			if (parameterArray == null) {
				return new MessageTO(type, key);
			}
			return new MessageTO(type, key, Arrays.asList(parameterArray));
		} catch (Exception e) {
			MessageTO.log.error(e.getMessage(), e);
		}
		return null;
	}
}
