package unitTests.practiceStation;

import java.util.NoSuchElementException;
import java.util.Queue;

import global.dictionaries.Languages;
import practiceStation.guides.IImagePanel;

public class ImagePanelStub implements IImagePanel {

	private TestEnvironment testEnviroment;
	private Queue<ShowFirstImageComponent> showFirstImageQueue;
	private Queue<ImagePanelRetireComponent> retireQueue;
	private Thread showThread;
	
	public ImagePanelStub(TestEnvironment testEnviroment,
			Queue<ShowFirstImageComponent> showFirstImageQueue,
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

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.ImagePanel_retire;
		}
	}
	
	@Override
	public void retire() {
		testEnviroment.checkCalling(PracticeTestFunction.ImagePanel_retire);
		if(showThread != null){
			showThread.interrupt();
		}
		try{
			retireQueue.remove();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
		
	}

	
	public static class ShowFirstImageComponent{
		private Languages shouldBeLanguage;
		
		public ShowFirstImageComponent(Languages shouldBeLanguage) {
			this.shouldBeLanguage = shouldBeLanguage;
		}
		
		public void checkParameters(Languages language) throws InterruptedException{
			language.equals(shouldBeLanguage);
		}
		
		@Override
		public String toString() {
			return "imagePanel.showFirstImage("+shouldBeLanguage+")";
		}

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.ImagePanel_showGuide;
		}
	}
	
	public static class ShowFirstImageLongComponent extends ShowFirstImageComponent{
		
		private long milliSeconds2Wait;
		
		public ShowFirstImageLongComponent(Languages language, long milliSeconds2Wait) {
			super(language);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public void checkParameters(Languages language) throws InterruptedException{
			super.checkParameters(language);
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " +super.toString();
		}
	}
	
	public static class ShowFirstImageTooLongComponent extends ShowFirstImageComponent{
		
		private long milliSeconds2Wait;
		
		public ShowFirstImageTooLongComponent(Languages language ,long milliSeconds2Wait) {
			super(language);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public void checkParameters(Languages language) throws InterruptedException{
			super.checkParameters(language);
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
	public void showGuide(Languages language) {
		testEnviroment.checkCalling(PracticeTestFunction.ImagePanel_showGuide);
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
