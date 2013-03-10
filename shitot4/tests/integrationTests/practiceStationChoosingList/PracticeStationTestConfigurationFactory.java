package integrationTests.practiceStationChoosingList;

import choosingList.factories.IChoosingListFactory;
import partiesList.model.IPartiesList;
import practiceStation.factories.IImagePanelFactory;
import practiceStation.factories.IPracticeStationFactory;
import practiceStation.factories.IPracticeStationWindowFactory;
import practiceStation.logic.IPracticeStation;
import practiceStation.logic.PracticeStation;

public class PracticeStationTestConfigurationFactory implements IPracticeStationFactory{

	private IChoosingListFactory chooseFactory;
	private IPracticeStationWindowFactory practiceStationWindowFactory;
	private IImagePanelFactory imagePanelFactory;
	private long time;

	public PracticeStationTestConfigurationFactory(IChoosingListFactory chooseFactory,
			IPracticeStationWindowFactory practiceStationWindowFactory,
			IImagePanelFactory imagePanelFactory,
			long time) {
		this.chooseFactory = chooseFactory;
		this.practiceStationWindowFactory = practiceStationWindowFactory;
		this.imagePanelFactory = imagePanelFactory;
		this.time = time;
	}
	
	@Override
	public IPracticeStation createInstance(IPartiesList parties) {
		return new PracticeStation(parties,chooseFactory,practiceStationWindowFactory,imagePanelFactory,time);
	}

}
