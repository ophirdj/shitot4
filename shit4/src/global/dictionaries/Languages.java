package global.dictionaries;

import global.dictionaries.IDictionary.SomeDictionaryEntriesAreMissing;
import global.gui.StationPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This enum represents all supported languages.
 * If you want to add a language just add its
 * corresponding enum type here and implement the required methods and
 * add the dictionary file in the dictionaries directory. You should
 * also add a file with the language name (in that language) so its name
 * could be displayed in the language button.
 * Note: For efficiency, all the dictionaries will be loaded
 * only once - on startup. 
 * (Then Ziv will do his magic with the language buttons
 * and all will be OK)
 * @author Ophir De Jager
 *
 */
public enum Languages {
	English {
		@Override
		public String toString() {
			return EnglishSymbol;
		}

		@Override
		public IDictionary getDictionary() {
			return EnglishDictionary;
		}
	},

	Hebrew {
		@Override
		public String toString() {
			return HebrewSymbol;
		}

		@Override
		public IDictionary getDictionary() {
			return HebrewDictionary;
		}
	};

	// dictionaries
	private static final IDictionary EnglishDictionary = readDictionary("English.dict");
	private static final IDictionary HebrewDictionary = readDictionary("Hebrew.dict");

	// language names
	private static final String EnglishSymbol = readLanguageSymbol("English.txt");
	private static final String HebrewSymbol = readLanguageSymbol("Hebrew.txt");

	public static class LanguageNotSupportedException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	
	/**
	 * Method used to read a dictionary from a file
	 * @param filename dictionary file
	 * @return an instance of IDictionary
	 */
	private static IDictionary readDictionary(String filename) {
		try {
			return readDictionaryFromFile(filename);
		} catch (LanguageNotSupportedException e) {
			// shouldn't happen
			// if it does one of the dictionary files is missing some
			// translations
			
			// had to catch this one because java doesn't like exceptions
			// thrown on startup
		}
		return null;
	}

	/**
	 * Method used to read a dictionary from a file
	 * You should use readDictionary(String filename) if you want
	 * to load a dictionary from file.
	 * @param filename dictionary file
	 * @return an instance of IDictionary
	 */
	private static IDictionary readDictionaryFromFile(String filename)
			throws LanguageNotSupportedException {
		Map<Messages, String> dict;
		try {
			dict = ReadDictionary.readDictionary(filename);
		} catch (IOException e) {
			throw new LanguageNotSupportedException();
		}
		if (dict.size() != Messages.values().length) {
			throw new LanguageNotSupportedException();
		}
		try {
			return new Dictionary(dict);
		} catch (SomeDictionaryEntriesAreMissing e) {
			/*
			 * if reached here some of the entries
			 * in the dictionary are missing.
			 */
			throw new LanguageNotSupportedException();
		}
	}

	/**
	 * Read the language symbol (currently its name) from file 
	 * @param filename
	 * @return the language symbol
	 */
	private static String readLanguageSymbol(String filename) {
		final String directory = "languages/symbols/";

		try {
			Scanner in = new Scanner(new File(directory + filename), "UTF8");
			String name = in.nextLine();
			in.close();
			return name;
		} catch (IOException e) {
		}
		return null;
	}

	
	
	/**
	 * Get a dictionary from Messages to language
	 * @return the dictionary of the language
	 */
	public abstract IDictionary getDictionary();

	
	/**
	 * Class to handle clicks on language buttons.
	 * When clicked, a language button should change the panel's language.
	 * @author Ziv Ronen
	 *
	 */
	public static class LanguageClick implements ActionListener {

		private StationPanel callerStation;
		private Object lock;
		private Languages language;

		public LanguageClick(StationPanel callerStation, Object lock,
				Languages language) {
			this.callerStation = callerStation;
			this.lock = lock;
			this.language = language;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			callerStation.setLanguage(language);
			synchronized (lock) {
				lock.notify();
			}

		}

	}

	/**
	 * Create a panel that contains all the language buttons
	 * @param callerStation StationPanel that will own the language panel
	 * @param lock lock to sync operation (we don't want any wierd glitches)
	 * @return the language panel
	 */
	public static JPanel getLanguagesPanel(StationPanel callerStation,
			Object lock) {
		JPanel languagesPanel = new JPanel(new FlowLayout());
		for (Languages language : Languages.values()) {
			JButton languageButton = new JButton(language.toString());
			languageButton.addActionListener(new LanguageClick(callerStation,
					lock, language));
			languagesPanel.add(languageButton);
		}
		return languagesPanel;
	}
}
