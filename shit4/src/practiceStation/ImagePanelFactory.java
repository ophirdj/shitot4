package practiceStation;

import javax.swing.JPanel;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.ImagePanel;

public class ImagePanelFactory implements IImagePanelFactory {

	@Override
	public IImagePanel createInstance(IListImages images, JPanel caller) {
		return new ImagePanel(images,caller);
	}

}
