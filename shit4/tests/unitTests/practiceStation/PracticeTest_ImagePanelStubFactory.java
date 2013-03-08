package unitTests.practiceStation;

import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;
import java.util.Queue;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ImagePanelRetireComponent;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ShowFirstImageComponent;

public class PracticeTest_ImagePanelStubFactory implements IImagePanelFactory{
	
	private PracticeStationTestEnvironment testEnviroment;
	private Queue<ShowFirstImageComponent> showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> retireQueue;
	
	public PracticeTest_ImagePanelStubFactory(
			PracticeStationTestEnvironment testEnviroment,
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
		return new PracticeTest_ImagePanelStub(testEnviroment,showFirstImageQueue,retireQueue);
	}
}
