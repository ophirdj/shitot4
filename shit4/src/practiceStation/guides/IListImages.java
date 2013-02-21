package practiceStation.guides;

import java.io.File;

public interface IListImages {
	
	/**
	 * 
	 * @param num: the index of the image to show
	 * @return file that contain the image
	 */
	public File getFile(int num);

	/**
	 * 
	 * @return the amount of images in the list
	 */
	public int size();
	
}
