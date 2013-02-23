package practiceStation.tests;

import global.dictionaries.Languages;
import global.gui.StationPanel;

import java.util.Map;
import java.util.Queue;

import practiceStation.factories.IImagePanelFactory;
import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;
import practiceStation.tests.PracticeTest_ImagePanelStub.ImagePanelRetireComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.showFirstImageComponent;

public class PracticeTest_ImagePanelStubFactory implements IImagePanelFactory{
	
	private PracticeStationTestEnvironment testEnviroment;
	private Queue<showFirstImageComponent> showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> retireQueue;
	
	public PracticeTest_ImagePanelStubFactory(
			PracticeStationTestEnvironment testEnviroment,
			Queue<showFirstImageComponent> showFirstImageQueue,
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
