package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.atatec.trugger.scan.ClassScan;
import org.atatec.trugger.scan.PackageScan;
import org.atatec.trugger.scan.ScanLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.helper.CollectionHelper;

public class ClassHelper implements Serializable {
	
	private static final long serialVersionUID = 386149031341420468L;
	private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);
	
	public static Set<Class<?>> findSet(Class<? extends Annotation> annotationClazz, String classPackage, ScanLevel scanLevel) {
		
		try {
			
			PackageScan packageScan = new PackageScan(classPackage, scanLevel);
			
			@SuppressWarnings("rawtypes")
			Set<Class> foundClazzSet = 
				ClassScan.findClasses().recursively().annotatedWith(annotationClazz).in(packageScan);
			
			if (CollectionHelper.isNullOrEmpty(foundClazzSet)) {
				return new HashSet<>();
			}
			
			Set<Class<?>> viewClazzSet = new HashSet<>();
			Collections.addAll(viewClazzSet, foundClazzSet.toArray(new Class<?>[]{}));
			
			return viewClazzSet;
			
		} catch (Exception e) {
			ClassHelper.logger.warn(e.getMessage());
		}
		
		return new HashSet<>();
	}
}
