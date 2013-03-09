package practiceStation.guides;

import java.io.File;

/**
 * The guide for Hebrew practice stations.
 */
public class PracticeStationImagesHebrew implements IListImages{
	final String src_directory = "Images/Hebrew/";
	final String[] filesName ={"enterScreenExplainedHe.png","ID_ScreenExplainedHe.png",
			"choosingScreenExplained1He.png","choosingScreenExplained2He.png",
			"choosingScreenExplained3He.png","ConfirmationExplainedHe.png",
			"EndingScreenExplainedHe.png"};
	public File getFile(int num){
		return new File(src_directory+filesName[num]);
	}
	
	public int size(){
		return filesName.length;
	}
}
