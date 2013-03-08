package global.tests;

import global.dictionaries.Languages;
import global.gui.StationPanel;
import practiceStation.guides.IImagePanel;

public class ImagePanelStub implements IImagePanel{

	private boolean showGuide = false;

	public ImagePanelStub(StationPanel caller) {
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public boolean hasPrev() {
		return false;
	}

	@Override
	public void showNextImage() {
		
	}

	@Override
	public void showPrevImage() {
		
	}

	@Override
	public void showGuide(Languages language) {
		showGuide = true;
	}

	@Override
	public void retire() {
		
	}

	public boolean hasShowGuide() {
		return showGuide ;
	}

}
