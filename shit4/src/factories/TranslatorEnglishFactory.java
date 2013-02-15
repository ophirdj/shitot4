package factories;

import translate.ITranslator;
import translate.TranslatorEnglish;

public class TranslatorEnglishFactory implements ITranslatorFactory {

	@Override
	public ITranslator createInstance() {
		return new TranslatorEnglish();
	}

}
