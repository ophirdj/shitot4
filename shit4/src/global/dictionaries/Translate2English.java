package global.dictionaries;

import java.util.EnumMap;

public class Translate2English implements IDictionary {
	
	private static final EnumMap<Messages, String> dictionary = createDictionary();
	
	private static EnumMap<Messages, String> createDictionary(){
		EnumMap<Messages, String> dict = new EnumMap<Messages, String>(Messages.class);
		
		
		//all translations should be put here
		dict.put(Messages.Main_Window, "Main Window");
		dict.put(Messages.Ask_for_conformation, "Ask for confirmation");
		dict.put(Messages.ERROR, "ERROR");
		dict.put(Messages.FYI, "FYI");//what's the meaning of this?
		dict.put(Messages.Exit, "Exit");
		dict.put(Messages.Main_frame, "Main Frame");
		dict.put(Messages.histogram, "histogram");
		dict.put(Messages.table, "table");
		dict.put(Messages.enter_ID, "enter ID");
		dict.put(Messages.symbol, "symbol");
		dict.put(Messages.name, "name");
		dict.put(Messages.votes, "votes");
		dict.put(Messages.previous, "previous");
		dict.put(Messages.next, "next");
		dict.put(Messages.white_note, "white note");
		dict.put(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone, " Are you sure you don't want to vote for anyone");
		dict.put(Messages.Are_you_sure_you_want_to_vote_for, "Are you sure you want to vote for");
		dict.put(Messages.This_station_is_only_for_practice, "This station is for practice only");
		dict.put(Messages.Do_you_want_to_see_a_guide, "Do you want to see a guide");
		dict.put(Messages.Did_you_intend_to_vote_for, "Did you intend to vote for ");
		dict.put(Messages.Have_you_understood_the_process, "Have you understood the process?");
		dict.put(Messages.Your_time_is_up, "Your time is up");
		dict.put(Messages.enter_password, "enter password");
		dict.put(Messages.You_quit_in_the_process_of_voting, "You quit in the process of voting");
		dict.put(Messages.You_need_to_identify_yourself_in_the_mainframe, "You need to identify yourself in the mainframe");
		dict.put(Messages.You_cannot_change_your_vote_anymore, "You cannot change your vote anymore");
		dict.put(Messages.You_cannot_vote_here, "You cannot vote here");
		dict.put(Messages.ID_must_be_a_number, "ID must be a number");
		dict.put(Messages.Chuck_Norris_removed_you_from_existance, "Chuck Norris removed you from existance");
		dict.put(Messages.You_successfully_voted_for_the_party, "You successfull voted for the party");
		dict.put(Messages.You_successfully_test_voted_for_the_party, "You successfully test voted for the party");
		dict.put(Messages.wrong_password, "wrong password");
		dict.put(Messages.make_vote, "make vote");
		dict.put(Messages.test_vote, "test vote");
		dict.put(Messages.practice_vote, "practice vote");
		dict.put(Messages.shut_down, "shut down");
		dict.put(Messages.count_votes, "count votes");
		dict.put(Messages.identification, "identification");
		dict.put(Messages.boot, "boot");
		dict.put(Messages.boot_from_backup, "boot from backup");
		dict.put(Messages.ID_is_already_registered, "ID is slready registered");
		dict.put(Messages.Yes, "Yes");
		dict.put(Messages.No, "No");
		dict.put(Messages.Ok, "Ok");
		dict.put(Messages.practiceStation, "practice station no. ");
		dict.put(Messages.voting_station, "voting station no. ");
		return dict;
	}

	@Override
	public String translate(Messages message) {
		return dictionary.get(message);
	}
	
}
