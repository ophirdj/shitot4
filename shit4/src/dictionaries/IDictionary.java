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
	 * @param message: the message we want to show
	 * @return a string that show the message in the correct language
	 */
	String translate(Messages message);
}
