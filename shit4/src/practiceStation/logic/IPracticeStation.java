package practiceStation.logic;

import global.dictionaries.Languages;

/**
 * Interface of practice station used to practice the voting process
 * PracticeStation in completely cut off from the mainframe and voting stations
 * @author Ophir De Jager
 *
 */
public interface IPracticeStation {
	
	class PracticeTimedOutException extends Exception{
		static final long serialVersionUID = 1L;
	}
	
	/**
	 * Let voter practice the voting process
	 */
	void practiceVote();
	
	/**
	 * Shutdown the practice station
	 */
	void retire();
	
	/**
	 * Set the language in the station to <language>
	 * @param language
	 */
	void setLanguage(Languages language);
}