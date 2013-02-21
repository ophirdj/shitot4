package mainframe.communication;

import mainframe.logic.IMainframe;

public interface IStationsControllerFactory {

	IStationsController createInstance(IMainframe mainframe);
}
