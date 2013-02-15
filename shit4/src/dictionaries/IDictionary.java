package dictionaries;

/**
 * Added feature: display messages in user's native language.
 * All messages should be first translated via this interface.
 * @author Ophir De Jager
 *
 */
public interface IDictionary {
	
	enum Messages{
		Hi
	}
	
	String translate(Messages message);
}
