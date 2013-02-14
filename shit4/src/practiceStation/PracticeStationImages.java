package practiceStation;

import java.io.File;

import GUI.IListImages;

public class PracticeStationImages implements IListImages{
	final String src_directory = "Images/";
	final String[] filesName ={"enterScreenEnglishExplained.png","ID_ScreenEnglishExplained.png",
			"choosingScreenEnglishExplained1.png","choosingScreenEnglishExplained2.png",
			"choosingScreenEnglishExplained3.png","ConformationEnglishExplained.png",
			"EndingScreenEnglishExplained.png"};
	public File getFile(int num){
		return new File(src_directory+filesName[num]);
	}
	
	public int size(){
		return filesName.length;
	}
}
