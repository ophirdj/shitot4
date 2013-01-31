import java.util.*;

public class VotingStation implements IVotingStation {
	private IMainframe mainframe;
	private IPartiesList parties;
	private List<VoterData> lastVotersList;
	private List<Integer> oldVotersList;
	private List<String> passwords;
	private IWindow votingStationWindow;

	public VotingStation(List<String> passwords){
		this.passwords = passwords;
	};

	public void initialize(IPartiesList parties,IMainframe mainframe){
		this.mainframe = mainframe;
		this.parties = parties;
		lastVotersList = new ArrayList<VoterData>();
		oldVotersList = new ArrayList<Integer>();
	}

	public IPartiesList getPartiesList(){
		for (VoterData voterData: lastVotersList){
			if (new Date().getTime()-voterData.getDate().getTime()>=120000){
				lastVotersList.remove(voterData);
				oldVotersList.add(voterData.getId());
			}
		}
		return parties;
	}

	public void voting(int id){
		if (!mainframe.getAuthorizedIdList().contains(id)){
			votingStationWindow.printError("Error: You were not authorized to vote");
			return;
		}
		if (oldVotersList.contains(id)){
			votingStationWindow.printError("Error: You already voted");
			return;
		}
		for (VoterData voterData: lastVotersList){
			if (voterData.getId()==id){ 
				if(new Date().getTime()-voterData.getDate().getTime()>=120000 || voterData.getChangeNumbers()>=2){
					votingStationWindow.printError("Error: You cannot change your vote any more");
					return;
				}
				this.changeVote(voterData);
				return;
			}
		}
		IParty lastParty = new ChoosingList(parties,votingStationWindow).chooseList();
		lastVotersList.add(new VoterData(id,lastParty));
		lastParty.increaseVoteNumber();
		votingStationWindow.printError("You successfully test voted for the party" + lastParty.getName());
	}

	private void changeVote(VoterData voterData) {
		Boolean confirmation = votingStationWindow.getConfirmation("Are you sure you want to change your vote?");
		if (!confirmation){return;}
		IParty newParty = new ChoosingList(parties,votingStationWindow).chooseList();
		voterData.getLastChosenParty().decreaseVoteNumber();
		newParty.increaseVoteNumber();
		voterData.setNewParty(newParty);
		votingStationWindow.printError("You successfully changed your vote to the party" + newParty.getName());
	}


	public void testVoting(int id,String password){
		if (!passwords.contains(password)){
			votingStationWindow.printError("Error: wrong password");
		}
		IParty testParty = new ChoosingList(parties,votingStationWindow).chooseList();
		votingStationWindow.printError("You successfully test voted for the party" + testParty.getName());
	}

	@Override
	public IVotersList getVotersList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void peep() {
		// TODO Auto-generated method stub
		
	}

}

