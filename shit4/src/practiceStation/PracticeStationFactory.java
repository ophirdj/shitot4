package practiceStation;

import factories.IChoosingListFactory;
import partiesList.IPartiesList;

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
