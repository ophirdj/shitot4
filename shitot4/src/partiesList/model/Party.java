package partiesList.model;

/**
 * implementation of Party
 * @author Emil
 *
 */
public class Party implements IParty{
	/**
	 * saves the name of the party
	 */
	private String name;
	/**
	 * saves the symbol of the pary
	 */
	private String symbol;
	/**
	 * saves the number of votings for the pary
	 */
	private int voteNumber = 0;
	
	/**
	 * builds a party
	 * @param name the name of the party
	 * @param symbol the symbol  of the party
	 * @param voteNumber the number of voters  of the party
	 */
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
		if(obj==null || this.getClass()!=obj.getClass()){
			return false;
		}
		Party arg = (Party) obj;
		return this.name.equals(arg.name) && this.symbol.equals(arg.symbol);
	}


}
