package practiceStation;

import javax.swing.JPanel;

import choosingList.IChoosingList;
import choosingList.IChoosingList.ChoosingInterruptedException;
import dictionaries.IDictionary.Messages;

import GUI.IImagePanel;
import GUI.Main_Window;
import factories.IChoosingListFactory;
import factories.IChoosingWindowFactory;
import partiesList.IPartiesList;
import partiesList.IParty;

public class PracticeStation implements IPracticeStation {

	IPartiesList parties;
	IPracticeStationWindow practiceStationWindow;
	IChoosingList choosingList;
	IImagePanelFactory imagePanelFactory;
	IImagePanel guide;
	private Main_Window mainWindow;

	private final int max_practice_time = 5;

	public PracticeStation(String name, IPartiesList parties,
			IChoosingListFactory choseFactory,
			IChoosingWindowFactory choseWindowFactory,
			IPracticeStationWindowFactory stationWindowFactory,
			IImagePanelFactory imagePanelFactory, Main_Window mainWindow) {
		this.parties = parties;
		this.practiceStationWindow = stationWindowFactory.createInstance(name,
				this, mainWindow);
		this.imagePanelFactory = imagePanelFactory;
		this.choosingList = choseFactory.createInstance(parties,
				(JPanel) practiceStationWindow, choseWindowFactory, mainWindow);
		this.mainWindow = mainWindow;
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
		guide = imagePanelFactory.createInstance(new PracticeStationImages(),
				(JPanel) practiceStationWindow,mainWindow);
		Watcher watcher = new Watcher(this);
		try {
			watcher.start();
			practiceStationWindow
					.printMessage(mainWindow.translate(Messages.This_station_is_only_for_practice));
			while (!understandConformation) {
				watcher.checkTime();
				boolean choice = practiceStationWindow
						.getConfirmation(mainWindow.translate(Messages.Do_you_want_to_see_a_guide) + "?");
				if (choice) {
					guide.showFirstImage();
				}
				watcher.checkTime();
				practiceStationWindow
						.printMessage(mainWindow.translate(Messages.This_station_is_only_for_practice));
				try {
					boolean partyConformation = false;
					while (!partyConformation) {
						chosen = choosingList.chooseList();
						String confirmationMessage = mainWindow.translate(Messages.You_voted_for) + " "
								+ chosen.getName()
								+ ". " + mainWindow.translate(Messages.Is_that_what_you_meant) + "?";
						watcher.checkTime();
						partyConformation = practiceStationWindow
								.getConfirmation(confirmationMessage);
					}
				} catch (ChoosingInterruptedException e) {
					throw new PracticeTimedOutException();
				}
				watcher.checkTime();
				understandConformation = practiceStationWindow
						.getConfirmation(mainWindow.translate(Messages.Have_you_understood_the_process) + "?");
			}
		} catch (PracticeTimedOutException e) {
			practiceStationWindow.printMessage(mainWindow.translate(Messages.Your_time_is_up));
		}
		watcher.interrupt();
	}

	public void retire() {
	}

}
