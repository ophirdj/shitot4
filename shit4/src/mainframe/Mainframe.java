package mainframe;

/*
 * creator: Ophir De Jager
 * date: 30.1.13
 * editors:
 */

import communication.IStationsController;
import factories.IBackupFactory;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import factories.IMainframeWindowFactory;
import factories.IPartiesListFactory;
import factories.IPartyFactory;
import factories.IReadSuppliedXMLFactory;
import factories.IStationsControllerFactory;
import factories.IVoterDataFactory;
import factories.IVotersListFactory;
import factories.IVotingStationFactory;
import factories.IVotingStationWindowFactory;
import partiesList.IPartiesList;
import partiesList.IParty;
import votersList.IVoterData;
import votersList.IVoterData.Unidentified;
import votersList.IVotersList;
import votersList.IVoterData.AlreadyIdentified;
import votersList.IVotersList.VoterDoesntExist;
import votingStation.IVotingStation;
import XML.IBackup;
import XML.IReadSuppliedXML;

public class Mainframe implements IMainframe, Runnable {
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private IStationsController votingStations;
	private IBackup backup;

	private boolean continueRun;

	private static final int MILISECONDS_BETWEEN_BACKUPS = 180 * 1000;

	// factories
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
	private IStationsControllerFactory stationsControllerFactory;

	public Mainframe(IBackupFactory backupFactory,
			IPartiesListFactory partiesListFactory, IPartyFactory partyFactory,
			IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory,
			IChoosingListFactory choosingListFactory,
			IChoosingWindowFactory choosingWindowFactory,
			IVotingStationFactory votingStationFactory,
			IVotingStationWindowFactory votingStationWindowFactory,
			IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory,
			IStationsControllerFactory stationsControllerFactory) {
		this.backupFactory = backupFactory;
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;
		this.choosingListFactory = choosingListFactory;
		this.choosingWindowFactory = choosingWindowFactory;
		this.votingStationFactory = votingStationFactory;
		this.votingStationWindowFactory = votingStationWindowFactory;
		this.mainframeWindowFactory = mainframeWindowFactory;
		this.readSuppliedXMLFactory = readSuppliedXMLFactory;
		this.stationsControllerFactory = stationsControllerFactory;
	}

	@Override
	public void initialize() {
		IReadSuppliedXML init = readSuppliedXMLFactory.createInstance(
				voterDataFactory, votersListFactory, partyFactory,
				partiesListFactory);
		voters = init.readVotersList();
		parties = init.readPartiesList();
		unregisteredVoters = votersListFactory.createInstance();
		initStations();
		backup = backupFactory.createInstance(partiesListFactory, partyFactory,
				votersListFactory, voterDataFactory);
		continueRun = true;
	}

	private void initStations() {
		votingStations = stationsControllerFactory.createInstance(this,
				votingStationFactory, choosingListFactory,
				choosingWindowFactory, votingStationWindowFactory);
		votingStations.initialize(parties);
	}

	@Override
	public void restore() {
		backup = backupFactory.createInstance(partiesListFactory, partyFactory,
				votersListFactory, voterDataFactory);
		voters = backup.restoreVoters();
		parties = backup.restoreParties();
		unregisteredVoters = votersListFactory.createInstance();
		initStations();
	}

	@Override
	public void countVotes() {
		// We want all the votes from all the stations.
		hotBackup();
		// We don't want to be interrupted so we'll work on a local copy.
		IPartiesList parties = this.parties.copy();
		// TODO: Now we should send 'parties' to the mainframe's window for
		// display.
	}

	@Override
	public void shutDown() {
		continueRun = false;
		backupState();
		for (IVotingStation s : votingStations) {
			s.retire();
		}
		// TODO: Retire mainframe window.
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
		} while (!matchingLists(voters, parties));
		backup.storeState(parties, voters);
	}

	// Synchronize mainframe's parties list with the ones in the voting
	// stations.
	private void hotBackup() {
		IPartiesList stationsParties = votingStations.hotBackup();
		if (stationsParties.size() == 0) {
			// error?
			return;
		}
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
		boolean inVoters = voters.inList(id);
		boolean inUnregisteredVoters = unregisteredVoters.inList(id);
		if (!inVoters && !inUnregisteredVoters) {
			throw new VoterDoesNotExist();
		}
		IVoterData voter = null;
		if (inVoters) {
			try {
				voter = voters.findVoter(id);
			} catch (VoterDoesntExist e) {
				// won't happen
				e.printStackTrace();
			}
		} else {
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
	public synchronized VoterStatus getVoterStatus(int id) {
		IVoterData voter;
		try {
			voter = getVoter(id);
		} catch (VoterDoesNotExist e) {
			return VoterStatus.unidentified;
		}
		if (voter.hasVoted())
			return VoterStatus.voted;
		if (voter.isIdentified())
			return VoterStatus.identified;
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
