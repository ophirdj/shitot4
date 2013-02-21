package votersList.model;


public class VoterData implements IVoterData{

	private int id;
	private boolean identified;
	private boolean voted;
	private boolean startedVote;
	
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
		return ret;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0.getClass()!=this.getClass() || arg0==null){
			return false;
		}
		VoterData arg = (VoterData) arg0;		
		return arg.id == id && arg.identified == identified && arg.voted == voted;
		
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
