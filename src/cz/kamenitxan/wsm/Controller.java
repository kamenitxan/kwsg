package cz.kamenitxan.wsm;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Controller extends Pane {
	private Generators generators;

	public Controller() {
		generators = Generators.getInstance();
	}


	@FXML
	private Label progres;

	@FXML
	private TextField name;

	@FXML
	private TextField realm;

	@FXML
	private ImageView image;

	@FXML
	private void handlePreviewButtonAction() {
		generators.setName(name.getText());
		generators.setRealm(realm.getText());
		String getStatus = generators.requestData();
		if (!Objects.equals(getStatus, "ok")) {
			setProgres(getStatus);
		} else {
			BufferedImage imageAwt = generators.generateImage();
			setImage(SwingFXUtils.toFXImage(imageAwt, null));
		}
		progres.setText("Údaje načtecy");
		System.out.println(name.getText());

	}

	public void setImage(Image image) {
		this.image.setImage(image);
	}

	public void setProgres(String string) {
		progres.setText(string);
	}
}
