package fileHandler.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import votersList.model.IVoterData;

/**
 * this class is implementing saving of the unregistered voters in a separate file
 * @author Emil
 *
 */
public class WriteXMLFileUnregisteredVoters {
	
	/**
	 * the location of the XML file that saves all the unregistered voters
	 */
	private String unregisteredVotersFile;
	
	/**
	 * the constructor of WriteXMLFileUnregisteredVoters
	 * @param unregisteredVotersFile the location of the XML file that saves all the unregistered voters
	 */
	public WriteXMLFileUnregisteredVoters(String unregisteredVotersFile){
		this.unregisteredVotersFile = unregisteredVotersFile;
	}
	
	 
	/**
	 * creates an empty file for the unregistered voters
	 */
	public  synchronized void createEmptyUnregisteredVotersXMLFile() {
 
		String fileName = unregisteredVotersFile;
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("voters");
		doc.appendChild(rootElement);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fileName));
 
		transformer.transform(source, result);
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
	
	/**
	 * adds the voter id to the file of the unregistered voters
	 * @param givenVoter the unregistered voter
	 */
	public synchronized void addVoterToXMLFile(IVoterData givenVoter){
		
		String fileName = unregisteredVotersFile;
		
		try {
			
			Integer id = givenVoter.getId();
			
			File file = new File(fileName);
		 
			//Create instance of DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 
			//Get the DocumentBuilder
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
		 
			//Using existing XML Document
			Document doc = docBuilder.parse(file);
		 
			//create the root element
			Element root = doc.getDocumentElement();
	 
			
			// voter elements
			Element voter = doc.createElement("voter");
			root.appendChild(voter);
	 
			// id elements
			Element idElement = doc.createElement("id");
			idElement.appendChild(doc.createTextNode(id.toString()));
			voter.appendChild(idElement);

		 
			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
		 
	        //create string from xml tree
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(doc);
	        trans.transform(source, result);
	        String xmlString = sw.toString();
	 
	        OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(fileName);
			for(int i=0;i<buf .length;i++) {
			   f0.write(buf[i]);
			}
			f0.close();
			buf = null;
	     }
	     catch(SAXException e) {
	    	 e.printStackTrace();
	     }
	     catch(IOException e) {
	    	 e.printStackTrace();
	     }
	     catch(ParserConfigurationException e) {
	    	 e.printStackTrace();
	     }
	     catch(TransformerConfigurationException e) {
	    	 e.printStackTrace();
	     }
	     catch(TransformerException e) {
	    	 e.printStackTrace();
	     }
	}
}