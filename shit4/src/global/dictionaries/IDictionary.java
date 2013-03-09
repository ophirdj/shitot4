package global.dictionaries;

/**
 * Dictionary to translate Messages type to String. 
 * @author Ophir De Jager
 *
 */
public interface IDictionary {
	
	/**
	 * Exception thrown if dictionary entries are missing
	 * @author Ophir De Jager
	 *
	 */
	public class SomeDictionaryEntriesAreMissing extends Exception{
		private static final long serialVersionUID = 1L;
		}
	
	
	/**
	 * Translate from Messages type to actual Sting
	 * @param message the message we want to show
	 * @return a translation of the message to the dictionary's language
	 */
	String translate(Messages message);
}
