package votingStation;

import GUI.Main_Window;
import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.IDictionary.Messages;

public enum VotingStationAction {
	VOTING{
		@Override
		void activate(IVotingStation callerStation) throws ChoosingInterruptedException{
			callerStation.voting();
		}
		
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.make_vote);
		}
	},
	TEST_VOTE{
		@Override
		void activate(IVotingStation callerStation) throws ChoosingInterruptedException{
			callerStation.testVoting();
		}
		
		@Override
		public String getString(Main_Window mainWindow) {
			return mainWindow.translate(Messages.test_vote);
		}
	};
	
	abstract void activate(IVotingStation callerStation) throws ChoosingInterruptedException;

	abstract String getString(Main_Window mainWindow);
}
