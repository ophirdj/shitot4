package dictionaries;

import java.util.EnumMap;

public class Translate2English implements IDictionary {
	
	private static final EnumMap<Messages, String> dictionary = createDictionary();
	
	private static EnumMap<Messages, String> createDictionary(){
		EnumMap<Messages, String> dict = new EnumMap<Messages, String>(Messages.class);
		
		//all translations should be put here
		dict.put(Messages.Hi, "Hi");
		
		
		
		
		return dict;
	}

	@Override
	public String translate(Messages message) {
		return dictionary.get(message);
	}
	
}
