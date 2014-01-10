package br.com.hcs.progressus.contract;

import java.lang.reflect.Field;
import java.util.List;

public interface ClassInformer extends ClassValidator {
	List<Field> getPublicFieldList(ClassValidator validator);
	List<Field> getPublicFieldList();
}
