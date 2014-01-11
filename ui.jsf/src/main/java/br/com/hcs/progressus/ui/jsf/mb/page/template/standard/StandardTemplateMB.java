package br.com.hcs.progressus.ui.jsf.mb.page.template.standard;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import br.com.hcs.progressus.client.bo.sb.crud.UserBORemote;
import br.com.hcs.progressus.ui.jsf.mb.common.ProgressusMB;

@ViewScoped
@ManagedBean
public class StandardTemplateMB extends ProgressusMB<StandardTemplateMB> {

	private static final long serialVersionUID = -5865218374795064882L;

	
	@Getter
	@EJB
	private UserBORemote testBO;
	
	
	public StandardTemplateMB() {
		super(StandardTemplateMB.class);
	}
	
	@Override
	public void init() { 
		
		System.out.println(this.getTestBO().test("2222"));
		
	}
}
