package br.com.hcs.progressus.ui.jsf.ldm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.hcs.progressus.client.ejb.sb.bo.entity.ProgressusBOEntityRemote;
import br.com.hcs.progressus.enumerator.OrderByType;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.ui.jsf.helper.JSFMessageHelper;
import br.com.hcs.progressus.ui.jsf.mb.ProgressusMB;
import br.com.hcs.progressus.ui.jsf.to.CountMethodTO;
import br.com.hcs.progressus.ui.jsf.to.SelectListMethodTO;

@Slf4j
public class LDM<T extends ProgressusEntity<T>> extends LazyDataModel<T> {
	
	private static final long serialVersionUID = -6788902683039436729L;
	
	
	public LDM(ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		super();
		try {
			this.setBo(bo);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("LDM", e);
		}
	}
	
	public LDM(ProgressusMB<? extends ProgressusMB<?>> ProgressusMB, ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		this(bo);
		try {
			this.setMb(ProgressusMB);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("LDM", e);
		}
	}
	
	public LDM(ProgressusBOEntityRemote<T> bo, SelectListMethodTO<T> selectListMethod, CountMethodTO countMethod) throws ProgressusException {
		
		this(bo);
		
		try {
			
			this.setSelectListMethod(selectListMethod);
			this.setCountMethod(countMethod);
			
			this.setRowCount(this.getCount());
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("LDM", e);
		}
	}

	public LDM(ProgressusMB<? extends ProgressusMB<?>> ProgressusMB, ProgressusBOEntityRemote<T> bo, SelectListMethodTO<T> selectListMethod, CountMethodTO countMethod) throws ProgressusException {
		this(bo, selectListMethod, countMethod);
		try {
			this.setMb(ProgressusMB);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("LDM", e);
		}
	}
	
	
	@Getter
	@Setter
	private ProgressusMB<? extends ProgressusMB<?>> mb;
	@Getter
	@Setter
	private ProgressusBOEntityRemote<T> bo;
	@Setter
	private SelectListMethodTO<T> selectListMethod;
	@Setter
	private CountMethodTO countMethod;
	@Setter(AccessLevel.PROTECTED)
	private Map<String, Object> parameterMap;
	
	
	public SelectListMethodTO<T> getSelectListMethod() throws ProgressusException {
		try {
			if (this.selectListMethod == null) {
				this.setSelectListMethod(new SelectListMethodTO<T>());
			}
			return this.selectListMethod;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSelectListMethod", e);
		}
	}
	
	
	public CountMethodTO getCountMethod() throws ProgressusException {
		try {
			if (this.countMethod == null) {
				this.setCountMethod(new CountMethodTO());
			}
			return this.countMethod;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getCountMethod", e);
		}
	}
	
	public Map<String, Object> getParameterMap() throws ProgressusException {
		try {
			if (this.parameterMap == null) {
				this.setParameterMap(new HashMap<String, Object>());
			}
			return this.parameterMap;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameterMap", e);
		}
	}
	
	
	public int getCount() throws ProgressusException {
		try {
			return this.getCountMethod().execute(this.getBo(), this.getParameterMap());
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getCount", e);
		}
	}
	
	public List<T> getSelectList() throws ProgressusException {
		try {
			return this.getSelectListMethod().execute(this.getBo(), this.getParameterMap());
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getCount", e);
		}
	}
	
	
	@Override
    public void setRowIndex(int rowIndex) {
		
        try {
			
        	if (rowIndex == -1 || super.getPageSize() == 0) {
			    super.setRowIndex(-1);
			}
			else {
			    super.setRowIndex(rowIndex % super.getPageSize());
			}
			
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
		}
        
    }
	
	
	@Override
	public Object getRowKey(T entity) {
		try {
			return entity.getId();
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	@Override
	public T getRowData(String id) {
		try {
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("id", Long.valueOf(id));
			return this.getSelectListMethod().execute(this.getBo(), parameterMap, 0, 1, new OrderByTO()).get(0);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		
		List<T> entityList = null;
		
		try {
		
			OrderByType orderByType = OrderByType.DESC; 
			
			if(sortOrder.toString().substring(0, 3).toUpperCase().equals("ASC")){
				orderByType = OrderByType.ASC;
			}
			
			int maxResult = (first + pageSize) - first;
			
			this.setParameterMap(this.getParameterMap(filters));
			OrderByTO orderBy = new OrderByTO(sortField, orderByType);
			
			entityList = this.getSelectListMethod().execute(this.getBo(), this.getParameterMap(), first, maxResult, orderBy);
			
			this.setRowCount(getCount());
			
			this.loadColumnList();
		
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("load", e));
		}
		
		return entityList;
	}
	
	
	protected void loadColumnList() throws ProgressusException {
		try {
			if (this.getMb() != null) {
				this.getMb().loadColumnList(this.getBo().getEntityClass());
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("loadColumnList", e);
		}
	}
	
	
	protected Map<String, Object> getParameterMap(Map<String, String> filters) throws ProgressusException {
 
		try {
			
			Map<String, Object> parameterMap = new HashMap<>();
			
			if(filters != null && filters.keySet().size() > 0) {
				
				Iterator<String> iterator = filters.keySet().iterator();
				while (iterator.hasNext()) {
					
					String key = iterator.next();
					String value = filters.get(key);
					
					if(key.endsWith("id")) {
						
						try {
							
							parameterMap.put(key, Long.parseLong(value));
							
						} catch (NumberFormatException e) {
							
							parameterMap.put(key, value);
							
						}
						
					} else if("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
						
						parameterMap.put(key, Boolean.parseBoolean(value));
						
					} else if(key.endsWith("EnumType")) {
						
						parameterMap.put(key.replace("EnumType", ""), value);
						
					} else {
						
						try {
							
							parameterMap.put(key, Float.parseFloat(value));
							
						} catch (NumberFormatException e) {
							
							parameterMap.put(key, value);
							
						}
					}
				}		
			}
			
			return parameterMap;
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameterMap", e);
		}
	}
}

