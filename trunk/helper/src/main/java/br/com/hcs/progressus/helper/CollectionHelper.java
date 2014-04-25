package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

@Slf4j
public final class CollectionHelper implements Serializable {

	private static final long serialVersionUID = 6362253797266179520L;

	
	public static final <T> boolean isNullOrEmpty(T[] array) throws ProgressusException {
		try {
			return array == null || array.length <= 0;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("isNullOrEmpty");
		}
	}
	
	public static final <T> boolean isNullOrEmpty(Collection<T> collection) throws ProgressusException {
		try {
			return collection == null || collection.size() <= 0;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("isNullOrEmpty");
		}
	}
	
	
	public static final <X> void add(boolean alterExistentChild, Collection<X> collection, X newItem) throws ProgressusException {
		try {
			
			if (collection == null) {
				return;
			}
			if (newItem == null) {
				return;
			}
			
			if (!collection.contains(newItem)) {
				collection.add(newItem);
				return;
			}
			
			if(alterExistentChild) {
				((List<X>)collection).set(((List<X>)collection).indexOf(newItem), newItem);
				return;
			}
			
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	public static final <X> void add(boolean alterExistentChild, Collection<X> collection, Collection<X> newCollection) throws ProgressusException {
		try {
			
			if (newCollection == null) {
				return;
			}
			
			for (X newItem : newCollection) {
				CollectionHelper.add(alterExistentChild, collection, newItem);
			}
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	@SafeVarargs
	public static final <X> void add(boolean alterExistentChild, Collection<X> collection, X...newItemArray) throws ProgressusException {
		try {
			if (newItemArray == null) {
				return;
			}
			CollectionHelper.add(alterExistentChild, collection, Arrays.asList(newItemArray));
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	
	public static final <X> void add(Collection<X> collection, X newItem) throws ProgressusException {
		try {
			CollectionHelper.add(false,  collection, newItem);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	public static final <X> void add(Collection<X> collection, Collection<X> newcollection) throws ProgressusException {
		try {
			CollectionHelper.add(false,  collection, newcollection);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	@SafeVarargs
	public static final <X> void add(Collection<X> collection, X...newItemArray) throws ProgressusException {
		try {
			CollectionHelper.add(false,  collection, newItemArray);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("add");
		}
	}
	
	
	public static final <X> void remove(Collection<X> collection, X remove) throws ProgressusException {
		try {
			if (collection == null) {
				return;
			}
			if (remove == null) {
				return;
			}
			if (collection.contains(remove)) {
				collection.remove(remove);
			}
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("remove");
		}
	}
	
	public static final <X> void remove(Collection<X> collection, Collection<X> removeCollection) throws ProgressusException {
		try {
			if (removeCollection == null) {
				return;
			}
			for (X remove : removeCollection) {
				CollectionHelper.remove(collection, remove);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("remove");
		}
	}
	
	@SafeVarargs
	public static final <X> void remove(Collection<X> collection, X...removeArray) throws ProgressusException {
		try {
			if (removeArray == null) {
				return;
			}
			CollectionHelper.remove(collection, Arrays.asList(removeArray));
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			CollectionHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("remove");
		}
	}
}
