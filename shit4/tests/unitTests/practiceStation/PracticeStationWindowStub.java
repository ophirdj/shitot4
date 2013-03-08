package unitTests.practiceStation;

import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.Assert;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.StationPanel;
import partiesList.model.IParty;
import practiceStation.gui.IPracticeStationWindow;

public class PracticeStationWindowStub extends IPracticeStationWindow {

	static final long serialVersionUID = 1L;
	
	private TestEnvironment testEnvironment;
	private Queue<PrintErrorMessageComponent> printErrorMessageQueue;
	private Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue;
	private Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue;
	private Queue<PrintInfoMessageComponent> printInfoMessageQueue;
	
	public PracticeStationWindowStub(
			TestEnvironment testEnvironment,
			Queue<PrintErrorMessageComponent> printErrorMessageQueue,
			Queue<PrintConfirmationMessageComponent> printConfirmationMessageQueue,
			Queue<ConfirmationWithPartyComponent> ConfirmationWithPartyQueue,
			Queue<PrintInfoMessageComponent> printInfoMessageQueue) {
		super();
		this.testEnvironment = testEnvironment;
		this.printErrorMessageQueue = printErrorMessageQueue;
		this.printConfirmationMessageQueue = printConfirmationMessageQueue;
		this.ConfirmationWithPartyQueue = ConfirmationWithPartyQueue;
		this.printInfoMessageQueue = printInfoMessageQueue;
		
	}
	
	public static class PrintInfoMessageComponent{
		private Messages shouldBeMessage;
		
		public PrintInfoMessageComponent(Messages shouldBeMessage) {
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public void checkAndReturn(Messages message) throws InterruptedException{
			Assert.assertEquals(shouldBeMessage, message);
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printInfoMessage("+shouldBeMessage+")";
		}

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.PracticeWindow_printInfoMessage;
		}
	}
	
	public static class PrintInfoMessageLongComponent extends PrintInfoMessageComponent{
		private long milliSeconds2Wait;
		
		public PrintInfoMessageLongComponent(Messages shouldBeMessage,
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
		testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printInfoMessage);
		try{
			printInfoMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
			throw new AssertionError();
		}
	}

	
	public static class PrintConfirmationMessageComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		
		public PrintConfirmationMessageComponent(Messages shouldBeMessage, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public boolean checkAndReturn(Messages message) throws InterruptedException{
			Assert.assertEquals(shouldBeMessage, message);
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printConfirmationMessage("+shouldBeMessage+"), return " + returnValue;
		}

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.PracticeWindow_printConfirmationMessage;
		}
	}
	
	public static class PrintConfirmationMessageLongComponent extends PrintConfirmationMessageComponent{
		
		private long milliSeconds2Wait;
		
		public PrintConfirmationMessageLongComponent(Messages shouldBeMessage, boolean returnValue, long milliSecond2Wait) {
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
	public boolean printConfirmationMessage(Messages message) {
		testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printConfirmationMessage);
		try{
			return printConfirmationMessageQueue.remove().checkAndReturn(message);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
			throw new AssertionError();
		}
	}
	
	
	public static class ConfirmationWithPartyComponent{
		private boolean returnValue;
		private Messages shouldBeMessage;
		private IParty shouldBeParty;
		
		public ConfirmationWithPartyComponent(Messages shouldBeMessage, IParty shouldBeParty, boolean returnValue) {
			this.returnValue = returnValue;
			this.shouldBeMessage = shouldBeMessage;
			this.shouldBeParty = shouldBeParty;
		}
		
		public boolean checkAndReturn(Messages message,IParty party) throws InterruptedException{
			Assert.assertEquals(shouldBeMessage, message);
			Assert.assertEquals(shouldBeParty, party);
			return returnValue;
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printConfirmationMessage("+shouldBeMessage+", "+shouldBeParty+"), return " + returnValue;
		}

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.PracticeWindow_printConfirmationMessageWithParty;
		}
	}

	public static class ConfirmationWithPartyLongComponent extends ConfirmationWithPartyComponent{
		private long milliSeconds2Wait;
		
		public ConfirmationWithPartyLongComponent(Messages shouldBeMessage, IParty shouldBeParty, boolean returnValue, long milliSeconds2Wait) {
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
	public boolean printConfirmationMessage(Messages message, IParty party) {
		testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printConfirmationMessageWithParty);
		try{
			return ConfirmationWithPartyQueue.remove().checkAndReturn(message,party);
		}catch (NoSuchElementException e) {
			throw new AssertionError();
		}catch (InterruptedException e) {
			//messages from within practice should not be interrupted
			throw new AssertionError();
		}
	}

	
	public static class PrintErrorMessageComponent{
		private Messages shouldBeMessage;
		
		public PrintErrorMessageComponent(Messages shouldBeMessage) {
			this.shouldBeMessage = shouldBeMessage;
		}
		
		public void checkAndReturn(Messages message) throws InterruptedException{
			Assert.assertEquals(shouldBeMessage, message);
		}
		
		@Override
		public String toString() {
			return "practiceWindow.printErrorMessage("+shouldBeMessage+")";
		}

		public PracticeTestFunction getFunction() {
			return PracticeTestFunction.PracticeWindow_printErrorMessage;
		}
	}
	
	public static class PrintErrorMessageLongComponent extends PrintErrorMessageComponent{
		private long milliSeconds2Wait;
		
		public PrintErrorMessageLongComponent(Messages shouldBeMessage,
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
		testEnvironment.checkCalling(PracticeTestFunction.PracticeWindow_printErrorMessage);
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
	public Boolean getConfirmation(StationPanel station, String confirmationMessage) {
		throw new AssertionError();
	}

	@Override
	public void printError(StationPanel station, String errorMessage) {
		throw new AssertionError();
	}

	@Override
	public void printInfoMessage(Messages message, IParty party) {
		throw new AssertionError();
	}

	@Override
	public void printMessage(StationPanel station, String message) {
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
