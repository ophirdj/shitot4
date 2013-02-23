package practiceStation.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IParty;
import practiceStation.tests.PracticeTest_ChoosingListStub.ChooseListComponent;
import practiceStation.tests.PracticeTest_ImagePanelStub.showFirstImageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.ConformationWithPartyComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.PrintConformationMessageComponent;
import practiceStation.tests.PracticeTest_PracticeStationWindowStub.printInfoMessageComponent;

public class PracticeTestPathes {
	
	private void reChooseParties(PracticeStationTestEnvironment testEnviroment, Iterable<IParty> wrongParties, IParty finalParty){
		for(IParty party : wrongParties){
			testEnviroment.addComponentForTest(new ChooseListComponent(party));
			testEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Did_you_intend_to_vote_for,party,false));
		}
		testEnviroment.addComponentForTest(new ChooseListComponent(finalParty));
		testEnviroment.addComponentForTest(new ConformationWithPartyComponent(Messages.Did_you_intend_to_vote_for,finalParty,true));
	}

	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate the shortest path (MSS path)
	 * @param testEnviroment: the test environment
	 * @param party: the chosen party
	 */
	public void shortestPathCall(PracticeStationTestEnvironment testEnviroment, IParty party){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		path_parameters.add(new PathParameters(party,false,null));
		genericPath(testEnviroment, path_parameters);
	}
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting and asking a guide
	 * @param testEnviroment: the test environment
	 * @param language: the language of the guide
	 * @param party: the chosen party
	 */
	public void guidePathCall(PracticeStationTestEnvironment testEnviroment, IParty party, Languages language){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		path_parameters.add(new PathParameters(party,true,language));
		genericPath(testEnviroment, path_parameters);
	}
	
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting and choosing "no" when asked if intend to choose party1
	 * @param testEnviroment: the test environment
	 * @param party1: the party chosen first
	 * @param party2: the party chosen second
	 */
	public void incorrectPartyPathCall(PracticeStationTestEnvironment testEnviroment, IParty party1,IParty party2){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		List<IParty> parties = new ArrayList<IParty>(2);
		parties.add(party1);
		parties.add(party2);
		path_parameters.add(new PathParameters(parties,false,null));
		
		genericPath(testEnviroment, path_parameters);
	}
	
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting and asking a guide
	 * @param testEnviroment: the test environment
	 * @param parties: the wrong parties
	 * @param finalParty: the correct final party
	 */
	public void incorrectPartyPathCall(PracticeStationTestEnvironment testEnviroment, Iterable<IParty> parties,IParty finalParty){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		List<IParty> all_parties = new LinkedList<IParty>();
		for (IParty party : parties) {
			all_parties.add(party);
		}
		all_parties.add(finalParty);
		path_parameters.add(new PathParameters(all_parties,false,null));
		genericPath(testEnviroment, path_parameters);
	}
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting, choosing "no" at did understand,
	 * then asking a guide and choosing again
	 * 
	 * @param testEnviroment: the test environment
	 * @param party1: the party chosen first
	 * @param party2: the party chosen second
	 */
	public void didntUnderstandPathCall(PracticeStationTestEnvironment testEnviroment, IParty party1,IParty party2, Languages language){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(2);
		path_parameters.add(new PathParameters(party1,false,language));
		path_parameters.add(new PathParameters(party2,true,language));
		genericPath(testEnviroment, path_parameters);
	}
	
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting, choosing "no" at did understand,
	 * then asking a guide and choosing again
	 * 
	 * @param testEnviroment: the test environment
	 * @param party1: the party chosen first
	 * @param party2: the party chosen second
	 */
	public void genericPath(PracticeStationTestEnvironment testEnviroment, Collection<PathParameters> pathes_parameters){
		if(pathes_parameters.isEmpty()) return;
		
		testEnviroment.addPracticeVoteCall();
		testEnviroment.addComponentForTest(new printInfoMessageComponent(Messages.This_station_is_only_for_practice));
		for (Iterator<PathParameters> iterator = pathes_parameters.iterator(); iterator.hasNext();) {
			PathParameters pathParameters = (PathParameters) iterator.next();
			pathParameters.toShowGuide(testEnviroment);
			testEnviroment.addComponentForTest(new printInfoMessageComponent(Messages.This_station_is_only_for_practice));
			reChooseParties(testEnviroment, pathParameters.getParties(),pathParameters.getFinalParty());
			if(iterator.hasNext()){
				testEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Have_you_understood_the_process,false));
			}
			else{
				testEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Have_you_understood_the_process,true));
			}
		}
	}
	
	public static class PathParameters{
		
		
		private boolean showGuide;
		private Languages language;
		private List<IParty> parties;
		private IParty finalParty;

		public PathParameters(IParty party,boolean showGuide, Languages language) {
			this.showGuide = showGuide;
			this.language = language;
			this.parties = new ArrayList<IParty>(0);
			this.finalParty = party;
		}
		
		public PathParameters(Collection<IParty> parties,boolean showGuide, Languages language) {
			this.showGuide = showGuide;
			this.language = language;
			this.parties = new ArrayList<IParty>(parties.size()-1);
			for (Iterator<IParty> iterator = parties.iterator(); iterator.hasNext();) {
				IParty party = (IParty) iterator.next();
				if(iterator.hasNext()){
					this.parties.add(party);
				}else{
					this.finalParty = party;
				}
			}
		}
		
		public PathParameters(IParty[] parties,boolean showGuide, Languages language) {
			this.showGuide = showGuide;
			this.language = language;
			this.parties = new ArrayList<IParty>(parties.length-1);
			for (int i = 0; i < parties.length-1; i++) {
				this.parties.add(parties[i]);
			}
			this.finalParty = parties[parties.length-1];
		}
		
		public void toShowGuide(PracticeStationTestEnvironment testEnviroment){
			if(showGuide){
				testEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Do_you_want_to_see_a_guide,true));
				testEnviroment.addComponentForTest(new showFirstImageComponent(language));
			}
			else{
				testEnviroment.addComponentForTest(new PrintConformationMessageComponent(Messages.Do_you_want_to_see_a_guide,false));
			}
		}
		
		public Iterable<IParty> getParties(){
			return parties;
		}
		
		public IParty getFinalParty(){
			return finalParty;
		}
	}
}
