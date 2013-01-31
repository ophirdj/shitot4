import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.print.attribute.IntegerSyntax;


public class VotersList implements IVotersList {

	private List<VoterData> votersList;
	
	//= new ArrayList<>();
	
	public VotersList(){
		this.votersList = new ArrayList<VoterData>();
	}
	
	public void addVoter(VoterData toAdd){
		this.votersList.add(toAdd.copy());
	}
	
	/***
	 * merging the list of the object with the list of toMerge
	 * @param toMerge - the list to merge
	 */
	public void merge(VotersList toMerge){
		for (VoterData toMergeIter : toMerge.votersList) {
			boolean notAddToList = false;
			for (VoterData myIter : this.votersList) {
				if(myIter.getId() == toMergeIter.getId()){
					notAddToList = true;
				}
			}
			if(!notAddToList){
				this.votersList.add(toMergeIter.copy());
			}
		}
	}
	
	/**
	 * prints the state of the the VotersList object
	 */
	public void peep(){
		System.out.println("=========================");
		System.out.println("Peep of VoterList");
		System.out.println("=========================");
		int i = 1;
		for (VoterData iter : this.votersList) {
			System.out.println("~~~~~~~~~~~~~~~~");
			System.out.println(iter);
			System.out.println("~~~~~~~~~~~~~~~~");
		}
	}
	
	/**
	 * return the actual (not a clone) object of the requested voter
	 * @param id - the id of the requested voter
	 * @return the requested voter
	 * @throws Exception - if the voter id isn't in the list
	 */
	public VoterData findVoter(int id) throws Exception{
		for (VoterData iter : this.votersList) {
			if(iter.getId() == id){
				return iter;
			}
		}
		throw new Exception("Not Found");
	}
	
	public void replaceWith (VotersList toReplace){
		ArrayList<VoterData> newList = new ArrayList<VoterData>();
		for (VoterData voter : toReplace.votersList) {
			newList.add(voter.copy());
		}
		this.votersList = newList;
			
	}
	
	public VotersList copy(){
		VotersList res = new VotersList();
		for (VoterData voter : this.votersList) {
			res.votersList.add(voter);
		}
		return res;
	}

	@Override
	public Iterator<VoterData> iterator() {
		return this.votersList.iterator();
	}
	
	public Map<VoterData,Integer> getVotersMap(){
		Map<VoterData,Integer> votersCounter = new HashMap<VoterData,Integer>();
		for (VoterData voter : this.votersList) {
			if(votersCounter.containsKey(voter)){
				int voterInstances = votersCounter.get(voter);
				votersCounter.remove(voter);
				votersCounter.put(voter, voterInstances + 1);
			}else{
				votersCounter.put(voter, 1);
			}
		}
		
		return votersCounter;
	}

	@Override
	public boolean compareWith(IVotersList votersLst) {
		Map<VoterData, Integer> votersCounterOfMe = this.getVotersMap();
		Map<VoterData, Integer> votersCounterOfArg = votersLst.getVotersMap();
		
		if(votersCounterOfMe.equals(votersCounterOfArg)){
			return true;
		}
		
		return false;
		
		
	}

	@Override
	public boolean inList(int id) {
		for (VoterData voter : this.votersList) {
			if(voter.getId()==id){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param id - the id of the voter we want to get
	 * @return - the VoterData which represents the voter id
	 */
	public VoterData getVoter( int id ){
		for (VoterData voter : this.votersList) {
			if(voter.getId()==id){
				return voter;
			}
		}
		
		return null;
	}

}
