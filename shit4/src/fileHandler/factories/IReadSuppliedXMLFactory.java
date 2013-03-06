package fileHandler.factories;

import fileHandler.logic.IReadSuppliedXML;

/**
 * the interface of the factory of IReadSuppliedXML
 * @author Emil
 *
 */
public interface IReadSuppliedXMLFactory {

	/**
	 * 
	 * @return new object that implement IReadSuppliedXML
	 */
	IReadSuppliedXML createInstance();
}
