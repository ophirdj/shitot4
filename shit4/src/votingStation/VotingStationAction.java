package votingStation;

import choosingList.IChoosingList.ChoosingInterruptedException;

public enum VotingStationAction {
	VOTING{
		@Override
		void activate(IVotingStation callerStation) throws ChoosingInterruptedException{
			callerStation.voting();
		}
		
		@Override
		public String toString() {
			return "make vote";
		}
	},
	TEST_VOTE{
		@Override
		void activate(IVotingStation callerStation) throws ChoosingInterruptedException{
			callerStation.testVoting();
		}
		
		@Override
		public String toString() {
			return "test vote";
		}
	};
	
	abstract void activate(IVotingStation callerStation) throws ChoosingInterruptedException;
}
