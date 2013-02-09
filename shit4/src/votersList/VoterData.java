package votersList;


public class VoterData implements IVoterData{

	private int id;
	private boolean identified;
	private boolean voted;
	
	public VoterData(int id){
		this.id = id;
		identified = voted = false;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean isIdentified() {
		return identified;
	}

	public boolean hasVoted() {
		return voted;
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
		return ret;
	}
	
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
	
	
	public void markIdentified() throws AlreadyIdentified{
		if(this.identified) throw new AlreadyIdentified();
		this.identified = true;
	}

	@Override
	public void markVoted() throws Unidentified {
		if(!identified) throw new Unidentified();
		voted = true;
	}
	
	
	
}
