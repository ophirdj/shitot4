package global.dictionaries;

import global.gui.StationPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public enum Languages {
	English{
		@Override
		public String toString() {
			return "English";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Translate2English();
		}
	},
	
	Hebrew{
		@Override
		public String toString() {
			return "עברית";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Translate2Hebrew();
		}
	}
	;
	
	public abstract IDictionary getDictionary();
	
	public static class LanguageClick implements ActionListener{

		private StationPanel callerStation;
		private Object lock;
		private Languages language;

		public LanguageClick(StationPanel callerStation,Object lock, Languages language) {
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
	
	public static JPanel getLanguagesPanel(StationPanel callerStation,Object lock){
		JPanel languagesPanel = new JPanel(new FlowLayout());
		for (Languages language : Languages.values()) {
			JButton languageButton = new JButton(language.toString());
			languageButton.addActionListener(new LanguageClick(callerStation,lock,language));
			languagesPanel.add(languageButton);
		}
		return languagesPanel;
	}
}
