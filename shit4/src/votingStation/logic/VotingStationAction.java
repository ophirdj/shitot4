package votingStation.logic;

import global.dictionaries.IDictionary;
import global.dictionaries.Messages;
import votingStation.gui.IVotingStationWindow;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

public enum VotingStationAction {
	VOTING{
		@Override
		public void activate(IVotingStation callerStation, IVotingStationWindow votingWindow) throws ChoosingInterruptedException{
			callerStation.voting();
		}
		
		@Override
		public String toString() {
			return "make vote";
		}

		@Override
		public int getRow() {
			return 0;
		}
		
		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.make_vote);
		}
	},
	TEST_VOTE{
		@Override
		public void activate(IVotingStation callerStation, IVotingStationWindow votingWindow) throws ChoosingInterruptedException{
			callerStation.testVoting();
		}
		
		@Override
		public String toString() {
			return "test vote";
		}

		@Override
		public int getRow() {
			return 0;
		}

		@Override
		public String getString(IDictionary dictionary) {
			return dictionary.translate(Messages.test_vote);
		}
	}
	;
	
	public abstract void activate(IVotingStation callerStation, IVotingStationWindow votingWindow) throws ChoosingInterruptedException;
	public abstract int getRow();
	public abstract String getString(IDictionary dictionary);
	
	public static int maxRow(){
		int max_row = 0;
		for(VotingStationAction action : VotingStationAction.values()){
			if(action.getRow() > max_row) max_row = action.getRow(); 
		}
		return max_row+1;
	}
}
