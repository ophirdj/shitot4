package practiceStation.guides;

import global.dictionaries.Languages;

import java.util.EnumMap;



public class PracticeStationImagesMap extends EnumMap<Languages, IListImages>{

	private static final long serialVersionUID = 1L;

	public PracticeStationImagesMap() {
		super(Languages.class);
		put(Languages.English,new PracticeStationImagesEnglish());
		put(Languages.Hebrew,new PracticeStationImagesHebrew());
	}
	

}
