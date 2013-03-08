package acceptanceTest;

import mainframe.communication.IStationsControllerFactory;
import mainframe.factories.IMainframeFactory;
import mainframe.factories.IMainframeWindowFactory;
import mainframe.logic.IMainframe;
import mainframe.logic.Mainframe;
import votersList.factories.IVoterDataFactory;
import votersList.factories.IVotersListFactory;
import fileHandler.factories.IBackupFactory;
import fileHandler.factories.IReadSuppliedXMLFactory;

public class MainframeTestConfigurationFactory implements IMainframeFactory{

	private IVoterDataFactory voterDataFactory;
	private IVotersListFactory votersListFactory;
	private IBackupFactory backupFactory;
	private IMainframeWindowFactory mainframeWindowFactory;
	private IReadSuppliedXMLFactory readSuppliedXMLFactory;
	private IStationsControllerFactory stationsControllerFactory;
	private int backupTimeIntervalSeconds;

	public MainframeTestConfigurationFactory(int backupTimeIntervalSeconds, IBackupFactory backupFactory,
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
		this.backupTimeIntervalSeconds = backupTimeIntervalSeconds;
	}
	
	@Override
	public IMainframe createInstance() {
		
		return new Mainframe(backupTimeIntervalSeconds , backupFactory,
				mainframeWindowFactory,
				readSuppliedXMLFactory, stationsControllerFactory,voterDataFactory,votersListFactory);
	}
}
