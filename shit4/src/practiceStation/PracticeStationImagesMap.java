package practiceStation;

import java.util.EnumMap;

import GUI.IListImages;

import dictionaries.Languages;

public class PracticeStationImagesMap extends EnumMap<Languages, IListImages>{

	private static final long serialVersionUID = 1L;

	public PracticeStationImagesMap() {
		super(Languages.class);
		put(Languages.English,new PracticeStationImagesEnglish());
		put(Languages.Hebrew,new PracticeStationImagesHebrew());
	}
	

}
