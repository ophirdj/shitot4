package unitTests.mainframe;

import fileHandler.factories.IReadSuppliedXMLFactory;

/**
 * this stub factory returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class ReadSuppliedXMLStubFactory implements IReadSuppliedXMLFactory {

	/**
	 * where we hold the ReadSuppliedXMLStub that this factory repeatedly returns 
	 */
	ReadSuppliedXMLStub readSuppliedXMLStub =  new ReadSuppliedXMLStub();
	
	/**
	 * makes this factory to return a brand new MainframeWindowStub object instead of the same one
	 */
	public void restart(){
		this.readSuppliedXMLStub = new ReadSuppliedXMLStub();
	}
	
	/**
	 * 
	 * @return get the created ReadSuppliedXMLStub
	 */
	public ReadSuppliedXMLStub getReadSuppliedXMLStub(){
		return this.readSuppliedXMLStub;
	}
	
	@Override
	public ReadSuppliedXMLStub createInstance() {
		return this.readSuppliedXMLStub;
	}

}
