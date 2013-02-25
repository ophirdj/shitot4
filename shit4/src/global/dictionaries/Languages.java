package global.dictionaries;

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

	private static IDictionary readDictionary(String filename) {
		try {
			return readDictionaryFromFile(filename);
		} catch (LanguageNotSupportedException e) {
			// shouldn't happen
			// if it does one of the dictionary files is missing some
			// translations
		}
		return null;
	}

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
		return new Dictionary(dict);
	}

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

	public abstract IDictionary getDictionary();

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
