package mainframe.tests;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import mainframe.gui.IMainframeWindow;

/**
 * the only actual use of this stub is the method 'showHistogram'
 * that with its help we check whether the Mainframe sends the right list to
 * the mentioned method
 * we use the method 
 * @author Emil
 *
 */
public class MainframeWindowStub implements IMainframeWindow {

	/**
	 * saves the parameter which the method 'showHistogram' gets as parameter
	 */
	private IPartiesList showHistogramParam;
	
	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		return null;
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
		return false;
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		return false;
	}

	@Override
	public void closeWindow() {

	}

	@Override
	public void run() {

	}

	@Override
	public void showHistogram(IPartiesList parties) {
		this.showHistogramParam = parties;
	}

	@Override
	public void showTable(IPartiesList parties) {

	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init() {

	}
	
	/**
	 * returns what 'showHistogram' got as parameter
	 * @return the parameter which the method 'showHistogram' got
	 */
	public IPartiesList getWhatShowHistogramGot(){
		return this.showHistogramParam;
	}

}
