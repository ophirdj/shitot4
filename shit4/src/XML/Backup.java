package XML;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import factories.IPartiesListFactory;
import factories.IPartyFactory;
import factories.IVoterDataFactory;
import factories.IVotersListFactory;

import partiesList.IPartiesList;
import partiesList.IParty;
import partiesList.Party;
import votersList.IVoterData;
import votersList.IVotersList;
import votersList.VoterData;
import votersList.VotersList;



public class Backup implements IBackup {
	private IPartiesListFactory partiesListFactory;
	private IPartyFactory partyFactory;
	private IVotersListFactory votersListFactory;
	private IVoterDataFactory voterDataFactory;
	
	public Backup(IPartiesListFactory partiesListFactory,
			IPartyFactory partyFactory, IVotersListFactory votersListFactory,
			IVoterDataFactory voterDataFactory){
		this.partiesListFactory = partiesListFactory;
		this.partyFactory = partyFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;
	}

	
	/*private void backup(IVotersList voters){
		
		try{
			 
			FileOutputStream fout = new FileOutputStream("vList.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(voters);
			oos.close();
			System.out.println("Done");
	 
		}catch(Exception ex){
			   ex.printStackTrace();
		}
		
		
	}*/
	
	/*public VotersList restore(){
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
	}*/

	////////////////////////////////////////////////////////////
	//the methods will be repaired if needed!
	//because here I use List<Party> or List<VoterData>
	//
	//reply from: Ophir
	//You are correct. Ideally, we would've wanted to parse the
	//XML files directly to IVoterData and IParty using their
	//factories. However Ziv claimed it would have been very gross
	//because it requires passing the factories all the way down
	//to the class that actually reads the data. I disagree, but
	//for the time being it will stay that way.
	//p.s. you can use /* some comment */ instead of //. It's useful.
	////////////////////////////////////////////////////////////
	
	
	@Override
	public IVotersList restoreVoters() {
		ArrayList<VoterData> backupedVotersList;
		backupedVotersList = ReadXMLFile.readXMLVotersListBackup("votersBackup.xml");
		
		IVotersList backupedList = votersListFactory.createInstance();
		
		for (VoterData voterData : backupedVotersList) {
			backupedList.addVoter(voterData);
		}
		
		return backupedList;
	}

	@Override
	public IPartiesList restoreParties() {
		ArrayList<Party> backupedPartiesList;
		backupedPartiesList = ReadXMLFile.readXMLPartiesListBackup("partiesBackup.xml");
		
		IPartiesList backupedList = partiesListFactory.createInstance();
		
		for (Party party : backupedPartiesList) {
			backupedList.addParty(party);
		}
		
		return backupedList;
	}


	@Override
	public void storeState(IPartiesList parties, IVotersList voters) {
		BackupPartiesListToXMLFile.createEmptyPartiesListXMLFile("partiesBackup.xml");
		for (IParty party : parties) {
			BackupPartiesListToXMLFile.addPartyToXMLFile(party, "partiesBackup.xml");
		}
		
		BackupVotersListToXMLFile.createEmptyVotersListXMLFile("votersBackup.xml");
		for (IVoterData voter : voters) {
			BackupVotersListToXMLFile.addVoterToXMLFile(voter, "votersBackup.xml");
		}
		
	}
	
	
	
	
}
