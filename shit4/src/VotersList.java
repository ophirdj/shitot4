import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class VotersList implements IVotersList {

	private List<IVoterData> votersList;
	
	//= new ArrayList<>();
	
	public VotersList(){
		this.votersList = new ArrayList<IVoterData>();
	}
	
	@Override
	public void addVoter(IVoterData toAdd){
		this.votersList.add(toAdd.copy());
	}
	
	/***
	 * merging the list of the object with the list of toMerge
	 * @param toMerge - the list to merge
	 */
	public void merge(VotersList toMerge){
		for (IVoterData toMergeIter : toMerge.votersList) {
			boolean notAddToList = false;
			for (IVoterData myIter : this.votersList) {
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
		for (IVoterData iter : this.votersList) {
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
	public IVoterData findVoter(int id) throws Exception{
		for (IVoterData iter : this.votersList) {
			if(iter.getId() == id){
				return iter;
			}
		}
		throw new Exception("Not Found");
	}
	
	public void replaceWith (VotersList toReplace){
		ArrayList<IVoterData> newList = new ArrayList<IVoterData>();
		for (IVoterData voter : toReplace.votersList) {
			newList.add(voter.copy());
		}
		this.votersList = newList;
			
	}
	
	public IVotersList copy(){
		// TODO
		return null;
	}

	@Override
	public Iterator<IVoterData> iterator() {
		return this.votersList.iterator();
	}
	
	public Map<IVoterData,Integer> getVotersMap(){
		Map<IVoterData,Integer> votersCounter = new HashMap<IVoterData,Integer>();
		for (IVoterData voter : this.votersList) {
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
		Map<IVoterData, Integer> votersCounterOfMe = this.getVotersMap();
		Map<IVoterData, Integer> votersCounterOfArg = votersLst.getVotersMap();
		
		if(votersCounterOfMe.equals(votersCounterOfArg)){
			return true;
		}
		
		return false;
		
		
	}

	@Override
	public boolean inList(int id) {
		for (IVoterData voter : this.votersList) {
			if(voter.getId()==id){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param id - the id of the voter we want to get
	 * @return - the IVoterData which represents the voter id
	 */
	public IVoterData getVoter( int id ){
		for (IVoterData voter : this.votersList) {
			if(voter.getId()==id){
				return voter;
			}
		}
		
		return null;
	}

	@Override
	public void replaceWith(IVotersList toReplace) {
		// TODO Auto-generated method stub
		
	}

}
