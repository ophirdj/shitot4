package practiceStation.guides;

import global.dictionaries.Languages;

public interface IImagePanel {
	
	/**
	 * 
	 * @return true iff the current image is not the final image
	 */
	public boolean hasNext();
	
	/**
	 * 
	 * @return true iff the current image is not the firsts image
	 */
	public boolean hasPrev();
	
	/**
	 * show next image (if exist)
	 */
	public void showNextImage();
	
	/**
	 * show previous image (if exist)
	 */
	public void showPrevImage();
	
	/**
	 * show first image (if exist)
	 * @param language: the language of the guide
	 */
	public void showFirstImage(Languages language);
	
	/**
	 * return to caller station
	 */
	public void retire();
	
}
