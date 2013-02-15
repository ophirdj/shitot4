package translate;

/**
 * Added feature: display messages in user's native language.
 * All messages should be first translated via this interface.
 * @author Ophir De Jager
 *
 */
public interface ITranslator {
	
	enum Messages{
		Hi
	}
	
	String getMessage(Messages message);
}
