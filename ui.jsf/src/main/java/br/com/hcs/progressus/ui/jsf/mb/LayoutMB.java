package br.com.hcs.progressus.ui.jsf.mb;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

@Slf4j
@NoArgsConstructor
@ViewScoped
@ManagedBean
public class LayoutMB extends ProgressusMB<LayoutMB> {

	private static final long serialVersionUID = 603458329345725492L;
	
	
	@Override
	public void init() {
		
	}
	
	
	public String getIndexPageLink() {
		return JSFHelper.getURL("index");
	}
	
	public String getToday() {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		} catch (Exception e) {
			LayoutMB.log.error(e.getMessage(), e);
		}
		return "";
	}
}
