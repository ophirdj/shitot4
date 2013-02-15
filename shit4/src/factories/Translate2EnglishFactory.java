package factories;

import dictionaries.IDictionary;
import dictionaries.Translate2English;

public class Translate2EnglishFactory implements IDictionaryFactory {

	@Override
	public IDictionary createInstance() {
		return new Translate2English();
	}

}
