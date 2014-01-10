package br.com.hcs.progressus.to;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.hcs.progressus.to.common.ProgressusTO;
import lombok.Setter;

public class WhereTO extends ProgressusTO<WhereTO> {

	private static final long serialVersionUID = 2906302393621217859L;
	
	@Setter
	private Map<String, WhereClauseTO> clauseMap = null;
	
	
	public Map<String, WhereClauseTO> getClauseMap() {
		if (clauseMap == null) {
			this.setClauseMap(new HashMap<String, WhereClauseTO>());
		}
		return clauseMap;
	}
	
	
	public WhereTO(){ super(); }
	public WhereTO(Collection<WhereClauseTO> clauseCollection){
		this();
		this.setClauseMapCollection(clauseCollection);
	}

	
	public void addClause(WhereClauseTO...clauses) throws InvalidParameterException {
		
		Set<WhereClauseTO> clauseList = new HashSet<WhereClauseTO>(); 
		
		if(!Collections.addAll(clauseList, clauses)){
			throw new InvalidParameterException("restrictions");
		}
		
		clauseList.addAll(this.getClauseMap().values());
		
		this.setClauseMapCollection(clauseList);
		
	}
	
	
	private void setClauseMapCollection(Collection<WhereClauseTO> clauseCollection) {
		this.setClauseMap(this.loadClauseMap(clauseCollection));
	}
	private Map<String, WhereClauseTO> loadClauseMap(Collection<WhereClauseTO> clauseCollection) {
		
		if (clauseCollection instanceof List) {
			clauseCollection = this.removeDuplicateClause((List<WhereClauseTO>)clauseCollection);			
		}
		
		Map<String, WhereClauseTO> clauseMap = new HashMap<>();
		
		for (WhereClauseTO whereClauseRestriction : clauseCollection) {
			clauseMap.put(whereClauseRestriction.toString(), whereClauseRestriction);
		}
		
		return clauseMap;
	}
	private Collection<WhereClauseTO> removeDuplicateClause(List<WhereClauseTO> clauseList) {
		Iterator<WhereClauseTO> iterator = clauseList.iterator();
		while (iterator.hasNext()) {
			WhereClauseTO restriction = iterator.next();
			if (clauseList.indexOf(restriction) != clauseList.lastIndexOf(restriction)) {
				iterator.remove();
			}
		}
		return clauseList;
	}
	
	@Override
	public String toString() {
		
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
	}

}
