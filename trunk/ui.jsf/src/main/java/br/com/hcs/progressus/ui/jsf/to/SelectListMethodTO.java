package br.com.hcs.progressus.ui.jsf.to;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ReflectionHelper;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.MethodTO;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.ParameterTO;

public class SelectListMethodTO<T extends ProgressusEntity<T>> extends MethodTO {

	private static final long serialVersionUID = 3346945989939853749L;

	
	public SelectListMethodTO() throws ProgressusException {
		this("selectList");
	}
	
	public SelectListMethodTO(String name) throws ProgressusException {
		super(name);
	}
	
	public SelectListMethodTO(String name, ParameterTO<?>[] parameterArray) throws ProgressusException {
		super(name, parameterArray);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterTO<?>> getDefaultParameterList() throws ProgressusException {
		
		try {
			List<ParameterTO<?>> defaultSelectListParameterList = new ArrayList<>();				
			
			defaultSelectListParameterList.add(new ParameterTO<Map>(Map.class, "parameterMap", null));
			defaultSelectListParameterList.add(new ParameterTO<Integer>(Integer.class, "firstResult", null));
			defaultSelectListParameterList.add(new ParameterTO<Integer>(Integer.class, "maxResult", null));
			defaultSelectListParameterList.add(new ParameterTO<OrderByTO>(OrderByTO.class, "orderBy", null));
			
			return defaultSelectListParameterList;
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDefaultParameterList", e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		
		try {
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			this.setParameterValue("parameterMap", parameterMap);
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo, Integer firstResult, Integer maxResult) throws ProgressusException {
		
		try {
			
			this.setParameterValue("firstResult", firstResult);
			this.setParameterValue("maxResult", maxResult);
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo, OrderByTO orderBy) throws ProgressusException {
		
		try {
			
			this.setParameterValue("orderBy", orderBy);
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		try {
			
			this.setParameterValue("parameterMap", parameterMap);
			this.setParameterValue("firstResult", firstResult);
			this.setParameterValue("maxResult", maxResult);
			this.setParameterValue("orderBy", orderBy);
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> execute(ProgressusBOEntityRemote<T> bo, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException {
		
		try {
			
			this.setParameterValue("firstResult", firstResult);
			this.setParameterValue("maxResult", maxResult);
			this.setParameterValue("orderBy", orderBy);
			
			return 
				(List<T>)
					ReflectionHelper
						.executeMethod(bo, this.getMethod(bo.getClass()), this.getParameterValueArray());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("execute", e);
		}
		
	}
}
