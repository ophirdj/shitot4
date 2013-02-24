package global.dictionaries;

import java.util.Map;

public class Dictionary implements IDictionary {
	
	private Map<Messages, String> dict;
	
	
	public Dictionary(Map<Messages, String> dict) {
		this.dict = dict;
	}

	@Override
	public String translate(Messages message) {
		return dict.get(message);
	}

}
