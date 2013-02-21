package global.gui;

import global.dictionaries.IDictionary;
import global.dictionaries.Languages;
import global.dictionaries.Messages;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import partiesList.model.IParty;


public abstract class BasicPanel extends JPanel implements IWindow {

	private static final long serialVersionUID = 1L;

	protected Main_Window window;
	protected IDictionary dictionary;

	public BasicPanel(Main_Window window) {
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		this.window = window;
		dictionary = window.DEFUALT_LANGUAGE.getDictionary();
	}

	public Boolean getConfirmation(String confirmationMessage) {
		Object[] options = new Object[2];
		options[0]=translate(Messages.Yes);
		options[1]=translate(Messages.No);
		int n = JOptionPane.showOptionDialog(window, confirmationMessage,
				translate(Messages.Ask_for_conformation),
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		if (n == JOptionPane.YES_OPTION)
			return true;
		else
			return false;
	}

	public void printError(String errorMessage) {
		Object[] options = new Object[1];
		options[0]=translate(Messages.Ok);
		JOptionPane.showOptionDialog(window, errorMessage,
				translate(Messages.ERROR),
				JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}

	public void printMessage(String message) {
		Object[] options = new Object[1];
		options[0]=translate(Messages.Ok);
		JOptionPane.showOptionDialog(window, message,
				translate(Messages.FYI),
				JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				options, options[0]);
	}

	@Override
	public void closeWindow() {
		window.remove_panel(this);
	}

	@Override
	public void setLanguage(Languages language) {
		dictionary = language.getDictionary();
	}

	@Override
	public String translate(Messages message) {
		return dictionary.translate(message);
	}

	@Override
	public void printErrorMessage(Messages message) {
		printError(translate(message));
	}

	@Override
	public void printInfoMessage(Messages message) {
		printMessage(translate(message));
	}

	@Override
	public boolean printConformationMessage(Messages message) {
		return getConfirmation(translate(message));
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		return getConfirmation(translate(message) + party.getName() + "?");
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		printMessage(translate(message) + party.getName());
	}
}
