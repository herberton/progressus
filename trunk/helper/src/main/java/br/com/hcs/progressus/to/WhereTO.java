package br.com.hcs.progressus.to;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.MapHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;
import lombok.Setter;

public class WhereTO extends ProgressusTO<WhereTO> {

	private static final long serialVersionUID = 2906302393621217859L;
	private static final Logger logger = LoggerFactory.getLogger(WhereTO.class);
	
	@Setter
	private Map<String, WhereClauseTO> clauseMap = null;
	
	
	public Map<String, WhereClauseTO> getClauseMap() {
		return MapHelper.isNullOrEmptyReplaceByNewHashMap(this.clauseMap);
	}
	
	
	public WhereTO(){ super(); }
	public WhereTO(Collection<WhereClauseTO> clauseCollection){
		this();
		this.setClauseMapCollection(clauseCollection);
	}

	
	public void addClause(WhereClauseTO...clauseArray) throws InvalidParameterException {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(clauseArray)) {
				return;
			}
			
			if (MapHelper.isNullOrEmpty(this.getClauseMap())) {
				return;
			}
			
			Set<WhereClauseTO> clauseList = new HashSet<WhereClauseTO>(); 
			
			if(!Collections.addAll(clauseList, clauseArray)){
				throw new InvalidParameterException("restrictions");
			}
			
			clauseList.addAll(this.getClauseMap().values());
			
			this.setClauseMapCollection(clauseList);
			
		} catch (Exception e) {
			WhereTO.logger.warn(e.getMessage());
		}
	}
	
	private void setClauseMapCollection(Collection<WhereClauseTO> clauseCollection) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(clauseCollection)) {
				return;
			}
			
			this.setClauseMap(this.loadClauseMap(clauseCollection));
		
		} catch (Exception e) {
			WhereTO.logger.warn(e.getMessage());
		}
		
	}
	
	private Map<String, WhereClauseTO> loadClauseMap(Collection<WhereClauseTO> clauseCollection) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(clauseCollection)) {
				return new HashMap<>();
			}
			
			if (clauseCollection instanceof List) {
				clauseCollection = this.removeDuplicateClause((List<WhereClauseTO>)clauseCollection);			
			}
			
			Map<String, WhereClauseTO> clauseMap = new HashMap<>();
			
			for (WhereClauseTO whereClauseRestriction : clauseCollection) {
				
				if (ObjectHelper.isNullOrEmpty(whereClauseRestriction)) {
					continue;
				}
				
				clauseMap.put(whereClauseRestriction.toString(), whereClauseRestriction);
			}
			
			return clauseMap;
			
		} catch (Exception e) {
			WhereTO.logger.warn(e.getMessage());
		}
		
		return new HashMap<>();
	}
	
	private Collection<WhereClauseTO> removeDuplicateClause(List<WhereClauseTO> clauseList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(clauseList)) {
				return new ArrayList<>();
			}
			
			Iterator<WhereClauseTO> iterator = clauseList.iterator();
			
			while (iterator.hasNext()) {
				
				WhereClauseTO restriction = iterator.next();
				
				if (clauseList.indexOf(restriction) != clauseList.lastIndexOf(restriction)) {
					iterator.remove();
				}
				
			}
			
			return clauseList;
			
		} catch (Exception e) {
			WhereTO.logger.warn(e.getMessage());
		}
		
		return new ArrayList<>();
	}
	
	@Override
	public String toString() {
		
		try {
			
			if (MapHelper.isNullOrEmpty(this.getClauseMap())) {
				return "";
			}
			
			if (this.getClauseMap().size() <= 0) {
				return "";
			}
			
			StringBuffer jpql = new StringBuffer(" WHERE ");
			
			Iterator<String> iterator = this.getClauseMap().keySet().iterator();
			
			while (iterator.hasNext()) {
				
				jpql.append(iterator.next());
				
				if (iterator.hasNext()) {
					jpql.append(" AND ");
				}
			}
			
			return jpql.toString();
			
		} catch (Exception e) {
			WhereTO.logger.warn(e.getMessage());
		}
		
		return "";
	}

}
