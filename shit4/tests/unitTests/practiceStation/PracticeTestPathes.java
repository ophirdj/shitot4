package unitTests.practiceStation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IParty;
import partiesList.model.Party;
import unitTests.practiceStation.PracticeTest_ChoosingListStub.ChooseListComponent;
import unitTests.practiceStation.PracticeTest_ChoosingListStub.ChooseListLongComponent;
import unitTests.practiceStation.PracticeTest_ChoosingListStub.ChooseListTooLongComponent;
import unitTests.practiceStation.PracticeTest_ChoosingListStub.ChoosingListRetireComponent;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ImagePanelRetireComponent;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ShowFirstImageComponent;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ShowFirstImageLongComponent;
import unitTests.practiceStation.PracticeTest_ImagePanelStub.ShowFirstImageTooLongComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.ConfirmationWithPartyComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.PrintConfirmationMessageComponent;
import unitTests.practiceStation.PracticeTest_PracticeStationWindowStub.PrintInfoMessageComponent;

public class PracticeTestPathes {
	
	/**
	 * only needed for station that doesn't wait, so any non negative number is fine
	 */
	final static long enough_time = 0;
	
	/**
	 * In order to avoid false alarms when the station retire in time
	 * but did'nt have the time to finish all the calls
	 */
	final static long timeGuard = 100;
	
	public static long getTimeGuard(){
		return timeGuard * Thread.activeCount();
	}
		
	public static long getStationActualTime(long stationLogicalTime){
		return stationLogicalTime + getTimeGuard()/2;
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
		genericPath(testEnviroment, path_parameters,enough_time);
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
		genericPath(testEnviroment, path_parameters,enough_time);
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
		
		genericPath(testEnviroment, path_parameters, enough_time);
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
		genericPath(testEnviroment, path_parameters,enough_time);
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
		genericPath(testEnviroment, path_parameters,enough_time);
	}
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate asking guide and then wait some time.
	 * 
	 * @param party: the chosen party
	 * @param testEnviroment: the test environment
	 * @param waitingTime: waiting time
	 * @param language: the language for the dictionary
	 * @param stationTime: the station time before timeout.
	 */
	public void longFirstGuide(PracticeStationTestEnvironment testEnviroment, IParty party, long waitingTime, Languages language,long stationTime){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		path_parameters.add(new PathParameters(new PartyParameters(party),new GuideParameters(language,waitingTime)));
		genericPath(testEnviroment, path_parameters,stationTime);
	}
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate choosing list that take some time
	 * 
	 * @param testEnviroment: the test environment
	 * @param party: the chosen party
	 * @param waitingTime: waiting time
	 * @param language: the language for the dictionary
	 * @param stationTime: the station time before timeout.
	 */
	public void longFirstChoose(PracticeStationTestEnvironment testEnviroment, IParty party,long waitingTime, Languages language, long stationTime){
		List<PathParameters> path_parameters = new ArrayList<PathParameters>(1);
		path_parameters.add(new PathParameters(new PartyParameters(party,waitingTime),new GuideParameters(false,language)));
		genericPath(testEnviroment, path_parameters, stationTime);
	}
	
	private IParty getPseudoRandomParty(Object[] partiesArray, Random rand ){
		
		return (IParty)partiesArray[rand.nextInt(partiesArray.length)];
	}
	
	private long getPseudoRandomTime(long stationTime, Random rand){
		return stationTime/(rand.nextInt(10)+5);
	}
	
	public void complicatedPath(PracticeStationTestEnvironment testEnviroment, Collection<IParty> parties, Languages language, long stationTime){
		long onePerTen = stationTime/10;
		long onePerFour = stationTime/4;
		long onePerTwo = stationTime/2;
		Random rand = new Random(System.nanoTime());
		
		Object partiesArray[] = new Party[parties.size()];
		partiesArray = parties.toArray();
		
		Collection<PathParameters> pathes = new LinkedList<PathParameters>();
		Collection<PartyParameters> partiesParameters1 = new LinkedList<PartyParameters>();
		partiesParameters1.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		partiesParameters1.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		partiesParameters1.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));;
		partiesParameters1.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		
		GuideParameters guideParameters1 = new GuideParameters(language,onePerTen);
		
		Collection<PartyParameters> partiesParameters2 = new LinkedList<PartyParameters>();
		partiesParameters2.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		partiesParameters2.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		partiesParameters2.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));;
		partiesParameters2.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTen));
		
		GuideParameters guideParameters2 = new GuideParameters(language,onePerTen);
		
		Collection<PartyParameters> partiesParameters3 = new LinkedList<PartyParameters>();
		partiesParameters3.add(new PartyParameters(getPseudoRandomParty(partiesArray,rand),onePerTwo));
		GuideParameters guideParameters3 = new GuideParameters(language,onePerTen);
		
		pathes.add(new PathParameters(partiesParameters1,guideParameters1));
		pathes.add(new PathParameters(partiesParameters2,guideParameters2));
		pathes.add(new PathParameters(partiesParameters3,guideParameters3));
		genericPath(testEnviroment,pathes,stationTime);
		
		pathes.clear();
		
		Collection<PartyParameters> partiesParametersRandom = new LinkedList<PartyParameters>();
		final int MaxParties = 10;
		int partiesNum = new Random(System.currentTimeMillis()).nextInt(MaxParties);
		for(int i = 0; i < partiesNum+1; i++){
			PartyParameters party = new PartyParameters(getPseudoRandomParty(partiesArray,rand),getPseudoRandomTime(stationTime,rand));
			partiesParametersRandom.add(party);
		}
		GuideParameters guideParametersRandom = new GuideParameters(language,onePerFour);
		pathes.add(new PathParameters(partiesParametersRandom,guideParametersRandom));
		genericPath(testEnviroment,pathes,stationTime);
		
	}
	
	
	/**
	 * add another practiceVoteCall to testEnviroment with parameters that
	 * simulate voting where every PathParameters in pathes_parameters
	 * represent a choosing path from offering guide to asking if the user
	 * understand (inclusive).
 	 * Understand in the last pathParameters.
 	 * Can only wait in panels and parties choosing.
	 * 
	 * @param testEnviroment: the test environment.
	 * @param pathes_parameters: PathParameters that simulate the voting between
	 * offering a guide and asking if the user understand (inclusive).
	 */
	public void genericPath(PracticeStationTestEnvironment testEnviroment, Collection<PathParameters> pathes_parameters, long stationTime){
		if(pathes_parameters.isEmpty()) return;
		testEnviroment.addPracticeVoteCall();
		testEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
		new PathesParameters(pathes_parameters,stationTime).buildPathes(testEnviroment);
	}

	public enum WaitingInterval{
		None{
			@Override
			public void chooseParty(PracticeStationTestEnvironment testEnviroment,IParty party,long waitTime){
				testEnviroment.addComponentForTest(new ChooseListComponent(party));
			}
			
			@Override
			public void showGuide(PracticeStationTestEnvironment testEnviroment,Languages language, long waitTime){
				testEnviroment.addComponentForTest(new ShowFirstImageComponent(language));
			}
		},
		Some{
			@Override
			public void chooseParty(PracticeStationTestEnvironment testEnviroment,IParty party,long waitTime){
				testEnviroment.addComponentForTest(new ChooseListLongComponent(party,waitTime));
			}
			
			@Override
			public void showGuide(PracticeStationTestEnvironment testEnviroment,Languages language, long waitTime){
				testEnviroment.addComponentForTest(new ShowFirstImageLongComponent(language,waitTime));
			}
		}
		,
		TooLong{
			@Override
			public void chooseParty(PracticeStationTestEnvironment testEnviroment,IParty party,long waitTime){
				testEnviroment.addComponentForTest(new ChooseListTooLongComponent(waitTime+getTimeGuard()));
			}
			
			@Override
			public void showGuide(PracticeStationTestEnvironment testEnviroment,Languages language, long waitTime){
				testEnviroment.addComponentForTest(new ShowFirstImageTooLongComponent(language,waitTime+getTimeGuard()));
			}
		};
		
		public abstract void chooseParty(PracticeStationTestEnvironment testEnviroment,IParty party, long waitTime);
		public abstract void showGuide(PracticeStationTestEnvironment testEnviroment,Languages language, long waitTime);
		
	}
	
	public static class PartyParameters{
		private IParty party;
		private long waitFor;
		
		public PartyParameters(IParty party) {
			this.party = party;
			waitFor = 0;
		}
		
		public PartyParameters(IParty party, long waitFor) {
			this.party = party;
			this.waitFor = waitFor;
		}
		
		public long Choose(PracticeStationTestEnvironment testEnviroment,boolean isFinal,long timeLeft){
			WaitingInterval waiting = waitingMode(timeLeft);
			waiting.chooseParty(testEnviroment, party, waitFor);
			if(!waiting.equals(WaitingInterval.TooLong)){
				testEnviroment.addComponentForTest(new ConfirmationWithPartyComponent(Messages.Did_you_intend_to_vote_for,party,isFinal));
			}
			return timeLeft - waitFor;
			
		}

		private WaitingInterval waitingMode(long timeLeft){
			if(waitFor == 0) return WaitingInterval.None;
			else if(waitFor > timeLeft) return WaitingInterval.TooLong;
			else return WaitingInterval.Some;
		}
	}
	
	public static class GuideParameters{
		private Languages language;
		private boolean showGuide = false;
		private long guideTime = 0;
		
		GuideParameters(Languages language){
			this.language = language;
		}
		
		GuideParameters(boolean showGuide,Languages language){
			this.language = language;
			this.showGuide = showGuide;
		}
		
		GuideParameters(Languages language, long guideTime){
			this.showGuide = true;
			this.language = language;
			this.guideTime = guideTime;
		}

		public long showGuide(PracticeStationTestEnvironment testEnviroment, long timeLeft) {
			WaitingInterval guideWait = waitingMode(timeLeft);
			if(!showGuide){
				testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Do_you_want_to_see_a_guide,false));
				return timeLeft;
			}
			
			testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Do_you_want_to_see_a_guide,true));
			guideWait.showGuide(testEnviroment, language, guideTime);
			return timeLeft - guideTime;
		}
		
		private WaitingInterval waitingMode(long timeLeft){
			if(guideTime == 0) return WaitingInterval.None;
			else if(guideTime > timeLeft) return WaitingInterval.TooLong;
			else return WaitingInterval.Some;
		}
	}
	
	public static class PathesParameters{
		private long timeLeft;
		private Collection<PathParameters> pathes;
		
		public PathesParameters(Collection<PathParameters> pathes,long stationTime) {
			this.timeLeft = stationTime;
			this.pathes = pathes;
		}
		
		private void retirePath(PracticeStationTestEnvironment testEnviroment) {
			testEnviroment.addComponentForTest(new ChoosingListRetireComponent());
			testEnviroment.addComponentForTest(new ImagePanelRetireComponent());
			testEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.Your_time_is_up));
		}
		
		public void buildPathes(PracticeStationTestEnvironment testEnviroment){
			for (Iterator<PathParameters> iterator = pathes.iterator(); iterator.hasNext();) {
				PathParameters pathParameters = (PathParameters) iterator.next();
				timeLeft = pathParameters.toShowGuide(testEnviroment,timeLeft);
				
				if(timeLeft < 0){
					retirePath(testEnviroment);
					return;
				}
				
				testEnviroment.addComponentForTest(new PrintInfoMessageComponent(Messages.This_station_is_only_for_practice));
				timeLeft = pathParameters.reChooseParties(testEnviroment,timeLeft);
				if(timeLeft < 0){
					retirePath(testEnviroment);
					return;
				}
				
				if(iterator.hasNext()){
					testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Have_you_understood_the_process,false));
				}
				else{
					testEnviroment.addComponentForTest(new PrintConfirmationMessageComponent(Messages.Have_you_understood_the_process,true));
				}
			}
		}
	}
	
	public static class PathParameters{
		
		private GuideParameters guide;
		private List<PartyParameters> parties;
		private PartyParameters finalParty;

		public PathParameters(IParty party,boolean showGuide, Languages language) {
			this.guide = new GuideParameters(showGuide,language);
			this.parties = new ArrayList<PartyParameters>(0);
			this.finalParty = new PartyParameters(party);
		}
		
		public PathParameters(Collection<IParty> parties,boolean showGuide, Languages language) {
			this.guide = new GuideParameters(showGuide,language);
			this.parties = new ArrayList<PartyParameters>(parties.size()-1);
			for (Iterator<IParty> iterator = parties.iterator(); iterator.hasNext();) {
				PartyParameters party = new PartyParameters(iterator.next());
				if(iterator.hasNext()){
					this.parties.add(party);
				}else{
					this.finalParty = party;
				}
			}
		}
		
		public PathParameters(IParty[] parties,boolean showGuide, Languages language) {
			this.guide = new GuideParameters(showGuide,language);
			this.parties = new ArrayList<PartyParameters>(parties.length-1);
			for (int i = 0; i < parties.length-1; i++) {
				this.parties.add(new PartyParameters(parties[i]));
			}
			this.finalParty = new PartyParameters(parties[parties.length-1]);
		}
		
		public PathParameters(PartyParameters party) {
			this.guide = new GuideParameters(null);
			this.parties = new ArrayList<PartyParameters>(0);
			this.finalParty = party;
		}
		
		public PathParameters(PartyParameters party,GuideParameters guide) {
			this.guide = guide;
			this.parties = new ArrayList<PartyParameters>(0);
			this.finalParty = party;
		}
		
		public PathParameters(Collection<PartyParameters> parties,GuideParameters guide) {
			this.guide = guide;
			this.parties = new ArrayList<PartyParameters>(parties.size()-1);
			for (Iterator<PartyParameters> iterator = parties.iterator(); iterator.hasNext();) {
				PartyParameters party = iterator.next();
				if(iterator.hasNext()){
					this.parties.add(party);
				}else{
					this.finalParty = party;
				}
			}
		}
		
		
		
		public long toShowGuide(PracticeStationTestEnvironment testEnviroment,long timeLeft){
			timeLeft = guide.showGuide(testEnviroment,timeLeft);
			return timeLeft;
		}
		
		public long reChooseParties(PracticeStationTestEnvironment testEnviroment, long timeLeft){
			for(PartyParameters partyParameter : parties){
				timeLeft = partyParameter.Choose(testEnviroment,false,timeLeft);
				if(timeLeft < 0){
					return timeLeft;
				}
			}
			timeLeft = finalParty.Choose(testEnviroment, true,timeLeft);
			return timeLeft;
		}
	}



}
