package practiceStation.tests;

import global.dictionaries.Languages;
import practiceStation.logic.IPracticeStation;


public enum PracticeTestDriverCalls {
	PracticeVote{
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.practiceVote();
		}
	},
	Retire{
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.retire();
		}
	},
	SetLangugeEnglish{
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.setLanguage(Languages.English);
		}
	},
	
	SetLangugeHebrew{
		
		@Override
		public void activate(IPracticeStation testedStation){
			testedStation.setLanguage(Languages.Hebrew);
		}
	}
	
	;
	
	public abstract void activate(IPracticeStation testedStation);
}
