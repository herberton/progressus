package br.com.hcs.progressus.contract;

import br.com.hcs.progressus.exception.ProgressusException;

public interface Converter<T> {
	T convert() throws ProgressusException;
}