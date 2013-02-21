package factories;

import mainframe.IMainframe;
import communication.IStationsController;

public interface IStationsControllerFactory {

	IStationsController createInstance(IMainframe mainframe);
}
