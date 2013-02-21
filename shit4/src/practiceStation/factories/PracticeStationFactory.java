package practiceStation.factories;

import choosingList.factories.IChoosingListFactory;
import partiesList.model.IPartiesList;
import practiceStation.logic.IPracticeStation;
import practiceStation.logic.PracticeStation;

public class PracticeStationFactory implements IPracticeStationFactory {

	private IChoosingListFactory chooseFactory;
	private IPracticeStationWindowFactory stationWindowFactory;
	private IImagePanelFactory imagePanelFactory;

	public PracticeStationFactory(IChoosingListFactory chooseFactory,
	IPracticeStationWindowFactory stationWindowFactory,
	IImagePanelFactory imagePanelFactory) {
		this.chooseFactory=chooseFactory;
		this.imagePanelFactory = imagePanelFactory;
		this.stationWindowFactory = stationWindowFactory;
	}
	
	@Override
	public IPracticeStation createInstance(String name, IPartiesList parties) {
		return new PracticeStation(name, parties, chooseFactory, stationWindowFactory, imagePanelFactory);
	}

}
