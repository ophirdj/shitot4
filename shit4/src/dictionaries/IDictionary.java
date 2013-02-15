package dictionaries;

/**
 * Added feature: display messages in user's native language.
 * All messages should be first translated via this interface.
 * @author Ophir De Jager
 *
 */
public interface IDictionary {
	
	/**
	 * 
	 * @author Ophir De Jager
	 * This is the dictionary that contains all the phrases that the program shows the user.
	 * Every message must have a corresponding enum here to be successfully translated.
	 * Add your messages here if you can't find one that matches.
	 */
	enum Messages{
		Main_Window,
		Ask_for_conformation,
		ERROR,
		FYI,
		Exit,
		Main_frame,
		histogram,
		table,
		enter_ID,
		symbol,
		name,
		votes,
		previous,
		next,
		white_note,
		Are_you_sure_you_dont_want_to_vote_for_anyone,
		Are_you_sure_you_want_to_vote_for,
		This_station_is_only_for_practice,
		Do_you_want_to_see_a_guide,
		You_voted_for,
		Is_that_what_you_meant,
		Have_you_understood_the_process,
		Your_time_is_up,
		enter_password,
		You_quit_in_the_process_of_voting,
		You_need_to_identify_yourself_in_the_mainframe,
		You_cannot_change_your_vote_anymore,
		You_cannot_vote_here,
		ID_must_be_a_number,
		Chuck_Norris_removed_you_from_existance,
		You_successfully_voted_for_the_party,
		wrong_password,
		make_vote,
		test_vote,
		practice_vote,
		shut_down,
		count_votes,
		identification,
		boot,
		boot_from_backup,
		ID_is_already_registered
		
	}
	
	String translate(Messages message);
}
