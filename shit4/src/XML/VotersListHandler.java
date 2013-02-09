package XML;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class VotersListHandler extends DefaultHandler {

	private boolean bid = false;
	private ArrayList<Integer> votersIdList = new ArrayList<Integer>();	
	

	public void startElement(String uri, String localName,String qName, 
	            Attributes attributes) throws SAXException {

		System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("ID")) {
			bid = true;
		}

	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException {

		System.out.println("End Element :" + qName);

	}

	public void characters(char ch[], int start, int length) throws SAXException {

		if (bid) {
			System.out.println("id : " + new String(ch, start, length));
			votersIdList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
			}
			bid = false;
	}
	
	public ArrayList<Integer> getVotersIdList(){
		return votersIdList;
	}

}