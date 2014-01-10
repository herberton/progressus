package br.com.hcs.progressus.enumerator;

public enum Template {
	
	STANDARD("standard.css");
	
	private String css;
	
	public String getCss() {
		return css;
	}
	private void setCss(String css) {
		this.css = css;
	}
	
	private Template(String css) {
		this.setCss(css);
	}
	
	public static Template getDefault() {
		return Template.STANDARD;
	}
}
