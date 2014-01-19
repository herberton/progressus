package br.com.hcs.progressus.ui.jsf.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassHelper {
	private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);
	public static List<Class<?>> findList(Class<? extends Annotation> annotationClazz) {
		try {
			
		} catch (Exception e) {
			ClassHelper.logger.warn(e.getMessage());
		}
		return new ArrayList<>();
	}
	
}
