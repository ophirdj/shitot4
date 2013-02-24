package mainframe.logic;

/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */

import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IBackup;
import fileHandler.logic.IReadSuppliedXML;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.IVoterData.AlreadyIdentified;
import votersList.model.IVoterData.Unidentified;
import votersList.model.IVotersList.VoterDoesntExist;
import votingStation.logic.IVotingStation;

public class Mainframe implements IMainframe, Runnable {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private IStationsController votingStations;
	private IBackup backup;
	private IMainframeWindow window;

	private boolean continueRun;

	private static final int MILISECONDS_BETWEEN_BACKUPS = 180 * 1000;

	// factories
	private IBackupFactory backupFactory;
	private IVoterDataFactory voterDataFactory;
	private IVotersListFactory votersListFactory;
	private IReadSuppliedXMLFactory readSuppliedXMLFactory;
	private IStationsControllerFactory stationsControllerFactory;

	public Mainframe(IBackupFactory backupFactory,
			IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory,
			IStationsControllerFactory stationsControllerFactory,
			IVoterDataFactory voterDataFactory,
			IVotersListFactory votersListFactory) {
		this.backupFactory = backupFactory;
		this.readSuppliedXMLFactory = readSuppliedXMLFactory;
		this.stationsControllerFactory = stationsControllerFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;
		
		window = mainframeWindowFactory.createInstance(this);
	}

	@Override
	public void initialize() {
		IReadSuppliedXML initRead = readSuppliedXMLFactory.createInstance();
		backup = backupFactory.createInstance();
		init(initRead.readVotersList(), votersListFactory.createInstance(), initRead.readPartiesList());
	}

	@Override
	public void restore() {
		backup = backupFactory.createInstance();
		init(backup.restoreVoters(), backup.restoreUnregisteredVoters(), backup.restoreParties());
	}
	
	
	private void init(IVotersList voters, IVotersList unregistered, IPartiesList parties){
		this.voters = voters;
		this.parties = parties;
		this.unregisteredVoters = unregistered;
		votingStations = stationsControllerFactory.createInstance(this);
		votingStations.initialize(parties);
		continueRun = true;
		window.init();
	}

	@Override
	public void countVotes() {
		// We want all the votes from all the stations.
		hotBackup();
		// We don't want to be interrupted so we'll work on a local copy.
		IPartiesList parties = this.parties.copy();
		// Now we should send 'parties' to the mainframe's window for display.
		window.showHistogram(parties);
		window.showTable(parties);
	}

	@Override
	public void shutDown() {
		continueRun = false;
		if(backup != null) backupState();
		if(votingStations != null) votingStations.retire();
	}

	// Save voters and parties lists to backup file. Lists must match.
	private void backupState() {
		IVotersList voters;
		IPartiesList parties;
		do {
			hotBackup();
			synchronized (this) {
				voters = this.voters.copy();
				parties = this.parties.copy();
			}
		} while (!matchingLists(voters ,parties));
		backup.storeState(parties, voters, unregisteredVoters);
	}

	// Synchronize mainframe's parties list with the ones in the voting
	// stations.
	private void hotBackup() {
		IPartiesList stationsParties = votingStations.hotBackup();
		synchronized (this) {
			parties = stationsParties;
		}
	}

	// Check that sum of votes in voters list matches sum of votes in parties
	// list
	private boolean matchingLists(IVotersList voters, IPartiesList parties) {
		int sumVotesVoters = 0, sumVotesParties = 0;
		for (IVoterData v : voters) {
			if (v.hasVoted()) {
				sumVotesVoters++;
			}
		}
		for (IParty p : parties) {
			sumVotesParties += p.getVoteNumber();
		}
		return sumVotesVoters == sumVotesParties;
	}

	@Override
	public synchronized void identification(int id) throws IdentificationError {
		if (voters.inList(id)) {
			try {
				voters.findVoter(id).markIdentified();
			} catch (AlreadyIdentified e) {
				throw new IdentificationError();
			} catch (VoterDoesntExist e) {
				// won't happen
				e.printStackTrace();
			}
		} else {
			IVoterData voter = voterDataFactory.createInstance(id);
			try {
				voter.markIdentified();
			} catch (AlreadyIdentified e) {
				// won't happen
				e.printStackTrace();
				return;
			}
			unregisteredVoters.addVoter(voter);
			voters.addVoter(voter);
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
		for (IVotingStation station : votingStations) {
			station.peep();
		}
	}

	private synchronized IVoterData getVoter(int id) throws VoterDoesNotExist {
		if (!voters.inList(id)) {
			throw new VoterDoesNotExist();
		}
		try {
			return voters.findVoter(id);
		} catch (VoterDoesntExist e) {
			// won't happen
			e.printStackTrace();
			throw new VoterDoesNotExist();
		}
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
	public synchronized void markStartedVote(int id) throws VoterDoesNotExist, VoterStartedVote {
		IVoterData voter = getVoter(id);
		try {
			if(voter.hasStartedVote()) throw new VoterStartedVote();
			voter.markStartedVote();
		} catch (Unidentified e) {
			throw new VoterDoesNotExist();
		}
	}

	@Override
	public synchronized VoterStatus getVoterStatus(int id) {
		IVoterData voter;
		try {
			voter = getVoter(id);
		} catch (VoterDoesNotExist e) {
			return VoterStatus.unidentified;
		}
		if (voter.hasVoted())
			return VoterStatus.voted;
		if(voter.hasStartedVote()){
			return VoterStatus.startedVote;
		}
		if (voter.isIdentified()){
			return VoterStatus.identified;
		}
		return VoterStatus.unidentified;
	}

	@Override
	public void run() {
		while (continueRun) {
			try {
				Thread.sleep(MILISECONDS_BETWEEN_BACKUPS);
			} catch (InterruptedException e) {
			}
			backupState();
		}
	}

}
