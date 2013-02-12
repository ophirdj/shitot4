package partiesList;

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
	public synchronized void increaseVoteNumber() {
		voteNumber++;
	}
	
	@Override
	public synchronized void decreaseVoteNumber() {
		voteNumber--;	
	}
	
	@Override
	public synchronized int getVoteNumber() {
		return voteNumber;
	}
	
	synchronized void setVoteNumber(int v) {
		voteNumber = v;
	}
	
	public synchronized Party copy(){
		Party res = new Party(name, symbol, voteNumber);
		return res;
	}
	
	@Override
	public synchronized String toString() {
		return "name = " + name + "; symbol = " + symbol + "; voteNumber = " + voteNumber + "\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		Party arg = (Party) obj;
		if(this.getClass()!=obj.getClass() || obj==null){
			return false;
		}
		return this.name==arg.name && this.symbol==arg.symbol;
	}


}
