package practiceStation;

import javax.swing.JPanel;

import choosingList.IChoosingList;
import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.Languages;
import dictionaries.Messages;

import GUI.BasicPanel;
import GUI.IImagePanel;
import GUI.StationPanel;
import factories.IChoosingListFactory;
import partiesList.IPartiesList;
import partiesList.IParty;

public class PracticeStation implements IPracticeStation {

	IPartiesList parties;
	IPracticeStationWindow practiceStationWindow;
	IChoosingList choosingList;
	IImagePanelFactory imagePanelFactory;
	IImagePanel guide;
	Languages language;

	private final int max_practice_time = 5;

	public PracticeStation(String name, IPartiesList parties,
			IChoosingListFactory chooseFactory,
			IPracticeStationWindowFactory stationWindowFactory,
			IImagePanelFactory imagePanelFactory) {
		this.parties = parties;
		this.practiceStationWindow = stationWindowFactory.createInstance(name,
				this);
		this.imagePanelFactory = imagePanelFactory;
		this.choosingList = chooseFactory.createInstance(parties,
				(StationPanel) practiceStationWindow);
	};

	class Watcher extends Thread {
		private Object lock;
		private boolean timeout = false;

		public Watcher(Object lock) {
			this.lock = lock;
		}

		private long maxTime() {
			final long mill2Minutes = 1000 * 60;
			return max_practice_time * mill2Minutes;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(maxTime());
				synchronized (lock) {
					timeout = true;
					choosingList.retire();
					guide.retire();
					lock.notify();
				}
			} catch (InterruptedException e) {
			}
		}

		public void checkTime() throws PracticeTimedOutException {
			if (timeout)
				throw new PracticeTimedOutException();
		}
	}

	public void practiceVote() {
		boolean understandConformation = false;
		IParty chosen;
		guide = imagePanelFactory.createInstance(new PracticeStationImagesMap(),
				(StationPanel) practiceStationWindow);
		Watcher watcher = new Watcher(this);
		try {
			watcher.start();
			practiceStationWindow
					.printInfoMessage(Messages.This_station_is_only_for_practice);
			while (!understandConformation) {
				watcher.checkTime();
				boolean choice = practiceStationWindow
						.printConformationMessage(Messages.Do_you_want_to_see_a_guide);
				if (choice) {
					guide.showFirstImage(this.language);
				}
				watcher.checkTime();
				practiceStationWindow
						.printInfoMessage(Messages.This_station_is_only_for_practice);
				try {
					boolean partyConformation = false;
					while (!partyConformation) {
						chosen = choosingList.chooseList();
						watcher.checkTime();
						partyConformation = practiceStationWindow
								.printConformationMessage(Messages.Did_you_intend_to_vote_for, chosen);
					}
				} catch (ChoosingInterruptedException e) {
					throw new PracticeTimedOutException();
				}
				watcher.checkTime();
				understandConformation = practiceStationWindow
						.printConformationMessage(Messages.Have_you_understood_the_process);
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
