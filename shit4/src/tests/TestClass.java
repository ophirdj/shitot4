package tests;
import java.util.ArrayList;

import backupToXML.Backup;
import backupToXML.BackupPartiesListToXMLFile;
import backupToXML.BackupVotersListToXMLFile;
import backupToXML.ReadXMLFile;
import backupToXML.WriteXMLFileUnregisteredVoters;

import partiesList.Party;
import votersList.IVoterData.AlreadyIdentified;
import votersList.IVoterData.Unidentified;
import votersList.VoterData;
import votersList.VotersList;


public class TestClass {

	


		
	public static void main(String[] args) {
		//testVoter3();
		//testVotersXML();
		//testVotingRecords();
		//testSerBackup();
		//testReadWriteUnregisteredVotersXMLFile();
		//System.out.println(Boolean.toString(true));
		//testBackupVotersList1();
		testBackupPartiesList1();
		System.out.println("иси");
	}

	public static void testVoter1(){
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		
		VoterData emil = new VoterData(1);
		VoterData ziv = new VoterData(2);
		VoterData yona = new VoterData(3);
		VoterData ophir = new VoterData(4);
		VoterData daniel = new VoterData(5);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		//list1.merge(list2);
		list1.peep();

	}
	
	public static void testVoter2(){
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		
		VoterData emil = new VoterData(1);
		VoterData ziv = new VoterData(2);
		VoterData yona = new VoterData(3);
		VoterData ophir = new VoterData(4);
		VoterData daniel = new VoterData(5);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.addVoter(yona);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		//list1.merge(list2);
		list1.peep();

	}
	
	public static void testVoter3(){
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		
		VoterData emil = new VoterData(1);
		VoterData ziv = new VoterData(2);
		VoterData yona = new VoterData(3);
		VoterData ophir = new VoterData(4);
		VoterData daniel = new VoterData(5);
		
		VotersList list1 = new VotersList();
		list1.addVoter(emil);	
		list1.addVoter(ziv);
		list1.addVoter(yona);
		list1.peep();
		
		VotersList list2 = new VotersList();
		list2.addVoter(yona);
		list2.addVoter(ophir);
		list2.addVoter(daniel);
		list2.peep();
		
		//list1.replaceWith(list2);
		list1.peep();

	}
	
	/*public static void testVotersXML(){
		ArrayList<Integer> res = ReadXMLFile.readXMLVotersList();
		
		System.out.println("======================");
		for (Integer id : res) {
			System.out.println(id);
		}
	}
	
	public static void testVotingRecords(){
		ArrayList<Party> res = ReadXMLFile.readXMLvotingRecords();
		
		System.out.println("======================");
		for (Party p : res) {
			System.out.println(p);
		}
	}
	
	public static void testSerBackup(){
		
		Party p = new Party("AtudaLashilton", "AL", 0);
		
		VoterData emil = new VoterData(1);
		VoterData ziv = new VoterData(2);
		VoterData yona = new VoterData(3);
		VoterData ophir = new VoterData(4);
		VoterData daniel = new VoterData(5);
		
		VotersList votersList = new VotersList();
		votersList.addVoter(emil);	
		votersList.addVoter(ziv);
		votersList.addVoter(yona);
		votersList.addVoter(ophir);
		votersList.addVoter(daniel);
		votersList.peep();

		//Backup.backup(votersList);
		//VotersList restored = Backup.restore();
		
		System.out.println("#######################");
		//restored.peep();
		
	}*/
	
	public static void testReadWriteUnregisteredVotersXMLFile(){
		WriteXMLFileUnregisteredVoters.createEmptyUnregisteredVotersXMLFile("file.xml");
		WriteXMLFileUnregisteredVoters.addVoterToXMLFile(123,"file.xml");
		WriteXMLFileUnregisteredVoters.addVoterToXMLFile(456,"file.xml");
		WriteXMLFileUnregisteredVoters.addVoterToXMLFile(789,"file.xml");
		ArrayList<Integer> res = ReadXMLFile.readUnregisteredVotersXMLFile("file.xml");
		System.out.println(res);
	}
	
	public static void testBackupVotersList1(){
		BackupVotersListToXMLFile.createEmptyVotersListXMLFile("votersBackup.xml");
		VoterData v1 = new VoterData(123);
		try {
			v1.markIdentified();
		} catch (AlreadyIdentified e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VoterData v2 = new VoterData(456);
		try {
			v2.markIdentified();
			v2.markVoted();
		} catch (AlreadyIdentified e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Unidentified e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VoterData v3 = new VoterData(789);
		
		ArrayList<VoterData> lst = new ArrayList<VoterData>();
		lst.add(v1);
		lst.add(v2);
		lst.add(v3);
		
		//BackupVotersListToXMLFile.addVoterToXMLFile(v1, "votersBackup");
		//BackupVotersListToXMLFile.addVoterToXMLFile(v2, "votersBackup");
		//BackupVotersListToXMLFile.addVoterToXMLFile(v3, "votersBackup");
		
		System.out.println("The list before backup is:");
		for (VoterData voterData : lst) {
			System.out.println(voterData);
		}
		
		System.out.println("\n\n\n now we will put it to the xml file \n... ");
		
		for (VoterData voterData : lst) {
			BackupVotersListToXMLFile.addVoterToXMLFile(voterData, "votersBackup.xml");
		}
		
		System.out.println("now we will print the backuped list:");
		
		ArrayList<VoterData> backupedVotersList = ReadXMLFile.readXMLVotersListBackup("votersBackup.xml");
		
		for (VoterData voterData : backupedVotersList) {
			System.out.println(voterData);
		}	
	}
	
	
	public static void testBackupPartiesList1(){
		
		//TODO: huge problem when was hebrew
		Party p1 = new Party("RIGHT", "й", 10);
		Party p2 = new Party("LEFT", "L", 10);
		Party p3 = new Party("CENTER", "C", 10);
		
		ArrayList<Party> lst = new ArrayList<Party>();
		
		lst.add(p1);
		lst.add(p2);
		lst.add(p3);
		
		System.out.println("The parties:");
		for (Party party : lst) {
			System.out.println(party);
		}
		
		System.out.println("\n\n\n  now we will backup \n...");
		
		BackupPartiesListToXMLFile.createEmptyPartiesListXMLFile("partiesBackup.xml");
		
		for (Party party : lst) {
			BackupPartiesListToXMLFile.addPartyToXMLFile(party, "partiesBackup.xml");
		}
		
		
		ArrayList<Party> backupList = ReadXMLFile.readXMLPartiesListBackup("partiesBackup.xml");
		
		
		
		System.out.println("\n\n\n\n\n\nThe list after backup is:");
		for (Party party : backupList) {
			System.out.println(party);
		}
		
		
	}
	
		



}
