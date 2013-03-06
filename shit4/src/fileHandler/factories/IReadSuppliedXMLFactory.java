package fileHandler.factories;

import fileHandler.logic.IReadSuppliedXML;

public interface IReadSuppliedXMLFactory {

	/**
	 * 
	 * @return new object that implement IReadSuppliedXML
	 */
	IReadSuppliedXML createInstance();
}
