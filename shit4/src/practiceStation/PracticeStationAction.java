package practiceStation;

import dictionaries.IDictionary.Messages;
import GUI.Main_Window;

public enum PracticeStationAction {
	
		practice_vote{
			@Override
			public String getString(Main_Window mainWindow) {
				return mainWindow.translate(Messages.practice_vote);
			}
			
			@Override
			void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				callerStation.practiceVote();
			}
			
			@Override
			int getRow(){
				return 0;
			}
		}, 
		shut_down{
			@Override
			public String getString(Main_Window mainWindow) {
				return mainWindow.translate(Messages.shut_down);
			}
			
			@Override
			void activate(IPracticeStation callerStation, IPracticeStationWindow window){
				callerStation.retire();
				window.closeWindow();
			}
			
			@Override
			int getRow(){
				return 0;
			}
		}, 
		
		;

	abstract void activate(IPracticeStation callerStation, IPracticeStationWindow window);
	
	abstract String getString(Main_Window mainWindow);
	
	abstract int getRow();
	
	public static int maxRow(){
		int max_row = 0;
		for(PracticeStationAction action : PracticeStationAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}

