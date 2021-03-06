package cz.kamenitxan.wsm.gui;

import cz.kamenitxan.wsm.Generators;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class BgSelectController implements Initializable{
	private Generators generators = Generators.getInstance();

	public BgSelectController() {

	}

	@FXML
	private VBox bgvbox;

	@FXML
	private void handleBgSelectCloseButtonAction() {
		VistaNavigator.loadVista(VistaNavigator.VISTA_2);
	}

	@FXML
	private void handleBgChooseImageAction(String i) {
		generators.setBackgroudImage(i + ".png");
		VistaNavigator.loadVista(VistaNavigator.VISTA_2);
		System.out.println("vybráno obr " + i);
	}


	/**
	 * Fills scene with background images
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		URL url;
		ImageView image;

		for (int i = 1; i <= 10; i++) {
			url = this.getClass().getResource("/images/" + i + ".png");
			image = new ImageView(String.valueOf(url));
			image.setId(String.valueOf(i));
			bgvbox.getChildren().add(image);
			bgvbox.setPrefHeight(bgvbox.getPrefHeight()+70);
		}
		for (Node node : bgvbox.getChildren()) {
			node.setOnMouseClicked((event) -> handleBgChooseImageAction(node.getId()));
		}
	}
}
