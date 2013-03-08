package integrationTests.practiceStationChoosingList;

import org.junit.*;

import global.dictionaries.Languages;
import practiceStation.guides.IImagePanel;

public class ImagePanelStub implements IImagePanel{

	private long failTime;
	private Languages language;
	private int timePassed = Integer.MAX_VALUE;
	private Thread thread;
	private int guideShowedTimes = 0;

	public ImagePanelStub(long failTime) {
		this.failTime = failTime;
	}
	
	@Override
	public boolean hasNext() {
		Assert.fail();
		return false;
	}

	@Override
	public boolean hasPrev() {
		Assert.fail();
		return false;
	}
	
	/**
	 * Set the expected language to the given one.
	 * @param language: The expected language from now. 
	 */
	public void setLanguage(Languages language){
		this.language = language;
	}
	
	/**
	 * Set the number of time that showGuide end normally
	 * @param amount: The given amount.
	 */
	public void setTimePassed(int amount){
		this.timePassed = amount;
	}
	
	/**
	 * Assert that the guide was showed the needed time.
	 * @param neededTime
	 */
	public void assertGuideShowedNeededTime(int neededTime){
		Assert.assertEquals(neededTime, guideShowedTimes);
	}
	
	/**
	 * Reset the counter of times guide was showed.
	 */
	public void guideShowedReset(){
		guideShowedTimes = 0;
	}

	@Override
	public void retire() {
		if(thread != null) thread.interrupt();
	}

	@Override
	public void showGuide(Languages language) {
		Assert.assertEquals(this.language, language);
		guideShowedTimes++;
		if(timePassed > 0){
			timePassed--;
		}
		else{
			thread = Thread.currentThread();
			try {
				
				Thread.sleep(failTime);
				Assert.fail();
			} catch (InterruptedException e) {
				thread = null;
			}
		}
	}

	@Override
	public void showNextImage() {
		Assert.fail();
	}

	@Override
	public void showPrevImage() {
		Assert.fail();
	}

}
