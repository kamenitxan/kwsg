package cz.kamenitxan.wsm;

import javafx.fxml.FXML;


public class BgSelectController{
	private Generators generators;

	public BgSelectController() {
		Generators.getInstance();
	}

	@FXML
	private void handleBgSelectCloseButtonAction() {
		VistaNavigator.loadVista(VistaNavigator.VISTA_2);
	}


}
