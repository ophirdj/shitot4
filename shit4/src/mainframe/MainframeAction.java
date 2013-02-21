package mainframe;

import mainframe.IMainframe.IdentificationError;

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
			}catch(NumberFormatException e){
				window.printError("id should be a number");
			}catch (IdentificationError e) {
				window.printError("voter already registered");
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
	},
	hebrew{
		@Override
		public String toString() {
			return "עברית";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
		}
		
		@Override
		protected int getRowInKind(){
			return 1;
		}
		
		@Override
		public boolean isBeforeInit() {
			return true;
		}
	},
	english{
		@Override
		public String toString() {
			return "english";
		}
		
		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window){
		}
		
		@Override
		protected int getRowInKind(){
			return 1;
		}
		
		@Override
		public boolean isBeforeInit() {
			return true;
		}
	}
	;

	public abstract void activate(IMainframe callerStation, IMainframeWindow window);
	protected abstract int getRowInKind();
	public abstract boolean isBeforeInit();
	
	public int getRow(boolean afterInit){
		if(isBeforeInit() && afterInit)
			return getRowInKind()+afterInitRow();
		else if(isBeforeInit() || afterInit){
			return getRowInKind();
		}
		return -1;
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
			if(action.getRowInKind() > max_row && !action.isBeforeInit()) max_row = action.getRowInKind(); 
		}
		return max_row+2;
	}
}
