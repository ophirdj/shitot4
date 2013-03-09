package practiceStation.guides;

import java.io.File;

/**
 * List of images to show in guide
 * @author Ziv Ronen
 *
 */
public interface IListImages {
	
	/**
	 * Get image number <num>
	 * @param num the index of the image to show
	 * @return file that contain the image
	 */
	public File getFile(int num);

	/**
	 * Get the amount of images in the list
	 * @return the amount of images in the list
	 */
	public int size();
	
}
