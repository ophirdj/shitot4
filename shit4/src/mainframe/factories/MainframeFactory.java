package mainframe.factories;

import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;
import mainframe.communication.IStationsControllerFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.Mainframe;

public class MainframeFactory implements IMainframeFactory {

	private IVoterDataFactory voterDataFactory;
	private IVotersListFactory votersListFactory;
	private IBackupFactory backupFactory;
	private IMainframeWindowFactory mainframeWindowFactory;
	private IReadSuppliedXMLFactory readSuppliedXMLFactory;
	private IStationsControllerFactory stationsControllerFactory;

	public MainframeFactory(IBackupFactory backupFactory,
			IMainframeWindowFactory mainframeWindowFactory,
			IReadSuppliedXMLFactory readSuppliedXMLFactory,
			IStationsControllerFactory stationsControllerFactory,
			IVoterDataFactory voterDataFactory,
			IVotersListFactory votersListFactory) {
		this.backupFactory = backupFactory;
		this.mainframeWindowFactory = mainframeWindowFactory;
		this.readSuppliedXMLFactory = readSuppliedXMLFactory;
		this.stationsControllerFactory = stationsControllerFactory;
		this.voterDataFactory = voterDataFactory;
		this.votersListFactory = votersListFactory;
	}
	
	@Override
	public IMainframe createInstance() {
		
		return new Mainframe(backupFactory,
				mainframeWindowFactory,
				readSuppliedXMLFactory, stationsControllerFactory,voterDataFactory,votersListFactory);
	}
}