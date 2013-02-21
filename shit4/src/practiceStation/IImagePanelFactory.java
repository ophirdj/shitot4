package practiceStation;


import java.util.Map;

import javax.swing.JPanel;

import dictionaries.Languages;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.StationPanel;


public interface IImagePanelFactory {
	
	/**
	 * 
	 * @param images: map from languages to images for show
	 * @param caller: the caller station
	 * @return a new object implement ImagePanel
	 */
	IImagePanel createInstance(Map<Languages,IListImages> images, StationPanel caller);
}
