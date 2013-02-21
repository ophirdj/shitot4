package practiceStation;

import java.io.File;
import GUI.IListImages;

public class PracticeStationImagesHebrew implements IListImages{
	final String src_directory = "Images/";
	final String[] filesName ={"enterScreenEnglishExplained.png","ID_ScreenEnglishExplained.png"};
	public File getFile(int num){
		return new File(src_directory+filesName[num]);
	}
	
	public int size(){
		return filesName.length;
	}
}
