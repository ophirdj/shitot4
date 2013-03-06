package mainframe.tests;

import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IReadSuppliedXML;

/**
 * this stubs returns the same object - and we can make it return a brand new
 * object by invoking restart()
 * @author Emil
 *
 */
public class ReadSuppliedXMLStubFactory implements IReadSuppliedXMLFactory {

	/**
	 * where we hold the ReadSuppliedXMLStub that this factory repeatedly returns 
	 */
	ReadSuppliedXMLStub readSuppliedXMLStub =  new ReadSuppliedXMLStub();
	
	public void restart(){
		this.readSuppliedXMLStub = new ReadSuppliedXMLStub();
	}
	
	public ReadSuppliedXMLStub getReadSuppliedXMLStub(){
		return this.readSuppliedXMLStub;
	}
	
	@Override
	public ReadSuppliedXMLStub createInstance() {
		return this.readSuppliedXMLStub;
	}

}
