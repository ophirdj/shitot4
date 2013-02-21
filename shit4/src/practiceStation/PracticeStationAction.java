package practiceStation;

import dictionaries.IDictionary;
import dictionaries.Languages;
import dictionaries.Messages;

public enum PracticeStationAction {
	
		practice_vote{
			@Override
			public String toString() {
				return "practice vote";
			}
			
			@Override
			public void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				callerStation.practiceVote();
			}
			
			@Override
			public int getRow(){
				return 0;
			}

			@Override
			public String getString(IDictionary dictionary) {
				return dictionary.translate(Messages.practice_vote);
			}
		}, 
		shut_down{
			@Override
			public String toString() {
				return "shut down";
			}
			
			@Override
			public void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				callerStation.retire();
				window.closeWindow();
			}
			
			@Override
			public int getRow(){
				return 0;
			}

			@Override
			public String getString(IDictionary dictionary) {
				return dictionary.translate(Messages.shut_down);
			}
		
		},
		english{
			@Override
			public String toString() {
				return "shut down";
			}
			
			@Override
			public void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				window.setLanguage(Languages.English);
			}
			
			@Override
			public int getRow(){
				return 1;
			}

			@Override
			public String getString(IDictionary dictionary) {
				return "English";
			}
		},
		hebrew{
			@Override
			public String toString() {
				return "shut down";
			}
			
			@Override
			public void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				window.setLanguage(Languages.Hebrew);
			}
			
			@Override
			public int getRow(){
				return 1;
			}
			
			@Override
			public String getString(IDictionary dictionary){
				return "עברית";
			}
			
		},
		
		;

	public abstract void activate(IPracticeStation callerStation, IPracticeStationWindow window);
	public abstract int getRow();
	public abstract String getString(IDictionary dictionary);
	
	public static int maxRow(){
		int max_row = 0;
		for(PracticeStationAction action : PracticeStationAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}

