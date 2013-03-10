package unitTests.practiceStation;

import global.dictionaries.Languages;
import practiceStation.logic.IPracticeStation;

/**
 * Enum that represent the methods that practice station provide.
 */
public enum PracticeTestDriverCalls {
	PracticeVote{
		@Override
		public String toString() {
			return "call parcticeStation.parcticeVote()";
		}
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.practiceVote();
		}
	},
	Retire{
		@Override
		public String toString() {
			return "call parcticeStation.retire()";
		}
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.retire();
		}
	},
	SetLangugeEnglish{
		@Override
		public String toString() {
			return "call parcticeStation.setLanguage("+Languages.English+")";
		}
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.setLanguage(Languages.English);
		}
	},
	
	SetLangugeHebrew{
		
		@Override
		public String toString() {
			return "call parcticeStation.setLanguage("+Languages.Hebrew+")";
		}
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.setLanguage(Languages.Hebrew);
		}
	}
	
	;
	
	public abstract void activate(IPracticeStation testedStation);
}
