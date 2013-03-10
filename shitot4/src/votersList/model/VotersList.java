package votersList.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * implementation of IVotersList
 * @author Emil
 *
 */
public class VotersList implements IVotersList {
	/**
	 * list of the voters
	 */
	List<IVoterData> voters;
	
	/**
	 * creates empty voters list
	 */
	public VotersList(){
		voters = new ArrayList<IVoterData>();
	}

	@Override
	public synchronized Iterator<IVoterData> iterator() {
		return voters.iterator();
	}

	@Override
	public synchronized void addVoter(IVoterData toAdd) {
		voters.add(toAdd);
	}

	@Override
	public synchronized IVoterData findVoter(int id) throws VoterDoesntExist {
		for(IVoterData voter: voters){
			if(voter.getId() == id){
				return voter;
			}
		}
		throw new VoterDoesntExist();
	}

	@Override
	public synchronized void peep() {
		System.out.println("=========================");
		System.out.println("Peep of Voters list");
		System.out.println("=========================");
		System.out.println(voters);
	}

	@Override
	public synchronized boolean inList(int id) {
		for(IVoterData voter: voters){
			if(voter.getId() == id){
				return true;
			}
		}
		return false;
	}

	@Override
	public synchronized IVotersList copy() {
		IVotersList copy = new VotersList();
		for(IVoterData voter: voters){
			copy.addVoter(voter.copy());
		}
		return copy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != VotersList.class) return false;
		IVotersList list = (IVotersList) obj;
		Iterator<IVoterData> thisVoterDataIterator = iterator();
		Iterator<IVoterData> objVoterDataIterator = list.iterator();
		while(thisVoterDataIterator.hasNext() && objVoterDataIterator.hasNext()){
			IVoterData thisData = thisVoterDataIterator.next();
			IVoterData objData = objVoterDataIterator.next();
			if(!thisData.equals(objData)) return false;
		}
		return(!thisVoterDataIterator.hasNext() && !objVoterDataIterator.hasNext());
	}
	
	@Override
	public boolean isEmpty(){
		return this.voters.isEmpty();
	}
}
