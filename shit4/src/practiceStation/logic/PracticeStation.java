package practiceStation.logic;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
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
				(StationPanel) practiceStationWindow);
		this.max_practice_time = mill2Minutes * DEFUALT_MINUTES;
		this.guide = imagePanelFactory.createInstance(new PracticeStationImagesMap(),
				(StationPanel) practiceStationWindow);
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
				(StationPanel) practiceStationWindow);
		this.max_practice_time = max_practice_time;
		this.guide = imagePanelFactory.createInstance(new PracticeStationImagesMap(),
				(StationPanel) practiceStationWindow);
	};

	class Watcher extends Thread {
		private Object lock;
		private boolean timeout = false;

		public Watcher(Object lock) {
			this.lock = lock;
		}

		private long maxTime() {
			return max_practice_time;
		}

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

		public void checkTime() throws PracticeTimedOutException {
			synchronized (lock) {
				if (timeout)
					throw new PracticeTimedOutException();
			}
		}
	}

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
					guide.showGuide(this.language);
				}
				watcher.checkTime();
				practiceStationWindow
						.printInfoMessage(Messages.This_station_is_only_for_practice);
				try {
					boolean partyConfirmation = false;
					while (!partyConfirmation) {
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

	public void retire() {
	}
	
	public void setLanguage(Languages language){
		this.language = language;
	}

}
