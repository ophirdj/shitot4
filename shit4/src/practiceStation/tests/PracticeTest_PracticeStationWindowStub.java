package practiceStation.tests;

import java.util.NoSuchElementException;
import java.util.Queue;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import practiceStation.gui.IPracticeStationWindow;

public class PracticeTest_PracticeStationWindowStub extends StationPanel implements
		IPracticeStationWindow {

	static final long serialVersionUID = 1L;
	
	private PracticeStationTestEnvironment testEnvironment;
	private Queue<printErrorMessageComponent> printErrorMessageQueue;
	private Queue<PrintConformationMessageComponent> printConformationMessageQueue;
	private Queue<ConformationWithPartyComponent> ConformationWithPartyQueue;
	private Queue<printInfoMessageComponent> printInfoMessageQueue;
	
	public PracticeTest_PracticeStationWindowStub(
			PracticeStationTestEnvironment testEnvironment,
			Queue<printErrorMessageComponent> printErrorMessageQueue,
			Queue<PrintConformationMessageComponent> printConformationMessageQueue,
			Queue<ConformationWithPartyComponent> ConformationWithPartyQueue,
			Queue<printInfoMessageComponent> printInfoMessageQueue) {
		super();
		this.testEnvironment = testEnvironment;
		this.printErrorMessageQueue = printErrorMessageQueue;
		this.printConformationMessageQueue = printConformationMessageQueue;
		this.ConformationWithPartyQueue = ConformationWithPartyQueue;
		this.printInfoMessageQueue = printInfoMessageQueue;
		
	}
	
	public static class printInfoMessageComponent{
		private Messages shouldBeMessage;
		
		public printInfoMessageComponent(Messages shouldBeMessage) {
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public void checkAndReturn(Messages message) throws InterruptedException{
			PracticeStationTestEnvironment.assertTrue(message.equals(shouldBeMessage));
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printInfoMessage("+shouldBeMessage+")";
		}
	}
	
	public static class printInfoMessageLongComponent extends printInfoMessageComponent{
		private long milliSeconds2Wait;
		
		public printInfoMessageLongComponent(Messages shouldBeMessage,
				long milliSecond2Wait) {
			super(shouldBeMessage);
			this.milliSeconds2Wait = milliSecond2Wait;
		}
		
		@Override
		public void checkAndReturn(Messages message) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			super.checkAndReturn(message);
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " + super.toString(); 
		}
	}
	
	@Override
	public void printInfoMessage(Messages message) {
		PracticeStationTestEnvironment.assertTrue(testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printInfoMessage));
		try{
			printInfoMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
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
		
		public boolean checkAndReturn(Messages message) throws InterruptedException{
			PracticeStationTestEnvironment.assertTrue(message.equals(shouldBeMessage));
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printConformationMessage("+shouldBeMessage+"), return " + returnValue;
		}
	}
	
	public static class PrintConformationMessageLongComponent extends PrintConformationMessageComponent{
		
		private long milliSeconds2Wait;
		
		public PrintConformationMessageLongComponent(Messages shouldBeMessage, boolean returnValue, long milliSecond2Wait) {
			super(shouldBeMessage,returnValue);
			this.milliSeconds2Wait = milliSecond2Wait;
		}
		
		public boolean checkAndReturn(Messages message) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			return super.checkAndReturn(message);
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " + super.toString(); 
		}
	}
	
	@Override
	public boolean printConformationMessage(Messages message) {
		PracticeStationTestEnvironment.assertTrue(testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printConformationMessage));
		try{
			return printConformationMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
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
		
		public boolean checkAndReturn(Messages message,IParty party) throws InterruptedException{
			PracticeStationTestEnvironment.assertTrue(message.equals(shouldBeMessage));
			PracticeStationTestEnvironment.assertTrue(party.equals(shouldBeParty));
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printConformationMessage("+shouldBeMessage+", "+shouldBeParty+"), return " + returnValue;
		}
	}

	public static class ConformationWithPartyLongComponent extends ConformationWithPartyComponent{
		private long milliSeconds2Wait;
		
		public ConformationWithPartyLongComponent(Messages shouldBeMessage, IParty shouldBeParty, boolean returnValue, long milliSeconds2Wait) {
			super(shouldBeMessage, shouldBeParty, returnValue);
			this.milliSeconds2Wait = milliSeconds2Wait;
		}
		
		@Override
		public boolean checkAndReturn(Messages message,IParty party) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch(InterruptedException e){
				throw new AssertionError();
			}
			return super.checkAndReturn(message, party);
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " + super.toString(); 
		}
	}
	
	@Override
	public boolean printConformationMessage(Messages message, IParty party) {
		PracticeStationTestEnvironment.assertTrue(testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printConformationMessageWithParty));
		try{
			return ConformationWithPartyQueue.remove().checkAndReturn(message,party);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
			throw new AssertionError();
		}
	}

	
	public static class printErrorMessageComponent{
		private Messages shouldBeMessage;
		
		public printErrorMessageComponent(Messages shouldBeMessage) {
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public void checkAndReturn(Messages message) throws InterruptedException{
			PracticeStationTestEnvironment.assertTrue(message.equals(shouldBeMessage));
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printErrorMessage("+shouldBeMessage+")";
		}
	}
	
	public static class printErrorMessageLongComponent extends printErrorMessageComponent{
		private long milliSeconds2Wait;
		
		public printErrorMessageLongComponent(Messages shouldBeMessage,
				long milliSecond2Wait) {
			super(shouldBeMessage);
			this.milliSeconds2Wait = milliSecond2Wait;
		}
		
		@Override
		public void checkAndReturn(Messages message) throws InterruptedException{
			try{
				Thread.sleep(milliSeconds2Wait);
			}catch (InterruptedException e) {
				throw new AssertionError();
			}
			super.checkAndReturn(message);
		}
		
		@Override
		public String toString() {
			return "wait " + milliSeconds2Wait + "millis, " + super.toString(); 
		}
	}
	
	@Override
	public void printErrorMessage(Messages message) {
		PracticeStationTestEnvironment.assertTrue(testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printErrorMessage));
		try{
			printErrorMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
			throw new AssertionError();
		}
	}
	
	
	
	
	
	@Override
	public void closeWindow() {
		throw new AssertionError();
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
	public void printInfoMessage(Messages message, IParty party) {
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
