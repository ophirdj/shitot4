package XML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import partiesList.IParty;

public class BackupPartiesListToXMLFile {
	
	
	/**
	 * creates a raw XML file to save partiesList in it 
	 * @param fileName: the wanted file name
	 */
	public static void createEmptyPartiesListXMLFile(String fileName) {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("votingRecords");
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
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
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
	 * adds a party to the XML file that his name is given as parameter
	 * @param givenParty: the party to add to the XML file
	 * @param fileName: the name of the XML file
	 */
	public static void addPartyToXMLFile(IParty givenParty, String fileName){
		try {
			//File file = new File("file.xml");
			File file = new File(fileName);
			//ByteArrayInputStream encXML = new ByteArrayInputStream();
			
			
			String xml = "";
			
			try {
		 
				BufferedReader in = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(file), "UTF8"));
		 
				String str;
		 
				while ((str = in.readLine()) != null) {
					xml+=str;
				}
		 
		                in.close();
			    } 
			    catch (UnsupportedEncodingException e) 
			    {
					System.out.println(e.getMessage());
			    } 
			    catch (IOException e) 
			    {
					System.out.println(e.getMessage());
			    }
			    catch (Exception e)
			    {
					System.out.println(e.getMessage());
			    }
			
			
			
			ByteArrayInputStream encXML = new  ByteArrayInputStream(xml.getBytes("UTF8"));
			
			
		 
			//Create instance of DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 
			//Get the DocumentBuilder
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
		 
			//Using existing XML Document
			Document doc = docBuilder.parse(encXML);
		 
			//create the root element
			Element root = doc.getDocumentElement();
	 
			
			// party elements
			Element newParty = doc.createElement("votingRecord");
			root.appendChild(newParty);
	 
			// recordName
			Element recordNameElement = doc.createElement("recordName");
			recordNameElement.appendChild(doc.createTextNode(givenParty.getName().toString()));
			newParty.appendChild(recordNameElement);
			
			// ballotLetters
			Element ballotLettersElement = doc.createElement("ballotLetters");
			ballotLettersElement.appendChild(doc.createTextNode(givenParty.getSymbol().toString()));
			newParty.appendChild(ballotLettersElement);
			
			// voteNumber
			Element voteNumberElement = doc.createElement("voteNumber");
			voteNumberElement.appendChild(doc.createTextNode(Integer.toString(givenParty.getVoteNumber())));
			newParty.appendChild(voteNumberElement);
			
			
			
			
			
			
			
			
			//the code below writes back the modified XML file with charSet = "UTF8"
			
			BufferedWriter xmlOutput =
					new BufferedWriter (
							new OutputStreamWriter(new FileOutputStream(fileName),"UTF8"));
			    
				// Write the Modified XML as usual
			    TransformerFactory tFactory =
			        TransformerFactory.newInstance();
			    Transformer transformer = tFactory.newTransformer();

			    DOMSource source = new DOMSource(doc);
			    StreamResult result = new StreamResult(xmlOutput);
			    transformer.transform(source, result);
			    xmlOutput.close();

			
			
			
			/*
			
			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
			
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
			buf = null;*/
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
