package global.tests;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.logic.IPracticeStation;
import practiceStation.logic.PracticeStationAction;

public class PracticeStationWindowStub extends StationPanel implements IPracticeStationWindow {

	private static final long serialVersionUID = 1L;
	private IPracticeStation caller;
	private boolean defaultConfirmation = true;
	private Queue<Boolean> confirmations = new LinkedBlockingQueue<Boolean>();

	public PracticeStationWindowStub(IPracticeStation caller) {
		this.caller = caller;
	}

	@Override
	public Boolean getConfirmation(StationPanel station, String confirmationMessage) {
		if(confirmations.isEmpty()) return defaultConfirmation;
		return confirmations.poll();
	}

	@Override
	public void printError(StationPanel station, String errorMessage) {

	}

	@Override
	public void printMessage(StationPanel station, String message) {

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
	public boolean printConfirmationMessage(Messages message) {
		if(confirmations.isEmpty()) return defaultConfirmation;
		return confirmations.poll();
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		if(confirmations.isEmpty()) return defaultConfirmation;
		return confirmations.poll();
	}

	@Override
	public void closeWindow() {

	}

	public void practiceVote() {
		caller.practiceVote();
	}

	@Override
	public void setAction(PracticeStationAction action) {
		
	}

}
