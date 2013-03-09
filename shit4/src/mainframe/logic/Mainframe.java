package mainframe.logic;

import mainframe.communication.IStationsController;
import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.MainframeAction.MainframeState;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IBackup;
import fileHandler.logic.IReadSuppliedXML;
import partiesList.model.IPartiesList;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.IVoterData.AlreadyIdentified;
import votersList.model.IVoterData.Unidentified;
import votersList.model.IVotersList.VoterDoesntExist;

/**
 * The Mainframe. The station that keeps track of the voting condition so far.
 * Also responsible for backing up and showing election local results.
 */
public class Mainframe implements IMainframe {
	
	private IVotersList voters;
	private IPartiesList parties;
	private IVotersList unregisteredVoters;
	private IStationsController votingStations;
	private IBackup backup;
	private HotBackup hotBackup;
	private IMainframeWindow window;

	private final int MILLISECONDS_BETWEEN_BACKUPS;

	// factories
	private IBackupFactory backupFactory;
	private IVoterDataFactory voterDataFactory;
	private IVotersListFactory votersListFactory;
	private IReadSuppliedXMLFactory readSuppliedXMLFactory;
	private IStationsControllerFactory stationsControllerFactory;

	/**
	 * 
	 * @param backupTimeIntervalSeconds the time between two hot backups (in seconds)
	 * @param backupFactory factory of the class Backup
	 * @param mainframeWindowFactory factory of the class mainframeWindow
	 * @param readSuppliedXMLFactory factory of the class readSuppliedXML
	 * @param stationsControllerFactory factory of the class stationsController
	 * @param voterDataFactory factory of the class voterData
	 * @param votersListFactory factory of the class votersList
	 */
	public Mainframe(int backupTimeIntervalSeconds, IBackupFactory backupFactory,
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
		this.MILLISECONDS_BETWEEN_BACKUPS = backupTimeIntervalSeconds * 1000;
		this.window = mainframeWindowFactory.createInstance(this);
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
	
	/**
	 * Initiate the mainframe and stations.
	 * Start the hot backup routine.
	 * 
	 * @param voters: The voters that can vote here (predefined or that already identify themselves here)
	 * @param unregistered: Voters that identify themselves here but were not registered in the initial voters file.
	 * @param parties: The parties list (might be with partial voting)
	 */
	private void init(IVotersList voters, IVotersList unregistered, IPartiesList parties){
		this.voters = voters;
		this.parties = parties;
		this.unregisteredVoters = unregistered;
		votingStations = stationsControllerFactory.createInstance(this);
		votingStations.initialize(parties);
		hotBackup = new HotBackup(this);
		hotBackup.start();
		window.setState(MainframeState.AfterInit);
	}

	@Override
	public void countVotes() {
		// We want all the votes from all the stations.
		gatherVotesFromVotingStations();
		// We don't want to be interrupted so we'll work on a local copy.
		IPartiesList parties = this.parties.copy();
		
		window.setDataDisplay(parties);
		window.setState(MainframeState.VotesCounted);
	}
	
	@Override
	public void shutDown() {
		if(hotBackup != null) hotBackup.retire();
		if(votingStations != null) votingStations.retire();
	}

	@Override
	public void crash(){
		if(hotBackup != null) hotBackup.kill();
	}

	/**
	 *  Save voters and parties lists to backup file. Lists must match.
	 */
	private void backupState() {
		IVotersList unregistered;
		IVotersList voters;
		IPartiesList parties;
		do {
			gatherVotesFromVotingStations();
			synchronized (this) {
				unregistered = unregisteredVoters.copy();
				voters = this.voters.copy();
				parties = this.parties.copy();
			}
		} while (!matchingLists(voters ,parties));
		backup.storeState(parties, voters, unregistered);
	}

	/**
	 *  Synchronize mainframe's parties list with the ones in the voting stations.
	 */
	private void gatherVotesFromVotingStations() {
		IPartiesList stationsParties = votingStations.gatherVotesFromVotingStations();
		synchronized (this) {
			parties = stationsParties;
		}
	}

	/**
	 *  Check that sum of votes in voters list matches sum of votes in parties list
	 * @param voters: The voter list.
	 * @param parties: The parties list
	 * @return True if the amount of votes to the parties match the amount of voting done by voters.
	 * false otherwise.
	 */
	private boolean matchingLists(IVotersList voters, IPartiesList parties) {
		int sumVotesVoters = 0;
		for (IVoterData v : voters) {
			if (v.hasVoted()) {
				sumVotesVoters++;
			}
		}
		return sumVotesVoters == parties.getTotalVotes();
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
		votingStations.peep();
	}

	/**
	 * Return the voter that match given id.
	 * @param id: represent a voter id.
	 * @return The voter data of the voter with the given id.
	 * @throws VoterDoesNotExist: throw this if the voter doesn't exist.
	 */
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
	public boolean checkParties(){
		if(votingStations == null) return false;
		return votingStations.checkParties(parties.zeroCopy());
	}

	@Override
	public boolean checkInit() {
		if(votingStations == null) return false;
		return votingStations.checkInit();
	}
	
	/**
	 * Thread that, when run, performs hot backup every given time
	 */
	private class HotBackup extends Thread{
		
		/**
		 * Lock to sync backup actions
		 */
		private Object lock;
		/**
		 * True while still need run hot backup
		 */
		private boolean continueRun;
		private Mainframe caller;
		
		/**
		 * Create new BackupThread ready to run.
		 * @param caller: The caller mainframe 
		 */
		public HotBackup(Mainframe caller) {
			lock = new Object();
			continueRun = true;
			this.caller = caller;
		}
		
		/**
		 * Backup mainframe state every given time (MILLISECONDS_BETWEEN_BACKUPS)
		 */
		@Override
		public void run() {
			synchronized (lock) {
				while(continueRun){
					caller.backupState();
					try {lock.wait(MILLISECONDS_BETWEEN_BACKUPS);}
					catch (InterruptedException e) {}
				}
			}
		}
		
		/**
		 * Halt backup thread & backup one last time.
		 */
		public void retire(){
			synchronized(lock){
				continueRun = false;
				lock.notify();
			}
			try {join();}
			catch (InterruptedException e) {}
			caller.backupState();
		}
		
		/**
		 * Simulate crush (exit without backup). for test only.
		 * Halt backup thread without ensuring a backup was made.
		 */
		public void kill(){
			synchronized(lock){
				continueRun = false;
				lock.notify();
			}
			try {join();}
			catch (InterruptedException e) {}
		}
	}

}
