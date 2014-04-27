package br.com.hcs.progressus.enumerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.helper.CollectionHelper;

public enum Template {
	
	STANDARD("template.standard.xhtml", Theme.getDefault());
	
	@Getter
	@Setter
	private String page;
	@Setter
	private List<Theme> themeList;
	
	
	private Template(String page, Theme...themeArray) {
		try {
			this.setPage(page);
			if (CollectionHelper.isNullOrEmpty(themeArray)) {
				return;
			}
			this.setThemeList(Arrays.asList(themeArray));
		} catch (ProgressusException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Theme> getThemeList() {
		if (this.themeList == null) {
			this.setThemeList(new ArrayList<Theme>());
		}
		return this.themeList;
	}
	
	public Theme getDefaultTheme() {
		return this.getThemeList().size() <= 0 ? Theme.getDefault(): this.getThemeList().get(0);
	}
	
	public String getCrudPage() {
		return this.getPage().replaceAll("\\.xhtml", "").concat(".crud.xhtml");
	}
	
	
	@Override
	public String toString() {
		return this.getPage();
	}
	
	
	public static Template getDefault() {
		return Template.STANDARD;
	}
	
	
}
