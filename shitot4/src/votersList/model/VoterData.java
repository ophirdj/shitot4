package votersList.model;

/**
 * implementation of IVoterData
 * @author Emil
 *
 */
public class VoterData implements IVoterData{

	/**
	 * the id of the voter
	 */
	private int id;
	/**
	 * true if the voter has already identified,
	 * otherwise false
	 */
	private boolean identified;
	/**
	 * true if the voter has already voted,
	 * otherwise false 
	 */
	private boolean voted;
	/**
	 * true if the voter has already started voting,
	 * otherwise false 
	 */
	private boolean startedVote;
	
	/**
	 * constructor of voterData
	 * @param id - the id of the voter we wish to create
	 */
	public VoterData(int id){
		this.id = id;
		identified = voted = startedVote = false;
		
	}
	
	@Override
	public int getId(){
		return id;
	}
	
	@Override
	public boolean isIdentified() {
		return identified;
	}

	@Override
	public boolean hasVoted() {
		return voted;
	}
	
	@Override
	public boolean hasStartedVote() {
		return startedVote;
	}
	
	@Override
	public String toString() {
		String ret = "voter ID is " + id + " ";
		if(identified){
			ret += "voter has been identified ";
		}
		else{
			ret += "voter hasn't been identified ";
		}
		if(voted){
			ret += "voter has already voted";
		}
		else{
			ret += "voter hasn't voted yet";
		}
		ret += "\n";
		return ret;
	}
	
	@Override
	public VoterData copy(){
		VoterData ret = new VoterData(id);
		ret.identified = identified;
		ret.voted = voted;
		ret.startedVote = startedVote;
		return ret;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0==null || arg0.getClass()!=this.getClass()  ){
			return false;
		}
		VoterData arg = (VoterData) arg0;		
		return arg.id == id && arg.identified == identified && arg.voted == voted && arg.startedVote == startedVote;
		
	}
	
	@Override
	public void markIdentified() throws AlreadyIdentified{
		if(this.identified) throw new AlreadyIdentified();
		this.identified = true;
	}

	@Override
	public void markVoted() throws Unidentified {
		if(!identified) throw new Unidentified();
		voted = true;
		startedVote = false;
	}
	
	
	@Override
	public void markStartedVote() throws Unidentified{
		if(!identified) throw new Unidentified();
		startedVote = true;
	}
	
	
	
	
}
