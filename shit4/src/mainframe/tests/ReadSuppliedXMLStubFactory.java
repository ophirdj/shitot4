package mainframe.tests;

import fileHandler.factories.IReadSuppliedXMLFactory;
import fileHandler.logic.IReadSuppliedXML;

public class ReadSuppliedXMLStubFactory implements IReadSuppliedXMLFactory {

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
