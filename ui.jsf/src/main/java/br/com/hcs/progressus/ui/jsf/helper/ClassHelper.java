package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.atatec.trugger.scan.ClassScan;
import org.atatec.trugger.scan.PackageScan;
import org.atatec.trugger.scan.ScanLevel;

import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.CollectionHelper;

@Slf4j
public class ClassHelper implements Serializable {
	
	private static final long serialVersionUID = 386149031341420468L;
	
	
	public static Set<Class<?>> getViewClazzSet() {
		try {
			return ClassHelper.getViewClazzSet(ScanLevel.SUBPACKAGES);
		} catch (Exception e) {
			ClassHelper.log.error(e.getMessage());
		}
		return new HashSet<>();
	}
	
	public static Set<Class<?>> getViewClazzSet(ScanLevel scanLevel) {
		try {
			return ClassHelper.getViewClazzSet(Setting.WEB_MANAGED_BEAN_PACKAGE.toString(), scanLevel);
		} catch (Exception e) {
			ClassHelper.log.error(e.getMessage());
		}
		return new HashSet<>();
	}
	
	public static Set<Class<?>> getViewClazzSet(String classPackage, ScanLevel scanLevel) {
		try {
			return ClassHelper.findSet(View.class, classPackage, scanLevel);
		} catch (Exception e) {
			ClassHelper.log.error(e.getMessage());
		}
		return new HashSet<>();
	}
	
	
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
			ClassHelper.log.error(e.getMessage());
		}
		
		return new HashSet<>();
	}
}
