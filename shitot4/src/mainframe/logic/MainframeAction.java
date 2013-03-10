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
			window.setState(MainframeState.VotesCounted);
		}
		
		@Override
		protected int getRowInKind(){
			return 0;
		}
		
		@Override
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return true;
			case VotesCounted: return true;
			default:
				return false;
			}
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.count_votes);
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
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return true;
			case VotesCounted: return true;
			default:
				return false;
			}
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.identification);
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
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return true;
			case VotesCounted: return true;
			default:
				return false;
			}
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.Check_parties_consistency);
		}	
	},
	checkInit{

		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window) {
			boolean flag = callerStation.checkInit();
			if(flag)
				window.printInfoMessage(Messages.initialization_ended_successfully);
			else
				window.printErrorMessage(Messages.initialization_falied);
		}

		@Override
		protected int getRowInKind() {
			return 1;
		}

		@Override
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return true;
			case VotesCounted: return true;
			default:
				return false;
			}
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.Check_initialization);
		}	
	},
	histogram{

		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window) {
			window.displayHistogram();
		}

		@Override
		protected int getRowInKind() {
			return 1;
		}

		@Override
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return false;
			case VotesCounted: return true;
			default:
				return false;
			}
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.histogram);
		}
		
	},
	Table{

		@Override
		public void activate(IMainframe callerStation, IMainframeWindow window) {
			window.displayTable();
		}

		@Override
		protected int getRowInKind() {
			return 1;
		}

		@Override
		public boolean needToShow(MainframeState state) {
			switch(state){
			case BeforeInit : return false;
			case AfterInit: return false;
			case VotesCounted: return true;
			default:
				return false;
			}
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.table);
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
		public boolean needToShow(MainframeState state) {
			return state == MainframeState.BeforeInit;
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.boot);
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
		public boolean needToShow(MainframeState state) {
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
		public boolean needToShow(MainframeState state) {
			return state.equals(MainframeState.BeforeInit);
		}
		
		@Override
		public String getString(IDictionary dictionary){
			return dictionary.translate(Messages.boot_from_backup);
		}
	}
	;
	
	/**
	 * Perform the action
	 * @param callerStation mainframe
	 * @param window mainframe panel
	 */
	public abstract void activate(IMainframe callerStation, IMainframeWindow window);
	
	/**
	 * @return the row of the button representing the button in the window.
	 */
	protected abstract int getRowInKind();
	
	/**
	 * Return whether or not the action can be performed in the given state.
	 * 
	 * @param state the state of the mainframe.
	 * @return true if the action can be performed in this state.
	 */
	public abstract boolean needToShow(MainframeState state);
	
	/**
	 * Get a string corresponding to the action name using <dictionary> for translation
	 * @param dictionary translator
	 * @return action name
	 */
	public abstract String getString(IDictionary dictionary);
	
	/**
	 * There are 3 configurations of mainframe panel:
	 * Before the initialization.
	 * Between initialization and vote counting.
	 * After vote counting.
	 * This enum represents when something should be shown in mainframe panel.
	 * @author Ziv Ronen
	 *
	 */
	public enum MainframeState{
		/**
		 * Before initialization state. 
		 */
		BeforeInit,
		/**
		 * Between initialization and vote counting.
		 */
		AfterInit,
		/**
		 * After vote counting.
		 */
		VotesCounted,
	}
	
	/**
	 * Return the row of the button representing the given action.
	 * As an convention, action that exist before initialization are showed below all other
	 */
	public int getRow(){
		if(needToShow(MainframeState.BeforeInit))
			return getRowInKind()+afterInitRow();
		return getRowInKind();
	}
	
	/**
	 * @return the amount of rows needed to show all the actions.
	 */
	public static int maxRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRow() > max_row)
				max_row = action.getRow(); 
		}
		return max_row+1;
	}
	
	/**
	 * @return the amount of rows needed to represent all actions that don't exist
	 * before initialization.
	 */
	private static int afterInitRow(){
		int max_row = 0;
		for(MainframeAction action : MainframeAction.values()){
			if(action.getRowInKind() > max_row && action.needToShow(MainframeState.VotesCounted))
				max_row = action.getRowInKind(); 
		}
		return max_row+1;
	}
}
