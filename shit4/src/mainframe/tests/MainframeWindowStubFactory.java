package mainframe.tests;

import mainframe.factories.IMainframeWindowFactory;
import mainframe.gui.IMainframeWindow;
import mainframe.logic.IMainframe;

/**
 * 
 * this stub factory returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class MainframeWindowStubFactory implements IMainframeWindowFactory {

	/**
	 * where we hold the MainframeWindowStub that this factory repeatedly returns 
	 */
	MainframeWindowStub mws = new MainframeWindowStub();
	
	@Override
	public IMainframeWindow createInstance(IMainframe callerStation) {
		return mws;
	}
	
	/**
	 * makes this factory to return a brand new MainframeWindowStub object instead of the same one
	 */
	public void restart(){
		this.mws = new MainframeWindowStub();
	}
	
	/**
	 * get the created MainframeWindowStub
	 * @return the created MainframeWindowStub
	 */
	public MainframeWindowStub getCreatedMainframeWindowStub(){
		return this.mws;
	}
	
	

}
