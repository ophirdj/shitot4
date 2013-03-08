package acceptanceTest;

import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;

public class ImagePanelStubFactory implements IImagePanelFactory {
	
	private AcceptanceTest test;
	
	public ImagePanelStubFactory(AcceptanceTest test) {
		this.test = test;
	}

	@Override
	public IImagePanel createInstance(Map<Languages, IListImages> images,
			StationPanel caller) {
		ImagePanelStub stub = new ImagePanelStub(caller);
		test.addImagePanelStub(stub, caller);
		return stub;
	}

}
