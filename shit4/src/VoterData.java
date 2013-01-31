
import java.util.*;
public class VoterData implements IVoterData{

	private int id;
	private Date date;
	private IParty lastParty;
	private int changeNumbers;
	private boolean identified;//TODO: check if need to change other Classes by that
	
	public VoterData(int id){
		this(id,null);
	}
	
	public VoterData(int id, IParty chosenParty){
		this.id = id;
		//this.date = new Date(); - this is wrong and will misjudge the equals function!
		this.date = null;
		this.lastParty = chosenParty;
		this.changeNumbers = 0;
		this.identified = false;
	}
	
	public VoterData(){
		this(-1,null);
	}
	public int getId(){
		return id;
	}
	public Date getDate(){
		return date;
	}
	
	public Date setVotingDate(){
		this.date = new Date();
		return this.date;
	}
	
	public IParty getLastChosenParty(){
		return lastParty;
	}
	public void increaseChangeNumbers(){
		changeNumbers++;
	}
	public int getChangeNumbers(){
		return changeNumbers;
	}
	public void setNewParty(IParty newParty){
		lastParty = newParty;
	}
	
	//added
	@Override
	public String toString() {
		String toPrint = "id = " + this.id + "\n" + 
				"date = " + this.date + "\n" +  
				"lastParty :: " + this.lastParty + "\n" +
				"chageNumebrs = " + changeNumbers + "\n";
		
		return toPrint;
	}
	
	public VoterData copy(){
		VoterData res = new VoterData(id,lastParty);
		res.date = this.date;
		res.changeNumbers = this.changeNumbers;
		
		return res;
	}
	
	@Override
	public boolean equals(Object arg0) {
		VoterData arg = (VoterData) arg0;
		if(arg0.getClass()!=this.getClass() || arg0==null){
			return false;
		}
		
		if(id==arg.id && date==arg.date
				&& this.changeNumbers==arg.changeNumbers 
				&& this.lastParty==arg.lastParty
				&& this.identified==arg.identified){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * sets the flag which tells if the voter already passed the identification
	 */
	public void setIdentified(){
		this.identified = true;
	}
	
	
	
}
