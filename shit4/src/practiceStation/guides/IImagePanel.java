package practiceStation.guides;

import global.dictionaries.Languages;

/**
 * Interface to Show the how-to-vote-guide in practice station
 * @author Ophir De Jager
 *
 */
public interface IImagePanel {
	
	/**
	 * Check if guide panel has a previous panel
	 * @return true if the current image is not the final image
	 */
	public boolean hasNext();
	
	/**
	 * Check if guide panel has a next panel
	 * @return true if the current image is not the firsts image
	 */
	public boolean hasPrev();
	
	/**
	 * Show next image of guide (if exist)
	 */
	public void showNextImage();
	
	/**
	 * Show previous image of guide (if exist)
	 */
	public void showPrevImage();
	
	/**
	 * Show first image of guide (if exist)
	 * @param language the language of the guide
	 */
	public void showGuide(Languages language);
	
	/**
	 * Return to practice station
	 */
	public void retire();
	
}
