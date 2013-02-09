package XML;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import partiesList.Party;

public class PartiesListBackupHandler extends DefaultHandler{

		private boolean bRecordName = false;
		private boolean bBallotLetters = false;
		private boolean bvoteNumber = false;
		private ArrayList<Party> votingRecordsList = new ArrayList<Party>();
		String recordName;//will hold the recordName until we reach the tag voteNumber
		String ballotLetters;//will hold the ballotLetters until we reach the tag voteNumber
		

		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {

			System.out.println("Start Element :" + qName);

			if (qName.equalsIgnoreCase("recordName")) {
				bRecordName = true;
			}

			if (qName.equalsIgnoreCase("ballotLetters")) {
				bBallotLetters = true;
			}
			
			if (qName.equalsIgnoreCase("voteNumber")) {
				bvoteNumber = true;
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
				//votingRecordsList.add(new Party(recordName, new String(ch, start, length)) );
				ballotLetters = new String(ch, start, length);
				bBallotLetters = false;
			}
			if(bvoteNumber){
				System.out.println("id : " + new String(ch, start, length));
				int numberOfVotes = Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", ""));
				votingRecordsList.add(new Party(recordName, ballotLetters, numberOfVotes));
				System.out.println("added!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				bvoteNumber = false;
				}
			
		}
		
		public ArrayList<Party> getPariesList(){
			return votingRecordsList;
		}
	
}
