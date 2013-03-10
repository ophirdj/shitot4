package practiceStation.factories;


import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;

import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;


public interface IImagePanelFactory {
	
	/**
	 * 
	 * @param images map from languages to images for show
	 * @param caller the caller station
	 * @return a new object implement ImagePanel
	 */
	IImagePanel createInstance(Map<Languages,IListImages> images, StationPanel caller);
}
