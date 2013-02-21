package fileHandler.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import votersList.model.IVoterData;
import votersList.model.IVotersList;
import votersList.model.IVoterData.AlreadyIdentified;
import votersList.model.IVoterData.Unidentified;
 
public class ReadXMLFile {
	
	/*
	 * the needed factories
	 */
	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory votersListFactory;
	private IVoterDataFactory voterDataFactory;
	
	/*
	 * these strings will hold the path to all the XML files
	 */
	/*private String suppliedVotersListFile;
	private String suppliedPartiesListFile;
	private String unregisteredVotersFile;
	private String backupedVotersListFile;
	private String backupedPartiesListFile;*/
	
	public ReadXMLFile(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory/*,
			String suppliedVotersListFile, String suppliedPartiesListFile,
			String unregisteredVotersFile, String backupedVotersListFile,
			String backupedPartiesListFile*/){
		/*
		 * initializing the factories
		 */
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;
		
		/*
		 * initializing the XML files names
		 */
		/*this.suppliedVotersListFile = suppliedVotersListFile;
		this.suppliedPartiesListFile = suppliedPartiesListFile;
		this.unregisteredVotersFile = unregisteredVotersFile;
		this.backupedVotersListFile = backupedVotersListFile;
		this.backupedPartiesListFile = backupedPartiesListFile;*/
	}
	
	public IVotersList readSuppliedVotersListXML(String fileName) {
 
		IVotersList res = null;
		
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	SuppliedVotersListHandler handler = this.new SuppliedVotersListHandler();
	 
	    	//saxParser.parse("voters.xml", handler);
	    	saxParser.parse(fileName, handler);
	    	
	    	ArrayList<Integer> votersIdList = handler.getVotersIdList();
	    	
	    	res = this.votersListFactory.createInstance();
	    	
	    	for (Integer id : votersIdList) {
				IVoterData voter = voterDataFactory.createInstance(id);
				res.addVoter(voter);
				
			}

	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	    
	    return res;
		
		
 
   }
   
   
   
   /***
    * 
    * @param fileName: the name of the file where the voting records are stored
    * @return
    */
   public IPartiesList readSuppliedVotingRecordsXML(String fileName) {
	   
	   IPartiesList res = null;
	   
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	SuppliedPartiesListHandler handler = this.new SuppliedPartiesListHandler();
	 
	    	saxParser.parse(fileName, handler);
	    	
	    	res = handler.getVotingRecordsList();

	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	    
	    
	    return res;
	 
   }
   
   
   
   public IVotersList readUnregisteredVotersXMLFile(String fileName) {
	   
	   	IVotersList res = null;
	      
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	UnregisteredVotersHandler handler = new UnregisteredVotersHandler();
	 
	    	saxParser.parse(fileName, handler);
	    	
	    	res = handler.getUnregisteredVotersIdList();    	
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	    
	 return res;
	 
  }
   
   public IPartiesList readXMLPartiesListBackup(String fileName){
	   
	   	IPartiesList res = null;

	   	try {
			 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	PartiesListBackupHandler handler = new PartiesListBackupHandler();
	 
	    	saxParser.parse(fileName, handler);
	    	
	    	res = handler.getPariesList();
	    	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	   
	   return res;
	   
   }
   
   
   
   public IVotersList readXMLVotersListBackup(String fileName){
	   	IVotersList res = null;
	   
	   	try {
			 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	VotersListBackupHandler handler = new VotersListBackupHandler();
	 
	    	saxParser.parse(fileName, handler);
	    	
	    	res = handler.getVotersList();
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	   
	   return res;
   }
   
   
   
   
   
   
   
   /***
	 * ============================================================================================
	 * this class is implementing handler needed in order to parse the supplied voters list XML file
	 * @author Emil
	 *
	 */
   private class SuppliedVotersListHandler extends DefaultHandler {

		private boolean bid = false;
		private ArrayList<Integer> votersIdList = new ArrayList<Integer>();	
		

		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {

			//System.out.println("Start Element :" + qName);

			if (qName.equalsIgnoreCase("ID")) {
				bid = true;
			}

		}

		public void endElement(String uri, String localName,
			String qName) throws SAXException {

			//System.out.println("End Element :" + qName);

		}

		public void characters(char ch[], int start, int length) throws SAXException {

			if (bid) {
				//System.out.println("id : " + new String(ch, start, length));
				votersIdList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				}
				bid = false;
		}
		
		public ArrayList<Integer> getVotersIdList(){
			return votersIdList;
		}

	}
   
   
   
   
   
   

   /***
	 * ============================================================================================
	 * this class is implementing handler needed in order to parse the supplied parties list XML file
	 * @author Emil
	 *
	 */
   private class SuppliedPartiesListHandler extends DefaultHandler {
		
		private boolean bRecordName = false;
		private boolean bBallotLetters = false;
		private ArrayList<IParty> votingRecordsList = new ArrayList<IParty>();
		private String recordName;//will hold the recordName until we reach the tag ballotLetters
		private String ballotLetters;
		
		
		public void startElement(String uri, String localName,String qName, 
		           Attributes attributes) throws SAXException {
		
			//System.out.println("Start Element :" + qName);
		
			if (qName.equalsIgnoreCase("recordName")) {
				bRecordName = true;
			}
		
			if (qName.equalsIgnoreCase("ballotLetters")) {
				bBallotLetters = true;
			}
		
		}
		
		public void endElement(String uri, String localName,
			String qName) throws SAXException {
		
			//System.out.println("End Element :" + qName);
		
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
		
			if (bRecordName) {
				//System.out.println("recordName : " + new String(ch, start, length));
				recordName = new String(ch, start, length);
				bRecordName = false;
			}
			if(bBallotLetters){
				//System.out.println("ballotLetters : " + new String(ch, start, length));
				ballotLetters = new String(ch, start, length);
				IParty newParty = partyFactory.createInstance(recordName, ballotLetters);
				votingRecordsList.add(newParty);
				bBallotLetters = false;
			}
			
		}
		
		public IPartiesList getVotingRecordsList(){

			//now we shall remove redundant records
	    	Map<String, Integer> recordsCounter = new HashMap<String, Integer>();
	    	
	    	for (IParty party : this.votingRecordsList) {
	    		if(!recordsCounter.containsKey(party.getSymbol())){
	    			recordsCounter.put(party.getSymbol(), 1);
	    		} else{
	    			int oldCounter = recordsCounter.get(party.getSymbol());
	    			recordsCounter.remove(party.getSymbol());
	    			recordsCounter.put(party.getSymbol(), oldCounter+1);
	    		}
			}
	    	
	    	for (Iterator<IParty> iterator = this.votingRecordsList.iterator(); iterator.hasNext();) {
				IParty party = iterator.next();
				while(recordsCounter.get(party.getSymbol())>1){
					int oldCounter = recordsCounter.get(party.getSymbol());
	    			recordsCounter.remove(party.getSymbol());
	    			recordsCounter.put(party.getSymbol(), oldCounter-1);
	    			iterator.remove();
				}
			}  	
	    	
	    	//now we will copy all the content of 'this.votingRecordsList' to 'res' of type IPartiesList
	    	IPartiesList res = partiesListFactory.createInstance();
	    	for (IParty party : this.votingRecordsList) {
				res.addParty(party);
			}

	    	return res;
			
		}	

   }
   

   
   
   
   
   /***
	 * ============================================================================================
	 * this class is implementing handler needed in order to parse the unregistered voters XML file
	 * @author Emil
	 *
	 */
   private class UnregisteredVotersHandler extends DefaultHandler {

		private boolean bid = false;
		private IVotersList unregisteredVotersList = votersListFactory.createInstance();	
		
	
		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {
	
			//System.out.println("Start Element :" + qName);
	
			if (qName.equalsIgnoreCase("ID")) {
				bid = true;
			}
	
		}
	
		public void endElement(String uri, String localName,
			String qName) throws SAXException {
	
			//System.out.println("End Element :" + qName);
	
		}
	
		public void characters(char ch[], int start, int length) throws SAXException {
	
			if (bid) {
				//System.out.println("id : " + new String(ch, start, length));
				IVoterData voter = voterDataFactory.createInstance(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				unregisteredVotersList.addVoter(voter);
				bid = false;
				}
				
		}
		
		public IVotersList getUnregisteredVotersIdList(){
			return unregisteredVotersList;
		}

}
  
   
   
   
   
   /***
	 * ============================================================================================
	 * this class is implementing handler needed in order to parse the backuped Voters List XML file
	 * @author Emil
	 *
	 */
   private class VotersListBackupHandler extends DefaultHandler {
		private boolean bid = false;
		private boolean bidentified = false;
		private boolean bvoted = false;
		
		private int id;
		private boolean identified;
		private boolean voted;
		
		private IVotersList votersList = votersListFactory.createInstance();	
		

		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {

			//System.out.println("Start Element :" + qName);


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

			//System.out.println("End Element :" + qName);

		}

		public void characters(char ch[], int start, int length) throws SAXException {

			if (bid) {
				//System.out.println("id : " + new String(ch, start, length));
				//votersList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				id = Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", ""));
				bid = false;
			}
			
			if (bidentified) {
				//System.out.println("identified : " + new String(ch, start, length));
				identified = Boolean.valueOf(new String(ch, start, length));
				bidentified = false;
			}
			
			if (bvoted) {
				//System.out.println("voted : " + new String(ch, start, length));
				//votersList.add(Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", "")));
				voted = Boolean.valueOf(new String(ch, start, length));
				
				
				IVoterData newVoter = voterDataFactory.createInstance(id);
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
				
				votersList.addVoter(newVoter); //we add a new voter to the returned list
	
				bvoted = false;
			}
				
		}
		
		public IVotersList getVotersList(){
			return votersList;
		}
   }
   
 
   
   
   
   
   /***
	 * ============================================================================================
	 * this class is implementing handler needed in order to parse the backuped Parties List XML file
	 * @author Emil
	 *
	 */
   public class PartiesListBackupHandler extends DefaultHandler{

		private boolean bRecordName = false;
		private boolean bBallotLetters = false;
		private boolean bvoteNumber = false;
		private IPartiesList votingRecordsList = partiesListFactory.createInstance();
		private String recordName;//will hold the recordName until we reach the tag voteNumber
		private String ballotLetters;//will hold the ballotLetters until we reach the tag voteNumber
		private int numberOfVotes;//will hold the number of votes
		
		

		public void startElement(String uri, String localName,String qName, 
		            Attributes attributes) throws SAXException {

			//System.out.println("Start Element :" + qName);

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

			//System.out.println("End Element :" + qName);

		}

		public void characters(char ch[], int start, int length) throws SAXException {

			if (bRecordName) {
				//System.out.println("recordName : " + new String(ch, start, length));
				recordName = new String(ch, start, length);
				bRecordName = false;
			}
			if(bBallotLetters){
				//System.out.println("ballotLetters : " + new String(ch, start, length));
				//votingRecordsList.add(new Party(recordName, new String(ch, start, length)) );
				ballotLetters = new String(ch, start, length);
				bBallotLetters = false;
			}
			if(bvoteNumber){
				//System.out.println("id : " + new String(ch, start, length));
				numberOfVotes = Integer.valueOf((new String(ch, start, length)).replaceAll("\\s", ""));
				
				
				
				votingRecordsList.addParty(partyFactory.createInstance(recordName, ballotLetters, numberOfVotes));
				bvoteNumber = false;
				}
			
		}
		
		public IPartiesList getPariesList(){
			return votingRecordsList;
		}
	
   }
   

   
 
}


