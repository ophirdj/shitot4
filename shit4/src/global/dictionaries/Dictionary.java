package global.dictionaries;

import java.util.EnumMap;
import java.util.Map;

/**
 * A simple and generic way to implement any dictionary 
 * @author Ophir De Jager
 *
 */
public class Dictionary implements IDictionary {
	
	private final Map<Messages, String> dict;
	
	
	/**
	 * Create a new Dictionary from given Map (uses keys as entries and values as translations).
	 * @param dict the given Map
	 * @throws SomeDictionaryEntriesAreMissing if some entries are missing. 
	 */
	public Dictionary(Map<Messages, String> dict) throws SomeDictionaryEntriesAreMissing {
		
		if(!isValid(dict)) throw new SomeDictionaryEntriesAreMissing();
		this.dict = new EnumMap<Messages, String>(dict);
	}
	
	/**
	 * Check if a given dictionary is valid (i.e. every message
	 * has a matching String value).
	 * @param dict the dictionary to be checked
	 * @return true if valid, false otherwise
	 */
	public static boolean isValid(Map<Messages, String> dict){
		for(Messages message: Messages.values()){
			if(!dict.containsKey(message)) return false;
		}
		return true;
	}

	@Override
	public String translate(Messages message) {
		return dict.get(message);
	}

}
