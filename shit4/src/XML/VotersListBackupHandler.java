package XML;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import votersList.IVoterData.AlreadyIdentified;
import votersList.IVoterData.Unidentified;
import votersList.VoterData;

public class VotersListBackupHandler extends DefaultHandler {
		private boolean bid = false;
		private boolean bidentified = false;
		private boolean bvoted = false;
		
		private int id;
		private boolean identified;
		private boolean voted;
		
		private ArrayList<VoterData> votersList = new ArrayList<VoterData>();	
		

		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {

			System.out.println("Start Element :" + qName);


			if (qName.equalsIgnoreCase("ID")) {
				bid = true;
			}
			
			if (qName.equalsIgnoreCase("IDENTIFIED")) {
				bidentified = true;
			}
			
			if (qName.equalsIgnoreCase("VOTED")) {
				bvoted = true;
			}

		}

		public void endElement(String uri, String localName,
			String qName) throws SAXException {

			System.out.println("End Element :" + qName);

		}

		public void characters(char ch[], int start, int length) throws SAXException {

			if (bid) {
				System.out.println("id : " + new String(ch, start, length));
				//votersList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				id = Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", ""));
				bid = false;
			}
			
			if (bidentified) {
				System.out.println("identified : " + new String(ch, start, length));
				identified = Boolean.valueOf(new String(ch, start, length));
				bidentified = false;
			}
			
			if (bvoted) {
				System.out.println("voted : " + new String(ch, start, length));
				//votersList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				voted = Boolean.valueOf(new String(ch, start, length));
				VoterData newVoter = new VoterData(id);
				if(identified){
					try {
						newVoter.markIdentified();
					} catch (AlreadyIdentified e) {
						System.out.println("We have a problem here!");
						e.printStackTrace();
					}
				}
				if(voted){
					try {
						newVoter.markVoted();
					} catch (Unidentified e) {
						System.out.println("We have a problem here!");
						e.printStackTrace();
					}
				}
				
				votersList.add(newVoter); //we add a new voter to the returned list
	
				bvoted = false;
			}
				
		}
		
		public ArrayList<VoterData> getVotersList(){
			return votersList;
		}
}
