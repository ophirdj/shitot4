package global.dictionaries;

import java.util.EnumMap;

public class Translate2Hebrew implements IDictionary {
	
	private static final EnumMap<Messages, String> dictionary = createDictionary();
	
	private static EnumMap<Messages, String> createDictionary(){
		EnumMap<Messages, String> dict = new EnumMap<Messages, String>(Messages.class);
		
		
		//all translations should be put here
		dict.put(Messages.Main_Window, "חלון ראשי");
		dict.put(Messages.Ask_for_conformation, "בקשת אישור");
		dict.put(Messages.ERROR, "שגיאה");
		dict.put(Messages.FYI, "לידיעתך");//what's the meaning of this?
		dict.put(Messages.Exit, "יציאה");
		dict.put(Messages.Main_frame, "עמדת ועדת הקלפי");
		dict.put(Messages.histogram, "היסטוגרמה");
		dict.put(Messages.table, "טבלה");
		dict.put(Messages.enter_ID, "הכנס/י תז");
		dict.put(Messages.symbol, "סמל");
		dict.put(Messages.name, "שם");
		dict.put(Messages.votes, "הצבעות");
		dict.put(Messages.previous, "הקודם");
		dict.put(Messages.next, "הבא");
		dict.put(Messages.white_note, "פתק לבן");
		dict.put(Messages.Are_you_sure_you_dont_want_to_vote_for_anyone, "האם את/ה בטוח/ה שאינך רוצה להצביע לאף אחד?");
		dict.put(Messages.Are_you_sure_you_want_to_vote_for, "האם את/ה בטוח/ה שברצונך להצביע ");
		dict.put(Messages.This_station_is_only_for_practice, "העמדה הזאת למטרת אימון בלבד");
		dict.put(Messages.Do_you_want_to_see_a_guide, "האם ברצונך לראות מדריך?");
		dict.put(Messages.Did_you_intend_to_vote_for, "האם התכוונת להצביע ");
		dict.put(Messages.Have_you_understood_the_process, "האם הבנת את תהליך ההצבעה?");
		dict.put(Messages.Your_time_is_up, "זמנך נגמר");
		dict.put(Messages.enter_password, "הכנס/י סיסמה");
		dict.put(Messages.You_quit_in_the_process_of_voting, "הופסקת במהלך ההצבעה");
		dict.put(Messages.You_need_to_identify_yourself_in_the_mainframe, "עליך להזדהות בפני ועדת הקלפי לפני ביצוע הצבעה");
		dict.put(Messages.You_cannot_change_your_vote_anymore, "אינך יכול/ה לשנות יותר את הצבעתך");
		dict.put(Messages.You_cannot_vote_here, "אינך יכול/ה להצביע כאן");
		dict.put(Messages.ID_must_be_a_number, "תז חייבת להיות מספר");
		dict.put(Messages.Chuck_Norris_removed_you_from_existance, "Chuck Norris removed you from existance");
		dict.put(Messages.You_successfully_voted_for_the_party, "הצבעת בהצלחה למפלגת ");
		dict.put(Messages.You_successfully_test_voted_for_the_party, "הצבעת הצבעת סרק בהצלחה למפלגת");
		dict.put(Messages.wrong_password, "הסיסמה שגויה");
		dict.put(Messages.make_vote, "בצע/י הצבעה");
		dict.put(Messages.test_vote, "הצבעת סרק");
		dict.put(Messages.practice_vote, "הצבעת אימון");
		dict.put(Messages.shut_down, "כיבוי");
		dict.put(Messages.count_votes, "ספירת קולות");
		dict.put(Messages.identification, "הזדהות");
		dict.put(Messages.boot, "אתחול");
		dict.put(Messages.boot_from_backup, "אתחול מגיבוי");
		dict.put(Messages.ID_is_already_registered, "תז כבר רשומה");
		dict.put(Messages.Yes, "כן");
		dict.put(Messages.No, "לא");
		dict.put(Messages.Ok, "אישור");
		dict.put(Messages.practiceStation, "עמדת תרגול מספר ");
		dict.put(Messages.voting_station, "עמדת הצבעה מספר ");
		return dict;
	}

	@Override
	public String translate(Messages message) {
		return dictionary.get(message);
	}
	
}
