package mainframe.logic;

import global.dictionaries.IDictionary;
import global.dictionaries.Messages;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe.IdentificationError;

public enum MainframeAction {
	countVotes{
		@Override
		public String toString() {
			return "count votes";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.countVotes();
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean isBeforeInit() {
			return false;
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.count_votes);
		}
		
		@Override
		public boolean isAfterInit() {
			return true;
		}
	}, 
	identification{
		@Override
		public String toString() {
			return "identification";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
			try{
				callerStation.identification(window.getID());
			}catch(IMainframeWindow.IllegalIdException e){
				window.printErrorMessage(Messages.ID_must_be_a_number);
			}catch (IdentificationError e) {
				window.printErrorMessage(Messages.ID_is_already_registered);
			}
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean isBeforeInit() {
			return false;
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.identification);
		}
		
		@Override
		public boolean isAfterInit() {
			return true;
		}
	},
	initialize{
		@Override
		public String toString() {
			return "boot";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.initialize();
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean isBeforeInit() {
			return true;
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.boot);
		}
		
		@Override
		public boolean isAfterInit() {
			return false;
		}
	},
shutDown{
		@Override
		public String toString() {
			return "shut down";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.shutDown();
			window.closeWindow();
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean isBeforeInit() {
			return true;
		}
		
		@Override
		public boolean isAfterInit() {
			return true;
		}

		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.shut_down);
		}
	},
	restore{
		@Override
		public String toString() {
			return "boot from backup";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
			callerStation.restore();
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean isBeforeInit() {
			return true;
		}
		
		@Override
		public boolean isAfterInit() {
			return false;
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.boot_from_backup);
		}
	}
	;
	
	public abstract void activate(IMainframe callerStation, IMainframeWindow window);
	protected abstract int getRowInKind();
	public abstract boolean isBeforeInit();
	public abstract boolean isAfterInit();
	public abstract String getString(IDictionary dictionary);
	
	public enum existsIn{
		BeforeInit,
		AfterInit,
		Always
	}
	
	public int getRow(boolean afterInit){
		if(isBeforeInit())
			return getRowInKind()+afterInitRow();
		return getRowInKind();
	}
	
	public static int maxRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRow(true) > max_row) max_row = action.getRow(true); 
		}
		return max_row+1;
	}
	
	private static int afterInitRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRowInKind() > max_row && action.isAfterInit()) max_row = action.getRowInKind(); 
		}
		return max_row+2;
	}
}
