package br.com.hcs.progressus.ui.jsf.pl;

import java.io.Serializable;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


public class AuthenticationPL implements PhaseListener, Serializable {

	
	private static final long serialVersionUID = -5246149878541861532L;

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		
	}

	@Override
	public void beforePhase(PhaseEvent event) { }

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
