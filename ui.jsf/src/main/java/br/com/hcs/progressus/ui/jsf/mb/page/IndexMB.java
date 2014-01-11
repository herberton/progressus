package br.com.hcs.progressus.ui.jsf.mb.page;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hcs.progressus.ui.jsf.mb.common.ProgressusMB;

@ViewScoped
@ManagedBean
public class IndexMB extends ProgressusMB<IndexMB> {

	private static final long serialVersionUID = -95650203654029480L;

	public IndexMB() {
		super(IndexMB.class);
	}

	@Override
	public void init() { }
	
	
}
