package mainframe;

import mainframe.IMainframe.IdentificationError;

public enum MainframeAction {
	countVotes{
		@Override
		public String toString() {
			return "count votes";
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.countVotes();
		}
		
		@Override
		int getRow(){
			return 0;
		}
	}, 
	identification{
		@Override
		public String toString() {
			return "identification";
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window){
			try{
				callerStation.identification(window.getID());
			}catch(NumberFormatException e){
				window.printError("id should be a number");
			}catch (IdentificationError e) {
				window.printError("voter already registered");
			}
		}
		
		@Override
		int getRow(){
			return 0;
		}
	},
	initialize{
		@Override
		public String toString() {
			return "boot";
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.initialize();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	},
	restore{
		@Override
		public String toString() {
			return "boot from backup";
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.restore();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	},
	shutDown{
		@Override
		public String toString() {
			return "shut down";
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.shutDown();
			window.closeWindow();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	}
	;

	abstract void activate(IMainframe callerStation, IMainframeWindow window);
	abstract int getRow();
	
	public static int maxRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}
