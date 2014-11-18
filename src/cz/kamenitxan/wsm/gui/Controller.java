package cz.kamenitxan.wsm.gui;

import cz.kamenitxan.wsm.*;
import cz.kamenitxan.wsm.Character;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	private Generators generators;
	private Character character;

	public Controller() {
		generators = Generators.getInstance();
		character = Character.getInstance();
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
	private ColorPicker colorPicker;


	@FXML
	private void handleBgChoseButtonAction() {
		VistaNavigator.loadVista(VistaNavigator.VISTA_1);
	}


	@FXML
	private void handlePreviewButtonAction() {
		handlePreviewButtonAction(false);
	}

	@FXML
	private void handlePreviewButtonAction(boolean save) {
		generators.setName(name.getText());
		generators.setRealm(realm.getText());
		String getStatus = generators.requestData();
		if (!Objects.equals(getStatus, "ok")) {
			setProgres(getStatus);
		} else {
			progres.setText("Údaje načtecy");
			BufferedImage imageAwt = generators.generateImage(save);
			setImage(SwingFXUtils.toFXImage(imageAwt, null));
		}
		System.out.println(name.getText());

	}

	@FXML
	private void handleGenerateButtonAction() {
		handlePreviewButtonAction(true);
	}

	@FXML
	private void handleColorPickerAction() {
		Color c = colorPicker.getValue();
		generators.setFontColor(new java.awt.Color((float) c.getRed(),(float) c.getGreen(),(float) c.getBlue()));
	}

	@FXML
	private void menuFolderChooseAction() {
		final DirectoryChooser directoryChooser =
				new DirectoryChooser();
		final File selectedDirectory =
				directoryChooser.showDialog(Main.getPrimaryStage());
		if (selectedDirectory != null) {
			generators.setFolder(selectedDirectory.getAbsolutePath());
		}
	}

	@SuppressWarnings("HardcodedFileSeparator")
	@FXML
	private void menuAboutAction() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("KWSG");
		alert.setHeaderText("");
		alert.setContentText("Kamenitxan's signature generator\n\n http://kamenitxan.eu");
		alert.show();
	}

	@FXML
	private void menuCharListAction() {
	}

	/**
	 * Show image in UI window
	 * @param image generated image
	 */
	public void setImage(Image image) {
		this.image.setImage(image);
	}

	public void setProgres(String string) {
		progres.setText(string);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		java.awt.Color c = Generators.getInstance().getFontColor();
		colorPicker.setValue(Color.rgb(c.getRed(), c.getGreen(), c.getBlue()));
		name.setText(character.getName());
		realm.setText(character.getRealm());
	}
}
