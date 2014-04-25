package br.com.hcs.progressus.ui.jsf.ldm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	
	@Getter
	@Setter
	private ProgressusMB<? extends ProgressusMB<?>> mb;
	@Getter
	@Setter
	private ProgressusBOEntityRemote<T> bo;
	@Setter
	private SelectListMethodTO<T> selectList;
	@Setter
	private CountMethodTO count;
	
	
	public LDM(ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		super();
		try {
			this.setBo(bo);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("LDM");
		}
	}
	
	public LDM(ProgressusMB<? extends ProgressusMB<?>> ProgressusMB, ProgressusBOEntityRemote<T> bo) throws ProgressusException {
		this(bo);
		try {
			this.setMb(ProgressusMB);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("LDM");
		}
	}
	
	public LDM(ProgressusBOEntityRemote<T> bo, SelectListMethodTO<T> selectList, CountMethodTO count) throws ProgressusException {
		
		this(bo);
		
		try {
			
			this.setSelectList(selectList);
			this.setCount(count);
			
			this.setRowCount(this.getCount().execute(this.getBo(), new HashMap<String, Object>()));
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("LDM");
		}
	} 
	
	public LDM(ProgressusMB<? extends ProgressusMB<?>> ProgressusMB, ProgressusBOEntityRemote<T> bo, SelectListMethodTO<T> selectList, CountMethodTO count) throws ProgressusException {
		this(bo, selectList, count);
		try {
			this.setMb(ProgressusMB);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("LDM");
		}
	} 
	
	
	public SelectListMethodTO<T> getSelectList() throws ProgressusException {
		if (this.selectList == null) {
			this.setSelectList(new SelectListMethodTO<T>());
		}
		return this.selectList;
	}
	
	
	public CountMethodTO getCount() throws ProgressusException  {
		if (this.count == null) {
			this.setCount(new CountMethodTO());
		}
		return this.count;
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
			return this.getSelectList().execute(this.getBo(), parameterMap, 0, 1, new OrderByTO()).get(0);
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
			
			Map<String, Object> parameterMap = this.getParameterMap(filters);
			OrderByTO orderBy = new OrderByTO(sortField, orderByType);
			
			entityList = this.getSelectList().execute(this.getBo(), parameterMap, first, maxResult, orderBy);
			
			this.setRowCount(this.getCount().execute(this.getBo(), parameterMap));
			
			this.loadColumnList();
		
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			LDM.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("load"));
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
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("loadColumnList");
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
			LDM.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getParameterMap");
		}
	}
}
