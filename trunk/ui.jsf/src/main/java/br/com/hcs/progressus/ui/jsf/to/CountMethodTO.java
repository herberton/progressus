package br.com.hcs.progressus.ui.jsf.to;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.MethodTO;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class CountMethodTO extends MethodTO {

	
	private static final long serialVersionUID = -1698601472103190087L;

	
	public CountMethodTO() throws ProgressusException {
		super("count");
	}
	
	public CountMethodTO(String name) throws ProgressusException {
		super(name);
	}
	
	public CountMethodTO(String name, ParameterTO<?>[] parameterArray) throws ProgressusException {
		super(name, parameterArray);
	}

	
	@Override
	@SuppressWarnings("rawtypes")
	public List<ParameterTO<?>> getDefaultParameterList() throws ProgressusException {
		
		try {
			
			List<ParameterTO<?>> defaultSelectListParameterList = new ArrayList<>();				
			
			defaultSelectListParameterList.add(new ParameterTO<Map>(Map.class, "parameterMap", null));
			
			return defaultSelectListParameterList;
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CountMethodTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getDefaultParameterList");
		}
	}

	
	public <T extends ProgressusEntity<T>> int execute(ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		try {
			return (int)ReflectionHelper.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CountMethodTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("execute");
		}
	}
	
	public <T extends ProgressusEntity<T>> int execute(ProgressusBOEntityRemote<T> bo, Map<String, Object> parameterMap) throws ProgressusException {
		try {
			this.setParameterValue("parameterMap", parameterMap);
			
			return (int)ReflectionHelper.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CountMethodTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("execute");
		}
	}
}
