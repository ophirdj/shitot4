package votingStation;

import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.IDictionary;
import dictionaries.Languages;
import dictionaries.Messages;

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
	},
	ENGLISH{
		@Override
		public void activate(IVotingStation callerStation, IVotingStationWindow votingWindow) throws ChoosingInterruptedException{
			votingWindow.setLanguage(Languages.English);
		}
		
		@Override
		public String toString() {
			return "English";
		}

		@Override
		public int getRow() {
			return 1;
		}

		@Override
		public String getString(IDictionary dictionary) {
			return "English";
		}
	},
	HEBREW{
		@Override
		public void activate(IVotingStation callerStation, IVotingStationWindow votingWindow) throws ChoosingInterruptedException{
			votingWindow.setLanguage(Languages.Hebrew);
		}
		
		@Override
		public String toString() {
			return "עברית";
		}

		@Override
		public int getRow() {
			return 1;
		}

		@Override
		public String getString(IDictionary dictionary) {
			return "עברית";
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
