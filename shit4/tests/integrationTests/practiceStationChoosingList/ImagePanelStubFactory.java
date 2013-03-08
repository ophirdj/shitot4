package integrationTests.practiceStationChoosingList;

import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;

public class ImagePanelStubFactory implements IImagePanelFactory {

	private long failTime;
	private IntegrationTest test;

	public ImagePanelStubFactory(IntegrationTest test, long failTime) {
		this.failTime = failTime;
		this.test = test;
	}

	@Override
	public IImagePanel createInstance(Map<Languages, IListImages> images,
			StationPanel caller) {
		ImagePanelStub stub = new ImagePanelStub(failTime);
		test.setImagePanelStub(stub);
		return stub;
	}

}
