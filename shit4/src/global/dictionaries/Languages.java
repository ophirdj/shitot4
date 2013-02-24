package global.dictionaries;
import java.util.Map;

public enum Languages {	
	English{
		@Override
		public String toString() {
			return "English";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Dictionary(EnglishDictionary);
		}
	},
	
	Hebrew{		
		@Override
		public String toString() {
			return "Hebrew";
		}
		
		@Override
		public IDictionary getDictionary(){
			return new Dictionary(HebrewDictionary);
		}
	}
	;
	
	//dictionaries
	private static final Map<Messages, String> EnglishDictionary = ReadDictionary.readDictionary("English.dict");
	private static final Map<Messages, String> HebrewDictionary = ReadDictionary.readDictionary("Hebrew.dict");
	
	
	
	public abstract IDictionary getDictionary();
}
