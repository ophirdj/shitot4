package XML;

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

import votersList.IVoterData;

public class BackupVotersListToXMLFile {
	/**
	 * creates a raw XML file to save votersList in it 
	 * @param fileName: the wanted file name
	 */
	public static void createEmptyVotersListXMLFile(String fileName) {
 
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
		//StreamResult result = new StreamResult(new File("file.xml"));
		StreamResult result = new StreamResult(new File(fileName));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
	

	/**
	 * adds a voter to the XML file that his name is given as parameter
	 * @param givenVoter: the voter needed to be added to the XML file
	 * @param fileName: the name of the XML file
	 */
	public static void addVoterToXMLFile(IVoterData givenVoter, String fileName){
		try {
			//File file = new File("file.xml");
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
			Element newVoter = doc.createElement("voter");
			root.appendChild(newVoter);
	 
			// id
			Element idElement = doc.createElement("id");
			idElement.appendChild(doc.createTextNode(Integer.toString(givenVoter.getId())));
			newVoter.appendChild(idElement);
			
			// identified
			Element identifiedElement = doc.createElement("identified");
			identifiedElement.appendChild(doc.createTextNode(Boolean.toString(givenVoter.isIdentified())));
			newVoter.appendChild(identifiedElement);
			
			// voted
			Element votedElement = doc.createElement("voted");
			votedElement.appendChild(doc.createTextNode(Boolean.toString(givenVoter.hasVoted())));
			newVoter.appendChild(votedElement);
			
			
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
			//f0 = new FileOutputStream("file.xml");
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
