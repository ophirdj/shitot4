import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Backup {
	
	//VotersList 

	
	public void backup(VotersList votersList){
		
		try{
			 
			FileOutputStream fout = new FileOutputStream("vList.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(votersList);
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
	
	
	
	
}
