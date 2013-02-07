package mainframe;
/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */


import java.util.ArrayList;
import java.util.List;

import choosingList.IChoosingListFactory;
import choosingList.IChoosingWindowFactory;

import partiesList.IPartiesList;
import partiesList.IPartiesListFactory;
import partiesList.IParty;
import partiesList.IPartyFactory;
import partiesList.PartiesList;
import partiesList.Party;
import votersList.IVoterData;
import votersList.IVoterData.Unidentified;
import votersList.IVoterDataFactory;
import votersList.IVotersList;
import votersList.IVotersListFactory;
import votersList.VoterData;
import votersList.VoterDataFactory;
import votersList.VotersList;
import votersList.IVoterData.AlreadyIdentified;
import votersList.IVotersList.VoterDoesntExist;
import votingStation.IVotingStation;
import votingStation.IVotingStationFactory;
import votingStation.IVotingStationWindowFactory;

import backup.Backup;
import backup.IBackup;
import backup.IBackupFactory;
import backup.ReadXMLFile;



public class Mainframe implements IMainframe, Runnable {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private List<IVotingStation> votingStations;
	private IBackup backup;
	private Thread backupThread;
	
	private final int NUM_OF_STATIONS = 2;
	
	//factories
	private IBackupFactory backupFactory;
	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVoterDataFactory voterDataFactory;
	private IVotersListFactory votersListFactory;
	private IChoosingListFactory choosingListFactory;
	private IChoosingWindowFactory choosingWindowFactory;
	private IVotingStationFactory votingStationFactory;
	private IVotingStationWindowFactory votingStationWindowFactory;
	private IMainframeWindowFactory mainframeWindowFactory;
	
	public Mainframe(IBackupFactory backupFactory, IPartiesListFactory partiesListFactory, IPartyFactory partyFactory,
			IVotersListFactory votersListFactory, IVoterDataFactory voterDataFactory, IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory, IVotingStationFactory votingStationFactory,
			IVotingStationWindowFactory votingStationWindowFactory, IMainframeWindowFactory mainframeWindowFactory) {
		this.backupFactory = backupFactory;
		this.partiesListFactory=partiesListFactory;
		this.partyFactory=partyFactory;
		this.voterDataFactory=voterDataFactory;
		this.votersListFactory=votersListFactory;
		this.choosingListFactory=choosingListFactory;
		this.choosingWindowFactory=choosingWindowFactory;
		this.votingStationFactory=votingStationFactory;
		this.votingStationWindowFactory=votingStationWindowFactory;
		this.mainframeWindowFactory=mainframeWindowFactory;
	}

	@Override
	public void initialize() {
		voters = loadVotersList();
		parties = loadPartiesList();
		unregisteredVoters = votersListFactory.createInstance();
		votingStations = new ArrayList<IVotingStation>();
		for (int i = 0; i < NUM_OF_STATIONS; i++) {
			IVotingStation station = votingStationFactory.createInstance(null, "voting station" + i,
					choosingListFactory, choosingWindowFactory, votingStationWindowFactory);
			votingStations.add(station);
		}
		backup = backupFactory.createInstance(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		backupThread = (new Thread(this));
		backupThread.start();
	}
	
	
	private IVotersList loadVotersList(){
		ArrayList<Integer> votersIdList = ReadXMLFile.readXMLVotersList();
		IVotersList voterList = votersListFactory.createInstance();
		for(Integer id : votersIdList) {
			voterList.addVoter(voterDataFactory.createInstance(id));			
		}
		return voterList;
	}
	
	private IPartiesList loadPartiesList(){
		//TODO: Emil: unerror it!!
		List<IParty> parties = ReadXMLFile.readXMLvotingRecords();
		IPartiesList partieslist = partiesListFactory.createInstance();
		for(IParty party: parties){
			partieslist.addParty(party);
		}
		return partieslist;
	}

	@Override
	public void restore() {
		voters = votersListFactory.createInstance();
		parties = partiesListFactory.createInstance();
		unregisteredVoters = votersListFactory.createInstance();
		votingStations = new ArrayList<IVotingStation>();
		backup = backupFactory.createInstance(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		backupThread = (new Thread(this));
		backupThread.start();
		for(IVoterData voter: backup.restoreVoters()){
			voters.addVoter(voter);
		}
		for(IParty party: backup.restoreParties()){
			parties.addParty(party);
		}
	}

	@Override
	public void countVotes() {
		// TODO
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		backup.retire();
		try {
			backupThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * This method should perform the hot backup routine.
	 * I.e. it should go over all voting stations and receive their parties lists.
	 * Then it should create a parties list that is the merge of all these lists
	 * and save that to the local parties list (not just change the reference)
	 * by using the joinLists method of IPartyList.
	 */
	private void hotBackup(){
		
	}
	

	@Override
	public void identification(int id) throws IdentificationError{
		if(voters.inList(id)){
			try {
				voters.findVoter(id).markIdentified();
			} catch (AlreadyIdentified e) {
				throw new IdentificationError();
			} catch (VoterDoesntExist e) {
				// won't happen
				e.printStackTrace();
			}
		}
		else{
			IVoterData voter = voterDataFactory.createInstance(id);
			try {
				voter.markIdentified();
			} catch (AlreadyIdentified e) {
				//won't happen
				return;
			}
			unregisteredVoters.addVoter(voter);
		}
	}

	@Override
	public void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Mainframe");
		System.out.println("=========================");
		System.out.println("voters:");
		voters.peep();
		System.out.println("parties:");
		parties.peep();
		System.out.println("unregistered voters:");
		unregisteredVoters.peep();
		System.out.println("voting stations:");
		for(IVotingStation station: votingStations){
			station.peep();
		}
	}
	
	
	private IVoterData getVoter(int id) throws VoterDoesNotExist{
		synchronized (voters) {
			boolean inVoters = voters.inList(id), inUnregisteredVoters = unregisteredVoters.inList(id);
			if(!inVoters && !inUnregisteredVoters){
				throw new VoterDoesNotExist();
			}
			IVoterData voter = null;
			if(inVoters){
				try {
					voter = voters.findVoter(id);
				} catch (VoterDoesntExist e) {
					// won't happen
					e.printStackTrace();
				}
			}
			else{
				try {
					voter = unregisteredVoters.findVoter(id);
				} catch (VoterDoesntExist e) {
					// won't happen
					e.printStackTrace();
				}
			}
			return voter;
		}
	}


	@Override
	public void markVoted(int id) throws VoterDoesNotExist {
		synchronized (voters) {
			IVoterData voter = getVoter(id);
			try {
				voter.markVoted();
			} catch (Unidentified e) {
				throw new VoterDoesNotExist();
			}
		}
	}


	@Override
	public VoterStatus getVoterStatus(int id){
		IVoterData voter;
		try {
			voter = getVoter(id);
		} catch (VoterDoesNotExist e) {
			return VoterStatus.unidentified;
		}
		if(voter.hasVoted()) return VoterStatus.voted;
		if(voter.isIdentified()) return VoterStatus.identified;
		return VoterStatus.unidentified;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
