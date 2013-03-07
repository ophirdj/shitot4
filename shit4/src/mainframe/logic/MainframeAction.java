package mainframe.logic;

import global.dictionaries.IDictionary;
import global.dictionaries.Messages;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe.IdentificationError;

/**
 * All actions possible in mainframe panel
 * @author Ophir De Jager
 *
 */
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
	checkParties{

		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window) {
			boolean flag = callerStation.checkParties();
			if(flag)
				window.printInfoMessage(Messages.parties_match);
			else
				window.printErrorMessage(Messages.parties_dont_match);
		}

		@Override
		protected int getRowInKind() {
			return 0;
		}

		@Override
		public boolean isBeforeInit() {
			return false;
		}

		@Override
		public boolean isAfterInit() {
			return true;
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.Check_parties_consistency);
		}
		
	}
	,
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
	
	/**
	 * Perform the action
	 * @param callerStation: mainframe
	 * @param window: mainframe panel
	 */
	public abstract void activate(IMainframe callerStation, IMainframeWindow window);
	
	/**
	 * 
	 * @return
	 */
	protected abstract int getRowInKind();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isBeforeInit();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isAfterInit();
	
	/**
	 * Get a string corresponding to the action name using <dictionary> for translation
	 * @param dictionary: translator
	 * @return action name
	 */
	public abstract String getString(IDictionary dictionary);
	
	/**
	 * There are 2 configurations of mainframe panel: before and after initialization.
	 * This enum represents when something should be shown in mainframe panel.
	 * @author Ziv Ronen
	 *
	 */
	public enum existsIn{
		/**
		 * Show only prior to initialization
		 */
		BeforeInit,
		/**
		 * Show only after initialization
		 */
		AfterInit,
		/**
		 * Show always
		 */
		Always
	}
	
	//Sort all buttons in rows
	
	// TODO add javadoc
	public int getRow(boolean afterInit){
		if(isBeforeInit())
			return getRowInKind()+afterInitRow();
		return getRowInKind();
	}
	
	// TODO add javadoc
	public static int maxRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRow(true) > max_row) max_row = action.getRow(true); 
		}
		return max_row+1;
	}
	
	// TODO add javadoc
	private static int afterInitRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRowInKind() > max_row && action.isAfterInit()) max_row = action.getRowInKind(); 
		}
		return max_row+2;
	}
}
