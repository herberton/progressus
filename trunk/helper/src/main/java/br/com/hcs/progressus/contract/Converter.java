package br.com.hcs.progressus.contract;

import java.io.Serializable;

import br.com.hcs.progressus.exception.common.ProgressusException;

public interface Converter<T> extends Serializable {
	T convert() throws ProgressusException;
}