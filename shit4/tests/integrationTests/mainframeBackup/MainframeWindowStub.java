package integrationTests.mainframeBackup;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.MainframeAction.MainframeState;

/**
 * the only actual use of this stub is the method 'showHistogram'
 * that with its help we check whether the Mainframe sends the right list to
 * the mentioned method
 * we use the method 
 * @author Emil
 *
 */
public class MainframeWindowStub extends IMainframeWindow {

	private static final long serialVersionUID = 1L;
	
	/**
	 * saves the parameter which the method 'showHistogram' gets as parameter
	 */
	private IPartiesList showHistogramParam;
	
	@Override
	public Boolean getConfirmation(StationPanel station, String confirmationMessage) {
		return null;
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
		return false;
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		return false;
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public int getID() {
		return 0;
	}
	
	/**
	 * returns what 'showHistogram' got as parameter
	 * @return the parameter which the method 'showHistogram' got
	 */
	public IPartiesList getWhatShowHistogramGot(){
		return this.showHistogramParam;
	}

	@Override
	public void setState(MainframeState state) {

	}

	@Override
	public void displayHistogram() {
	}

	@Override
	public void displayTable() {
		
	}

	@Override
	public void setDataDisplay(IPartiesList parties) {
		this.showHistogramParam = parties;
	}

}
