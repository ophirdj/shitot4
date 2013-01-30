import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

	@Override
	public boolean compareWith(IVotersList voters) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean inList(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
