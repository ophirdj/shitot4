package practiceStation;

import partiesList.IPartiesList;

public interface IPracticeStationFactory {
	/**
	 * 
	 * @param name:
	 * 				the station name
	 * @param parties:
	 * 				the parties in this station
	 * @return
	 * 				a new Object that implement IPracticeStation
	 */
	IPracticeStation createInstance(String name, IPartiesList parties);
}
