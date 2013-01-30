import java.util.List;

public interface IMainframe {
	
	/**
	 * 	
	 */
	public void initialize();
	
	
	/**
	 * 
	 */
	public void check();
	
	
	/**
	 * 
	 */
	public void hotbackup();
	
	
	/**
	 * 
	 */
	public void restore();
	
	
	/**
	 * 
	 */
	public void compareLists();
	
	
	/**
	 * 
	 */
	public void countVotes();
	
	
	/**
	 * 
	 */
	public void shutDown();
	
	/**
	 * check if voter is in the voters list and if he isn't there - add him to the unregistered voters list
	 * @param id
	 */
	public void identification(int id);
	
	
	
	/**
	 * our very much loved peep method
	 */
	public void peep();
	
	
	//WTF is this shit?
	/**
	 * 
	 * @return
	 */
	public List<Integer> getAuthorizedIdList();


}