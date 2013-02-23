package practiceStation.tests;

import java.util.NoSuchElementException;
import java.util.Queue;

import global.dictionaries.Languages;
import practiceStation.guides.IImagePanel;

public class PracticeTest_ImagePanelStub implements IImagePanel {

	private PracticeStationTestEnvironment testEnviroment;
	private Queue<showFirstImageComponent> showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> retireQueue;
	private Thread showThread;
	
	public PracticeTest_ImagePanelStub(PracticeStationTestEnvironment testEnviroment,
			Queue<showFirstImageComponent> showFirstImageQueue,
			Queue<ImagePanelRetireComponent> retireQueue) {

		this.testEnviroment = testEnviroment;
		this.showFirstImageQueue = showFirstImageQueue;
		this.retireQueue = retireQueue;
	}
	
	public static class ImagePanelRetireComponent{
		@Override
		public String toString() {
			return "imagePanel.retire()";
		}
	}
	
	@Override
	public void retire() {
		PracticeStationTestEnvironment.assertTrue(testEnviroment.checkCalling(PracticeTestFunction.ImagePanel_retire));
		if(showThread != null){
			showThread.interrupt();
		}
		try{
			retireQueue.remove();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}

	
	public static class showFirstImageComponent{
		private Languages shouldBeLanguage;
		
		public showFirstImageComponent(Languages shouldBeLanguage) {
			this.shouldBeLanguage = shouldBeLanguage;
		}
		
		public void checkParameters(Languages language) throws InterruptedException{
			PracticeStationTestEnvironment.assertTrue(language.equals(shouldBeLanguage));
		}
		
		@Override
		public String toString() {
			return "imagePanel.showFirstImage("+shouldBeLanguage+")";
		}
	}
	
	public static class ShowFirstImageLongComponent extends showFirstImageComponent{
		
		private long milliSeconds2Wait;
		
		public ShowFirstImageLongComponent(Languages language, long milliSeconds2Wait) {
			super(language);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public void checkParameters(Languages language) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			super.checkParameters(language);
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " +super.toString();
		}
	}
	
	public static class ShowFirstImageTooLongComponent extends showFirstImageComponent{
		
		private long milliSeconds2Wait;
		
		public ShowFirstImageTooLongComponent(long milliSeconds2Wait) {
			super(null);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public void checkParameters(Languages language) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
				throw new AssertionError();
			}catch (InterruptedException e) {
				//should reach here
				throw e;
			}
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " +super.toString() + "shouldn't return. ";
		}
	}
	
	@Override
	public void showFirstImage(Languages language) {
		PracticeStationTestEnvironment.assertTrue(testEnviroment.checkCalling(PracticeTestFunction.ImagePanel_showFirstImage));
		showThread = Thread.currentThread();
		try{
			showFirstImageQueue.remove().checkParameters(language);
		}catch (InterruptedException e) {
			
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
		showThread = null;
	}
	
	
	
	@Override
	public boolean hasNext() {
		throw new AssertionError();
	}

	@Override
	public boolean hasPrev() {
		throw new AssertionError();
	}

	@Override
	public void showNextImage() {
		throw new AssertionError();
	}

	@Override
	public void showPrevImage() {
		throw new AssertionError();
	}

}
