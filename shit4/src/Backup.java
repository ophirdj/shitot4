import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Backup implements IBackup {
	
	IVotersList voters;
	IPartiesList parties;
	
	public Backup(IVotersList voters, IPartiesList parties){
		this.voters = voters;
		this.parties = parties;
	}

	
	private void backup(){
		
		try{
			 
			FileOutputStream fout = new FileOutputStream("vList.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(voters);
			oos.close();
			System.out.println("Done");
	 
		}catch(Exception ex){
			   ex.printStackTrace();
		}
		
		
	}
	
	public VotersList restore(){
		VotersList res;
	
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("vList.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         res = (VotersList) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(Exception i)
	      {
	         i.printStackTrace();
	         System.out.println("Errrrrrr!!!");
	         return null;
	      }
		
		return res;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IVotersList restoreVoters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPartiesList restoreParties() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void retire() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
