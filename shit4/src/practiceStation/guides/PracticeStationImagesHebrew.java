package practiceStation.guides;

import java.io.File;

public class PracticeStationImagesHebrew implements IListImages{
	final String src_directory = "Images/English/";
	final String[] filesName ={"enterScreenEnglishExplained.png","ID_ScreenEnglishExplained.png"};
	public File getFile(int num){
		return new File(src_directory+filesName[num]);
	}
	
	public int size(){
		return filesName.length;
	}
}
