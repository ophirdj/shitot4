import java.util.*;
public class VoterData {
	private int id;
	private Date date;
	private IParty lastParty;
	private int changeNumbers;
	
	public VoterData(int id, IParty chosenParty){
		this.id = id;
		this.date = new Date();
		this.lastParty = chosenParty;
		this.changeNumbers = 0; 
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
	
	
	
}
