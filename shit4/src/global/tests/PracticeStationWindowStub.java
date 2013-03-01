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
		// TODO Auto-generated method stub

	}

	@Override
	public void printMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLanguage(Languages language) {
		// TODO Auto-generated method stub

	}

	@Override
	public String translate(Messages message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printErrorMessage(Messages message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printInfoMessage(Messages message) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	public void practiceVote() {
		caller.practiceVote();
	}

}
