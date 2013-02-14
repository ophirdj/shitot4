package practiceStation;

public enum PracticeStationAction {
	
		practice_vote{
			@Override
			public String toString() {
				return "practice vote";
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
			public String toString() {
				return "shut down";
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
	abstract int getRow();
	
	public static int maxRow(){
		int max_row = 0;
		for(PracticeStationAction action : PracticeStationAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}

