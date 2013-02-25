package practiceStation.logic;

import global.dictionaries.IDictionary;
import global.dictionaries.Messages;
import practiceStation.gui.IPracticeStationWindow;

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
		}
		
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

