package XML;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import partiesList.Party;



public class VotingRecordsHandler extends
		DefaultHandler {
	

	private boolean bRecordName = false;
	private boolean bBallotLetters = false;
	private ArrayList<Party> votingRecordsList = new ArrayList<Party>();
	String recordName;//will hold the recordName until we reach the tag ballotLetters
	

	public void startElement(String uri, String localName,String qName, 
	            Attributes attributes) throws SAXException {

		System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("recordName")) {
			bRecordName = true;
		}

		if (qName.equalsIgnoreCase("ballotLetters")) {
			bBallotLetters = true;
		}

	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException {

		System.out.println("End Element :" + qName);

	}

	public void characters(char ch[], int start, int length) throws SAXException {

		if (bRecordName) {
			System.out.println("recordName : " + new String(ch, start, length));
			recordName = new String(ch, start, length);
			bRecordName = false;
		}
		if(bBallotLetters){
			System.out.println("ballotLetters : " + new String(ch, start, length));
			votingRecordsList.add(new Party(recordName, new String(ch, start, length)) );
			bBallotLetters = false;
		}
		
	}
	
	public ArrayList<Party> getVotingRecordsList(){
		return votingRecordsList;
	}
	
	

}
