package XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.Party;
import votersList.VoterData;

 
public class ReadXMLFile {
   public static ArrayList<Integer> readSuppliedVotersListXML(String fileName) {
 
    try {
 
    	SAXParserFactory factory = SAXParserFactory.newInstance();
    	SAXParser saxParser = factory.newSAXParser();
 
    	DefaultHandler handler = new VotersListHandler();
 
    	//saxParser.parse("voters.xml", handler);
    	saxParser.parse(fileName, handler);
    	
    	ArrayList<Integer> res = ((VotersListHandler)handler).getVotersIdList();
    	
    	return res;
 
     } catch (Exception e) {
       e.printStackTrace();
       return new ArrayList<Integer>();
     }
 
   }
   
   
   
   
   public static ArrayList<Party> readSuppliedVotingRecordsXML(String fileName) {
	   
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	DefaultHandler handler = new VotingRecordsHandler();
	 
	    	//saxParser.parse("votingRecords.xml", handler);
	    	saxParser.parse(fileName, handler);
	    	
	    	ArrayList<Party> res = ((VotingRecordsHandler)handler).getVotingRecordsList();
	    	
	    	//now we shall remove redundant records
	    	Map<String, Integer> recordsCounter = new HashMap<String, Integer>();
	    	
	    	for (Party party : res) {
	    		if(!recordsCounter.containsKey(party.getSymbol())){
	    			recordsCounter.put(party.getSymbol(), 1);
	    		} else{
	    			int oldCounter = recordsCounter.get(party.getSymbol());
	    			recordsCounter.remove(party.getSymbol());
	    			recordsCounter.put(party.getSymbol(), oldCounter+1);
	    		}
			}
	    	
	    	
	    	
	    	for (Iterator iterator = res.iterator(); iterator.hasNext();) {
				Party party = (Party) iterator.next();
				if(recordsCounter.get(party.getSymbol())>1){
					int oldCounter = recordsCounter.get(party.getSymbol());
	    			recordsCounter.remove(party.getSymbol());
	    			recordsCounter.put(party.getSymbol(), oldCounter-1);
	    			iterator.remove();
				}
			}
	    	
	    	return res;
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	       return new ArrayList<Party>();
	     }
	 
   }
   
   
   
   public static ArrayList<Integer> readUnregisteredVotersXMLFile(String fileName) {
	   
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	DefaultHandler handler = new UnregisteredVotersHandler();
	 
	    	//saxParser.parse("file.xml", handler);
	    	saxParser.parse(fileName, handler);
	    	
	    	ArrayList<Integer> res = ((UnregisteredVotersHandler)handler).getUnregisteredVotersIdList();
	    	
	    	return res;
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	       return new ArrayList<Integer>();
	     }
	 
  }
   
   public static ArrayList<Party> readXMLPartiesListBackup(String fileName){
	   try {
			 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	PartiesListBackupHandler handler = new PartiesListBackupHandler();
	 
	    	//saxParser.parse("file.xml", handler);
	    	saxParser.parse(fileName, handler);
	    	
	    	ArrayList<Party> res = handler.getPariesList();
	    	
	    	return res;
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	       return new ArrayList<Party>();
	     }
   }
   
   
   
   public static ArrayList<VoterData> readXMLVotersListBackup(String fileName){
	   try {
			 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	VotersListBackupHandler handler = new VotersListBackupHandler();
	 
	    	//saxParser.parse("file.xml", handler);
	    	saxParser.parse(fileName, handler);
	    	
	    	ArrayList<VoterData> res = handler.getVotersList();
	    	
	    	return res;
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	       return new ArrayList<VoterData>();
	     }
   }
 
}












