package mainframe;
/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.SliderUI;

import factories.IBackupFactory;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import factories.IMainframeWindowFactory;
import factories.IPartiesListFactory;
import factories.IPartyFactory;
import factories.IReadSuppliedXMLFactory;
import factories.IVoterDataFactory;
import factories.IVotersListFactory;
import factories.IVotingStationFactory;
import factories.IVotingStationWindowFactory;
import factories.VoterDataFactory;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.PartiesList;
import partiesList.Party;
import votersList.IVoterData;
import votersList.IVoterData.Unidentified;
import votersList.IVotersList;
import votersList.VoterData;
import votersList.VotersList;
import votersList.IVoterData.AlreadyIdentified;
import votersList.IVotersList.VoterDoesntExist;
import votingStation.IVotingStation;

import backupToXML.Backup;
import backupToXML.IBackup;
import backupToXML.IReadSuppliedXML;
import backupToXML.ReadXMLFile;



public class Mainframe implements IMainframe, Runnable {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private List<IVotingStation> votingStations;
	private IBackup backup;
	
	private boolean continueRun;
	
	private static final int MILISECONDS_BETWEEN_BACKUPS = 180*1000;
	private static final int NUM_OF_STATIONS = 2;
	
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
	private IReadSuppliedXMLFactory readSuppliedXMLFactory;
	
	public Mainframe(IBackupFactory backupFactory, IPartiesListFactory partiesListFactory, IPartyFactory partyFactory,
			IVotersListFactory votersListFactory, IVoterDataFactory voterDataFactory, IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory, IVotingStationFactory votingStationFactory,
			IVotingStationWindowFactory votingStationWindowFactory, IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory) {
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
		this.readSuppliedXMLFactory=readSuppliedXMLFactory;
	}

	@Override
	public void initialize() {
		IReadSuppliedXML init = readSuppliedXMLFactory.createInstance(voterDataFactory, votersListFactory, partyFactory, partiesListFactory);
		voters = init.readVotersList();
		parties = init.readPartiesList();
		unregisteredVoters = votersListFactory.createInstance();
		initStations();
		backup = backupFactory.createInstance(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		continueRun = true;
	}
	
	private void initStations(){
		votingStations = new ArrayList<IVotingStation>();
		for (int i = 0; i < NUM_OF_STATIONS; i++) {
			IVotingStation station = votingStationFactory.createInstance(null, "voting station" + i,
					choosingListFactory, choosingWindowFactory, votingStationWindowFactory);
			votingStations.add(station);
		}
	}
	

	@Override
	public void restore() {
		backup = backupFactory.createInstance(partiesListFactory, partyFactory, votersListFactory, voterDataFactory);
		voters = backup.restoreVoters();
		parties = backup.restoreParties();
		unregisteredVoters = votersListFactory.createInstance();
		initStations();
	}

	@Override
	public void countVotes() {
		//We want all the votes from all the stations.
		hotBackup();
		//We don't want to be interrupted so we'll work on a local copy.
		IPartiesList parties;
		synchronized (this) {
			parties = this.parties.copy();
		}

		// TODO: Now we  should send 'parties' to the mainframe's window for display.
	}

	@Override
	public void shutDown() {
		continueRun = false;
		hotBackup();
		backupState();
		for(IVotingStation s: votingStations){
			s.retire();
		}
		//TODO: Retire mainframe window.
	}
	
	
	private void backupState(){
		IVotersList voters;
		IPartiesList parties;
		synchronized (this) {
			voters = this.voters.copy();
			parties = this.parties.copy();
		}
		backup.storeState(parties, voters);
	}
	
	
	/**
	 * This method should perform the hot backup routine.
	 * I.e. it should go over all voting stations and receive their parties lists.
	 * Then it should create a parties list that is the merge of all these lists
	 * and save that to the local parties list (not just change the reference)
	 * by using the joinLists method of IPartyList.
	 */
	private void hotBackup(){
		/*
		 * TODO: Please implement this carefully. The actual replacement of 'voters'
		 * and 'parties' should be inside a synchronized block. Do the following:
		 * First, perform the hot backup to some local lists. This can be done without
		 * synchronization on the mainframe's side. Second, inside a synchronized block,
		 * update the mainframe's lists.
		 */
		
	}
	

	@Override
	public synchronized void identification(int id) throws IdentificationError{
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
	
	
	private synchronized IVoterData getVoter(int id) throws VoterDoesNotExist{
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


	@Override
	public synchronized void markVoted(int id) throws VoterDoesNotExist {
		IVoterData voter = getVoter(id);
		try {
			voter.markVoted();
		} catch (Unidentified e) {
			throw new VoterDoesNotExist();
		}
	}


	@Override
	public synchronized VoterStatus getVoterStatus(int id){
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
		while(continueRun){
			try {Thread.sleep(MILISECONDS_BETWEEN_BACKUPS);} catch (InterruptedException e) {}
			hotBackup();
			backupState();
		}
	}

}
