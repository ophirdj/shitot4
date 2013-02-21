package practiceStation.factories;

import partiesList.model.IPartiesList;
import practiceStation.logic.IPracticeStation;

public interface IPracticeStationFactory {
	/**
	 * 
	 * @param parties:
	 * 				the parties in this station
	 * @return
	 * 				a new Object that implement IPracticeStation
	 */
	IPracticeStation createInstance(IPartiesList parties);
}
