package practiceStation.logic;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import choosingList.factories.IChoosingListFactory;
import choosingList.logic.IChoosingList;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.gui.IPracticeStationWindow;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.PracticeStationImagesMap;

public class PracticeStation implements IPracticeStation {

	private final int DEFUALT_MINUTES = 5;
	
	private IPracticeStationWindow practiceStationWindow;
	private IChoosingList choosingList;
	private IImagePanel guide;
	private Languages language;
	

	private final long mill2Minutes = 1000 * 60;
	private long max_practice_time;

	public PracticeStation(IPartiesList parties,
			IChoosingListFactory chooseFactory,
			IPracticeStationWindowFactory stationWindowFactory,
			IImagePanelFactory imagePanelFactory) {
		this.practiceStationWindow = stationWindowFactory.createInstance(this);
		this.choosingList = chooseFactory.createInstance(parties,
				practiceStationWindow);
		this.max_practice_time = mill2Minutes * DEFUALT_MINUTES;
		this.guide = imagePanelFactory.createInstance(new PracticeStationImagesMap(),
				practiceStationWindow);
	};
	
	/**
	 * Build practice station for test (different waiting time)
	 */
	public PracticeStation(IPartiesList parties,
			IChoosingListFactory chooseFactory,
			IPracticeStationWindowFactory stationWindowFactory,
			IImagePanelFactory imagePanelFactory,
			long max_practice_time) {

		this.practiceStationWindow = stationWindowFactory.createInstance(this);
		this.choosingList = chooseFactory.createInstance(parties,
				practiceStationWindow);
		this.max_practice_time = max_practice_time;
		this.guide = imagePanelFactory.createInstance(new PracticeStationImagesMap(),
				practiceStationWindow);
	};
	
	/**
	 * A thread that, after 5 minutes, signal the classes practice station
	 * use to retire. The practice station need to check after every message printing
	 * that it still have time!
	 */
	class Watcher extends Thread {
		private Object lock;
		private boolean timeout = false;

		public Watcher(Object lock) {
			this.lock = lock;
		}
		
		/**
		 * @return The maximum time for running.
		 */
		private long maxTime() {
			return max_practice_time;
		}

		/**
		 * Sleep for the given time (default 5 min.) and then if wasn't
		 * interrupted notify the guide and the choosing list to retire.
		 */
		@Override
		public void run() {
			try {
				Thread.sleep(maxTime());
				synchronized (lock) {
					choosingList.retire();
					guide.retire();
					timeout = true;
					lock.notify();
				}
			} catch (InterruptedException e) {}
		}

		/**
		 * Check that there is still time.
		 * @throws PracticeTimedOutException if time was over.
		 */
		public void checkTime() throws PracticeTimedOutException {
			synchronized (lock) {
				if (timeout)
					throw new PracticeTimedOutException();
			}
		}
	}

	@Override
	public void practiceVote() {
		boolean understandConfirmation = false;
		IParty chosen;
		Watcher watcher = new Watcher(this);
		try {
			watcher.start();
			practiceStationWindow
					.printInfoMessage(Messages.This_station_is_only_for_practice);
			while (!understandConfirmation) {
				watcher.checkTime();
				boolean choice = practiceStationWindow
						.printConfirmationMessage(Messages.Do_you_want_to_see_a_guide);
				if (choice) {
					watcher.checkTime();
					guide.showGuide(this.language);
				}
				watcher.checkTime();
				practiceStationWindow
						.printInfoMessage(Messages.This_station_is_only_for_practice);
				try {
					boolean partyConfirmation = false;
					while (!partyConfirmation) {
						watcher.checkTime();
						chosen = choosingList.chooseList();
						watcher.checkTime();
						partyConfirmation = practiceStationWindow
								.printConfirmationMessage(Messages.Did_you_intend_to_vote_for, chosen);
					}
				} catch (ChoosingInterruptedException e) {
					/*
					 * synchronized to assure deterministic calls for testing
					 */
					synchronized (this) {
						throw new PracticeTimedOutException();
					}
				}
				watcher.checkTime();
				understandConfirmation = practiceStationWindow
						.printConfirmationMessage(Messages.Have_you_understood_the_process);
			}
		} catch (PracticeTimedOutException e) {
			practiceStationWindow.printInfoMessage(Messages.Your_time_is_up);
		}
		watcher.interrupt();
	}

	@Override
	public void retire() {
	}
	
	@Override
	public void setLanguage(Languages language){
		this.language = language;
	}

}
