package unitTests.choosingList;

import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.Assert;

import global.dictionaries.Messages;
import partiesList.model.IPartiesList;
import partiesList.model.IParty;
import choosingList.gui.IChoosingWindow;
import choosingList.logic.ChooseType;
import choosingList.logic.IChoosingList.ChoosingInterruptedException;

public class ChoosingListWindowStub implements IChoosingWindow{

	private ChoosingListTestEnvironment testEnvironment;
	private Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue;
	private Queue<CloseWindowComponent> closeWindowQueue;
	private Queue<switchOnComponent> switchOnQueue;
	private Queue<switchOffComponent> switchOffQueue;
	private Queue<getPartyComponent> getPartyQueue;
	private Queue<getChoiceComponent> receiveChoiceQueue;
	
	public ChoosingListWindowStub(
			ChoosingListTestEnvironment testEnvironment,
			Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue,
			Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue,
			Queue<CloseWindowComponent> closeWindowQueue,
			Queue<switchOnComponent> switchOnQueue,
			Queue<switchOffComponent> switchOffQueue,
			Queue<getPartyComponent> getPartyQueue,
			Queue<getChoiceComponent> receiveChoiceQueue) {
		
		super();
		this.testEnvironment = testEnvironment;
		this.printConfirmationMessageQueue = printConfirmationMessageQueue;
		this.ConfirmationWithPartyQueue = ConfirmationWithPartyQueue;
		this.closeWindowQueue = closeWindowQueue;
		this.switchOnQueue = switchOnQueue;
		this.switchOffQueue = switchOffQueue;
		this.getPartyQueue = getPartyQueue;
		this.receiveChoiceQueue = receiveChoiceQueue;
	}
	
	public static class getPartyComponent{
		
		private IParty party;
		private ChoosingListTestEnvironment testEnvironment;

		public getPartyComponent(IParty party) {
			this.party =party;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.getParty(), return: " + party;
		}
		
		public IParty checkAndReturn(){
			testEnvironment.updateRunningTestLog(this.toString());
			return party;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_getParty;
		}
	}
	
	@Override
	public IParty getParty() {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_getParty);
		try{
			return getPartyQueue.remove().checkAndReturn();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class getChoiceComponent{
		
		protected IPartiesList shouldBeParties;
		private ChooseType choice;
		protected ChoosingListTestEnvironment testEnvironment;

		public getChoiceComponent(IPartiesList partiesToShow, ChooseType choice) {
			this.shouldBeParties =partiesToShow;
			this.choice = choice;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.recieveChoice("+shouldBeParties+"), return: " + choice;
		}
		
		public ChooseType checkAndReturn(IPartiesList actualParties) throws ChoosingInterruptedException{
			Assert.assertEquals(shouldBeParties, actualParties);
			testEnvironment.updateRunningTestLog(this.toString());
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
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
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
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_recieveChoice);
		try{
			return receiveChoiceQueue.remove().checkAndReturn(partiesToShow);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}

	public static class switchOffComponent{
		
		private ChoosingListTestEnvironment testEnvironment;

		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.switchOff()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_switchOff;
		}

		public void updateLog() {
			testEnvironment.updateRunningTestLog(this.toString());
		}
	}

	@Override
	public void switchOff() {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_switchOff);
		try{
			switchOffQueue.remove().updateLog();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class switchOnComponent{
		
		private ChoosingListTestEnvironment testEnvironment;

		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.switchOn()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_switchOn;
		}

		public void updateLog() {
			testEnvironment.updateRunningTestLog(this.toString());
		}
	}

	@Override
	public void switchOn() {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_switchOn);
		try{
			switchOnQueue.remove().updateLog();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class CloseWindowComponent{
		private getChoiceComponent toStop = null;
		private ChoosingListTestEnvironment testEnvironment;
		
		public CloseWindowComponent(){}
		
		public CloseWindowComponent(getChoiceComponent toStop) {
			this.toStop = toStop;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.closeWindow()";
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_closeWindow;
		}
		
		public void retire(){
			testEnvironment.updateRunningTestLog(this.toString());
			if(toStop != null) toStop.retire();
		}
	}

	@Override
	public void closeWindow() {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_closeWindow);
		try{
			closeWindowQueue.remove().retire();
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class PrintConfirmationMessageComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		private ChoosingListTestEnvironment testEnvironment;
		
		public PrintConfirmationMessageComponent(Messages shouldBeMessage, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		public boolean checkAndReturn(Messages message){
			Assert.assertEquals(shouldBeMessage, message);
			testEnvironment.updateRunningTestLog(this.toString());
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.printConfirmationMessage("+shouldBeMessage+"), return " + returnValue;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_printConfirmationMessage;
		}
	}
	
	@Override
	public boolean printConfirmationMessage(Messages message) {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_printConfirmationMessage);
		try{
			return printConfirmationMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}
	}
	
	public static class ConfirmationWithPartyComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		private IParty shouldBeParty;
		private ChoosingListTestEnvironment testEnvironment;
		
		public ConfirmationWithPartyComponent(Messages shouldBeMessage, IParty shouldBeParty, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
			this.shouldBeParty = shouldBeParty;
		}
		
		public void setTestEnvironment(ChoosingListTestEnvironment testEnvironment){
			this.testEnvironment = testEnvironment;
		}
		
		public boolean checkAndReturn(Messages message,IParty party){
			Assert.assertEquals(shouldBeMessage, message);
			Assert.assertEquals(shouldBeParty, party);
			testEnvironment.updateRunningTestLog(this.toString());
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "choosingWindow.printConfirmationMessage("+shouldBeMessage+", "+shouldBeParty+"), return " + returnValue;
		}
		
		public ChoosingListFunction getFunction(){
			return ChoosingListFunction.choosingWindow_printConfirmationMessageWithParty;
		}
	}

	@Override
	public boolean printConfirmationMessage(Messages message, IParty party) {
		testEnvironment.checkCalling(ChoosingListFunction.choosingWindow_printConfirmationMessageWithParty);
		try{
			return ConfirmationWithPartyQueue.remove().checkAndReturn(message,party);
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
	public String translate(Messages message) {
		throw new AssertionError();
	}

}
