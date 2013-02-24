package choosingList.tests;

import java.util.NoSuchElementException;
import java.util.Queue;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import choosingList.gui.IChoosingWindow;
import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

public class ChoosingListWindowStub implements IChoosingWindow{

	private ChoosingListTestEnvironment testEnvironment;
	private Queue<PrintConformationMessageComponent> printConformationMessageQueue;
	private Queue<ConformationWithPartyComponent> ConformationWithPartyQueue;
	private Queue<CloseWindowComponent> closeWindowQueue;
	private Queue<switchOnComponent> switchOnQueue;
	private Queue<switchOffComponent> switchOffQueue;
	private Queue<getPartyComponent> getPartyQueue;
	private Queue<getChoiceComponent> receiveChoiceQueue;
	
	public ChoosingListWindowStub(
			ChoosingListTestEnvironment testEnvironment,
			Queue<PrintConformationMessageComponent> printConformationMessageQueue,
			Queue<ConformationWithPartyComponent> ConformationWithPartyQueue,
			Queue<CloseWindowComponent> closeWindowQueue,
			Queue<switchOnComponent> switchOnQueue,
			Queue<switchOffComponent> switchOffQueue,
			Queue<getPartyComponent> getPartyQueue,
			Queue<getChoiceComponent> receiveChoiceQueue) {
		
		super();
		this.testEnvironment = testEnvironment;
		this.printConformationMessageQueue = printConformationMessageQueue;
		this.ConformationWithPartyQueue = ConformationWithPartyQueue;
		this.closeWindowQueue = closeWindowQueue;
		this.switchOnQueue = switchOnQueue;
		this.switchOffQueue = switchOffQueue;
		this.getPartyQueue = getPartyQueue;
		this.receiveChoiceQueue = receiveChoiceQueue;
	}
	
	public static class getPartyComponent{
		
		private IParty party;

		public getPartyComponent(IParty party) {
			this.party =party;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.getParty(), return: " + party;
		}
		
		public IParty checkAndReturn(){
			return party;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_getParty;
		}
	}
	
	@Override
	public IParty getParty() {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_getParty));
		try{
			return getPartyQueue.remove().checkAndReturn();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class getChoiceComponent{
		
		protected IPartiesList shouldBeParties;
		private ChooseType choice;

		public getChoiceComponent(IPartiesList partiesToShow, ChooseType choice) {
			this.shouldBeParties =partiesToShow;
			this.choice = choice;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.getParty("+shouldBeParties+"), return: " + choice;
		}
		
		public ChooseType checkAndReturn(IPartiesList actualParties) throws ChoosingInterruptedException{
			ChoosingListTestEnvironment.assertTrue(actualParties.equals(shouldBeParties));
			return choice;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_recieveChoice;
		}
		
		public void retire(){}
	}

	public static class getChoiceWaitComponent extends getChoiceComponent{
		private long longEnoughTime = 1000;
		Thread toStop = null;
		
		public getChoiceWaitComponent(IPartiesList partiesToShow) {
			super(partiesToShow, null);
		}
		
		@Override
		public String toString() {
			return "choosingWindow.getParty("+shouldBeParties+"), throw ChoosingInterruptedException";
		}
		
		@Override
		public ChooseType checkAndReturn(IPartiesList actualParties) throws ChoosingInterruptedException {
			super.checkAndReturn(actualParties);
			
			try {
				toStop = Thread.currentThread();
				Thread.sleep(longEnoughTime);
				throw new AssertionError();
			} catch (InterruptedException e) {
				throw new ChoosingInterruptedException();
			}finally{
				toStop = null;
			}
		}
		
		public void retire(){
			if(toStop != null){
				toStop.interrupt();
			}
		}
	}
	
	@Override
	public ChooseType receiveChoiceSymbol(IPartiesList partiesToShow)
			throws ChoosingInterruptedException {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_recieveChoice));
		try{
			return receiveChoiceQueue.remove().checkAndReturn(partiesToShow);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}

	public static class switchOffComponent{
		@Override
		public String toString() {
			return "choosingWindow.switchOff()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_switchOff;
		}
	}

	@Override
	public void switchOff() {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_switchOff));
		try{
			switchOffQueue.remove();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class switchOnComponent{
		@Override
		public String toString() {
			return "choosingWindow.switchOn()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_switchOn;
		}
	}

	@Override
	public void switchOn() {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_switchOn));
		try{
			switchOnQueue.remove();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class CloseWindowComponent{
		getChoiceComponent toStop = null;
		
		public CloseWindowComponent(){}
		
		public CloseWindowComponent(getChoiceComponent toStop) {
			this.toStop = toStop;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.closeWindow()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_closeWindow;
		}
		
		public void retire(){
			if(toStop != null) toStop.retire();
		}
	}

	@Override
	public void closeWindow() {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_closeWindow));
		try{
			closeWindowQueue.remove().retire();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class PrintConformationMessageComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		
		public PrintConformationMessageComponent(Messages shouldBeMessage, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public boolean checkAndReturn(Messages message){
			ChoosingListTestEnvironment.assertTrue(message.equals(shouldBeMessage));
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.printConformationMessage("+shouldBeMessage+"), return " + returnValue;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_printConformationMessage;
		}
	}
	
	@Override
	public boolean printConformationMessage(Messages message) {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_printConformationMessage));
		try{
			return printConformationMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class ConformationWithPartyComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		private IParty shouldBeParty;
		
		public ConformationWithPartyComponent(Messages shouldBeMessage, IParty shouldBeParty, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
			this.shouldBeParty = shouldBeParty;
		}
		
		public boolean checkAndReturn(Messages message,IParty party){
			ChoosingListTestEnvironment.assertTrue(message.equals(shouldBeMessage));
			ChoosingListTestEnvironment.assertTrue(party.equals(shouldBeParty));
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.printConformationMessage("+shouldBeMessage+", "+shouldBeParty+"), return " + returnValue;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_printConformationMessageWithParty;
		}
	}

	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		ChoosingListTestEnvironment.assertTrue(testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_printConformationMessageWithParty));
		try{
			return ConformationWithPartyQueue.remove().checkAndReturn(message,party);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public Boolean getConfirmation(String confirmationMessage) {
		throw new AssertionError();
	}

	@Override
	public void printError(String errorMessage) {
		throw new AssertionError();
	}

	@Override
	public void printErrorMessage(Messages message) {
		throw new AssertionError();
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		throw new AssertionError();
	}

	@Override
	public void printInfoMessage(Messages message) {
		throw new AssertionError();
	}

	@Override
	public void printMessage(String message) {
		throw new AssertionError();
	}

	@Override
	public void setLanguage(Languages language) {
		throw new AssertionError();
	}

	@Override
	public String translate(Messages message) {
		throw new AssertionError();
	}

}
