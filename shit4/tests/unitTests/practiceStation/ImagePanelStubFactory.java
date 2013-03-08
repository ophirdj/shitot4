package unitTests.practiceStation;

import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;
import java.util.Queue;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;
import unitTests.practiceStation.ImagePanelStub.ImagePanelRetireComponent;
import unitTests.practiceStation.ImagePanelStub.ShowFirstImageComponent;

public class ImagePanelStubFactory implements IImagePanelFactory{
	
	private TestEnvironment testEnviroment;
	private Queue<ShowFirstImageComponent> showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> retireQueue;
	
	public ImagePanelStubFactory(
			TestEnvironment testEnviroment,
			Queue<ShowFirstImageComponent> showFirstImageQueue,
			Queue<ImagePanelRetireComponent> retireQueue) {
		this.testEnviroment = testEnviroment;
		this.showFirstImageQueue = showFirstImageQueue;
		this.retireQueue = retireQueue;
	}
	
	@Override
	public IImagePanel createInstance(Map<Languages, IListImages> images,
			StationPanel caller) {
		for(Languages language : Languages.values()){
			assert(images.containsKey(language));
		}
		return new ImagePanelStub(testEnviroment,showFirstImageQueue,retireQueue);
	}
}
