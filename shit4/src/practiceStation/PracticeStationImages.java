package practiceStation;

import java.io.File;

import GUI.IListImages;

public class PracticeStationImages implements IListImages{
	final String src_directory = "Images/";
	final String[] filesName ={"Image1.png","Image2.png","Image1.png"};
	public File getFile(int num){
		return new File(src_directory+filesName[num]);
	}
	
	public int size(){
		return filesName.length;
	}
}
