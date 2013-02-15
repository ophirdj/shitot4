package mainframe;

import dictionaries.IDictionary.Messages;
import GUI.Main_Window;
import mainframe.IMainframe.IdentificationError;

public enum MainframeAction {
	countVotes{
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.count_votes);
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow){
			callerStation.countVotes();
		}
		
		@Override
		int getRow(){
			return 0;
		}
	}, 
	identification{
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.identification);
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow){
			try{
				callerStation.identification(window.getID());
			}catch(NumberFormatException e){
				window.printError(mainWindow.translate(Messages.ID_must_be_a_number));
			}catch (IdentificationError e) {
				window.printError(mainWindow.translate(Messages.ID_is_already_registered));
			}
		}
		
		@Override
		int getRow(){
			return 0;
		}
	},
	initialize{
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.boot);
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow){
			callerStation.initialize();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	},
	restore{
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.boot_from_backup);
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow){
			callerStation.restore();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	},
	shutDown{
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.shut_down);
		}
		
		@Override
		void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow){
			callerStation.shutDown();
			window.closeWindow();
		}
		
		@Override
		int getRow(){
			return 1;
		}
	}
	;

	abstract void activate(IMainframe callerStation, IMainframeWindow window, Main_Window mainWindow);
	
	abstract String getString(Main_Window mainWindow);
	
	abstract int getRow();
	
	public static int maxRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}
