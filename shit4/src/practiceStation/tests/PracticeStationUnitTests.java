package practiceStation.tests;

import global.dictionaries.Languages;

import org.junit.Test;

import choosingList.factories.IChoosingListFactory;



import partiesList.factories.IPartiesListFactory;
import partiesList.factories.IPartyFactory;
import partiesList.factories.PartiesListFactory;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.logic.PracticeStation;

public class PracticeStationUnitTests {
	
	PracticeTestPathes pathes = new PracticeTestPathes();
	
	public PracticeStation buildStation(PracticeStationTestEnvironment testEnviroment, IPartiesList partiesList){
		IImagePanelFactory ImagePanelFactory = testEnviroment.getImagePanelFactory();
		IPracticeStationWindowFactory practiceWindowFactory = testEnviroment.getPracticeWindowFactory();
		IChoosingListFactory choosingListFactory = testEnviroment.getChoosingListFactory();
		return new PracticeStation(partiesList,choosingListFactory,practiceWindowFactory,ImagePanelFactory);
	}
	
	@Test
	public void buildTest(){
		PracticeStationTestEnvironment buildTestEnviroment = new PracticeStationTestEnvironment("buildTest");
		IPartiesList emptyList = (new PartiesListFactory(new PracticeTest_PartyStubFactory())).createInstance();
		PracticeStation testedStation = buildStation(buildTestEnviroment,emptyList);
		buildTestEnviroment.runTest(testedStation);
	}
	
	@Test
	public void trivialTest(){
		PracticeStationTestEnvironment trivialTestEnviroment = new PracticeStationTestEnvironment("trivialTest");
		IPartyFactory partyFactory = new PracticeTest_PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		
		Languages language = Languages.Hebrew;
		trivialTestEnviroment.addSetLanguage(language);
		pathes.didntUnderstandPathCall(trivialTestEnviroment, party1, party1, language);
		
		/*
		 * will also work with partiesList = null,
		 * but I want to be fair about the parties.
		 */
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		
		PracticeStation testedStation = buildStation(trivialTestEnviroment,partiesList);
		trivialTestEnviroment.runTest(testedStation);
	}
	
	@Test
	public void easyTest(){
		PracticeStationTestEnvironment easyTestEnviroment = new PracticeStationTestEnvironment("easyTest");
		IPartyFactory partyFactory = new PracticeTest_PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		IParty party2 = partyFactory.createInstance("tested2", "2");
		IParty party3 = partyFactory.createInstance("tested3", "3");
		
		pathes.shortestPathCall(easyTestEnviroment, party1);
		pathes.shortestPathCall(easyTestEnviroment, party2);
		pathes.shortestPathCall(easyTestEnviroment, party3);
		pathes.incorrectPartyPathCall(easyTestEnviroment, party1, party2);
		
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		partiesList.addParty(party2);
		partiesList.addParty(party3);
		
		PracticeStation testedStation = buildStation(easyTestEnviroment,partiesList);
		easyTestEnviroment.runTest(testedStation);
	}
	
	@Test
	public void harderTest(){
		PracticeStationTestEnvironment harderTestEnviroment = new PracticeStationTestEnvironment("harderTest");
		IPartyFactory partyFactory = new PracticeTest_PartyStubFactory();
		IPartiesListFactory partiesListFactory = new PartiesListFactory(partyFactory);
		IParty party1 = partyFactory.createInstance("tested1", "1");
		IParty party2 = partyFactory.createInstance("tested2", "2");
		IParty party3 = partyFactory.createInstance("tested3", "3");
		
		IPartiesList partiesList = partiesListFactory.createInstance();
		partiesList.addParty(party1);
		partiesList.addParty(party2);
		partiesList.addParty(party3);
		
		Languages firstLanguage = Languages.English;
		Languages secondLanguage = Languages.Hebrew;
		Languages thirdLanguage = Languages.English;
		
		
		harderTestEnviroment.addSetLanguage(firstLanguage);
		pathes.shortestPathCall(harderTestEnviroment, party1);
		pathes.guidePathCall(harderTestEnviroment, party2, firstLanguage);
		pathes.guidePathCall(harderTestEnviroment, party3, firstLanguage);
		pathes.incorrectPartyPathCall(harderTestEnviroment, party1,party2);
		pathes.incorrectPartyPathCall(harderTestEnviroment, party2,party2);
		
		harderTestEnviroment.addSetLanguage(secondLanguage);
		pathes.guidePathCall(harderTestEnviroment, party1, secondLanguage);
		
		pathes.guidePathCall(harderTestEnviroment, party2, secondLanguage);
		harderTestEnviroment.addSetLanguage(thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party2, thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party3, thirdLanguage);
		pathes.incorrectPartyPathCall(harderTestEnviroment, partiesList, party3);
		
		harderTestEnviroment.addSetLanguage(thirdLanguage);
		pathes.guidePathCall(harderTestEnviroment, party1, thirdLanguage);
		pathes.didntUnderstandPathCall(harderTestEnviroment, party1, party2, thirdLanguage);
		
		
		PracticeStation testedStation = buildStation(harderTestEnviroment,partiesList);
		harderTestEnviroment.runTest(testedStation);
	}
}
