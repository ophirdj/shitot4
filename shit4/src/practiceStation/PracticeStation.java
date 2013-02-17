package practiceStation;

import javax.swing.JPanel;

import choosingList.IChoosingList;
import choosingList.IChoosingList.ChoosingInterruptedException;

import GUI.IImagePanel;
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

	private final int max_practice_time = 5;

	public PracticeStation(String name, IPartiesList parties,
			IChoosingListFactory choseFactory,
			IChoosingWindowFactory choseWindowFactory,
			IPracticeStationWindowFactory stationWindowFactory,
			IImagePanelFactory imagePanelFactory) {
		this.parties = parties;
		this.practiceStationWindow = stationWindowFactory.createInstance(name,
				this);
		this.imagePanelFactory = imagePanelFactory;
		this.choosingList = choseFactory.createInstance(parties,
				(JPanel) practiceStationWindow, choseWindowFactory);
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
				(JPanel) practiceStationWindow);
		Watcher watcher = new Watcher(this);
		try {
			watcher.start();
			practiceStationWindow
					.printMessage("This station is only for practice");
			while (!understandConformation) {
				watcher.checkTime();
				boolean choice = practiceStationWindow
						.getConfirmation("Do you want to see a guide?");
				if (choice) {
					guide.showFirstImage();
				}
				watcher.checkTime();
				practiceStationWindow
						.printMessage("This station is only for practice");
				try {
					boolean partyConformation = false;
					while (!partyConformation) {
						chosen = choosingList.chooseList();
						String confirmationMessage = "You voted for "
								+ chosen.getName()
								+ ". is that what you meant?";
						watcher.checkTime();
						partyConformation = practiceStationWindow
								.getConfirmation(confirmationMessage);
					}
				} catch (ChoosingInterruptedException e) {
					throw new PracticeTimedOutException();
				}
				watcher.checkTime();
				understandConformation = practiceStationWindow
						.getConfirmation("did you understand the process?");
			}
		} catch (PracticeTimedOutException e) {
			practiceStationWindow.printMessage("your time is up");
		}
		watcher.interrupt();
	}

	public void retire() {
	}

}
