 
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
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
 
public class WriteXMLFileUnregisteredVoters {
 
	/**
	 * creates an empty file for the unregistered voters
	 */
	public static void createEmptyUnregisteredVotersXMLFile() {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("voters");
		doc.appendChild(rootElement);
 
		
		/*// voter elements
		Element staff = doc.createElement("voter");
		rootElement.appendChild(staff);
 
		// name elements
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(""));
		staff.appendChild(name);
 
		// id elements
		Element id = doc.createElement("id");
		id.appendChild(doc.createTextNode(""));
		staff.appendChild(id);*/

 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("file.xml"));
 
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
	 * adds the voter id to the file of the unregistered voters
	 * @param id - the id of the unregistered voter
	 */
	public static void addVoterToXMLFile(Integer id){
		try {
			File file = new File("file.xml");
		 
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
	 
			/*// name elements
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(""));
			voter.appendChild(name);*/
	 
			// id elements
			Element idElement = doc.createElement("id");
			idElement.appendChild(doc.createTextNode(id.toString()));
			voter.appendChild(idElement);
			
			
			
			
			
			/*//create child element
			Element childElement = doc.createElement("number");
		 
		        //Add the attribute to the child
			childElement.setAttribute("id","3");
			root.appendChild(childElement);*/
		 
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
			f0 = new FileOutputStream("file.xml");
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