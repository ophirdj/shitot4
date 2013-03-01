package global.tests;

import global.dictionaries.Languages;
import global.gui.StationPanel;
import practiceStation.guides.IImagePanel;

public class ImagePanelStub implements IImagePanel{

	private boolean showGuide = false;

	public ImagePanelStub(StationPanel caller) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrev() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showNextImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPrevImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFirstImage(Languages language) {
		showGuide = true;
	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub
		
	}

	public boolean hasShowGuide() {
		return showGuide ;
	}

}
