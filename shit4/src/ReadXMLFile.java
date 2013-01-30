import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
 
public class ReadXMLFile {
 
   public static ArrayList<Integer> readXMLVotersList() {
 
    try {
 
    	SAXParserFactory factory = SAXParserFactory.newInstance();
    	SAXParser saxParser = factory.newSAXParser();
 
    	DefaultHandler handler = new VotersListHandler();
 
    	saxParser.parse("voters.xml", handler);
    	
    	ArrayList<Integer> res = ((VotersListHandler)handler).getVotersIdList();
    	
    	return res;
 
     } catch (Exception e) {
       e.printStackTrace();
       return new ArrayList<Integer>();
     }
 
   }
   
   
   
   
   public static ArrayList<Party> readXMLvotingRecords() {
	   
	    try {
	 
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	 
	    	DefaultHandler handler = new VotingRecordsHandler();
	 
	    	saxParser.parse("votingRecords.xml", handler);
	    	
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
 
}












