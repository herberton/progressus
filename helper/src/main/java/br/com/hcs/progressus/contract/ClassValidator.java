package br.com.hcs.progressus.contract;

import java.io.Serializable;

public interface ClassValidator extends Serializable {
	boolean isValidClass(Class<?> clazz);
}
