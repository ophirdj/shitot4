package global.tests;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;

public class PracticeStationWindowStub extends StationPanel implements IPracticeStationWindow {

	private static final long serialVersionUID = 1L;
	private IPracticeStation caller;
	private boolean defaultConformation = true;
	private Queue<Boolean> conformations = new LinkedBlockingQueue<Boolean>();

	public PracticeStationWindowStub(IPracticeStation caller) {
		this.caller = caller;
	}

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		if(conformations.isEmpty()) return defaultConformation;
		return conformations.poll();
	}

	@Override
	public void printError(String errorMessage) {

	}

	@Override
	public void printMessage(String message) {

	}

	@Override
	public void setLanguage(Languages language) {

	}

	@Override
	public String translate(Messages message) {
		return null;
	}

	@Override
	public void printErrorMessage(Messages message) {

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {

	}

	@Override
	public void printInfoMessage(Messages message) {

	}

	@Override
	public boolean printConformationMessage(Messages message) {
		if(conformations.isEmpty()) return defaultConformation;
		return conformations.poll();
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		if(conformations.isEmpty()) return defaultConformation;
		return conformations.poll();
	}

	@Override
	public void closeWindow() {

	}

	public void practiceVote() {
		caller.practiceVote();
	}

}
