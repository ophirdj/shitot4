package practiceStation;

public interface IPracticeStation {
	
	class PracticeTimedOutException extends Exception{
		static final long serialVersionUID = 1L;
	}
	
	
	void practiceVote();
	
	void retire();
}