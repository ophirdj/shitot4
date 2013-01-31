public class Party implements IParty{
	private String name;
	private String symbol;
	private int voteNumber = 0;
	
	public Party(String name,String symbol,int voteNumber){
		this.name = name;
		this.symbol = symbol;
		this.voteNumber = voteNumber;
	}
	
	public Party(String name, String symbol){
		this(name,symbol,0);
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void increaseVoteNumber() {
		voteNumber++;
	}
	@Override
	public void decreaseVoteNumber() {
		voteNumber--;	
	}
	@Override
	public int getVoteNumber() {
		return voteNumber;
	}
	
	public Party copy(){
		Party res = new Party(name, symbol, voteNumber);
		return res;
	}
	
	@Override
	public String toString() {
		return "name = " + name + "; symbol = " + symbol + "; voteNumber = " + voteNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		Party arg = (Party) obj;
		if(this.getClass()!=obj.getClass() || obj==null){
			return false;
		}
		
		if(this.name==arg.name 
				&& this.symbol==arg.symbol 
				&& this.voteNumber==arg.voteNumber){
			return true;
		}
		
		return false;
	}


}
