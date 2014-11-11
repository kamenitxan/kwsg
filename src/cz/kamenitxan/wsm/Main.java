package cz.kamenitxan.wsm;

import com.aquafx_project.AquaFx;
import cz.kamenitxan.wsm.images.DataPkg;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.json.*;


@SuppressWarnings("HardcodedFileSeparator")
public class Main extends Application {

	private Controller controller = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));

		primaryStage.setTitle("Kamenitxan's WoW Signature Generator");
		primaryStage.setScene(new Scene(fxmlLoader.load()));
		controller = fxmlLoader.getController();

		if(System.getProperty("os.name").equals("Mac OS X")){
			AquaFx.style();
		}

		primaryStage.show();

	}

	public static void main(String[] args) {
		if (args.length != 0) {
			new TextMode(args);
		}else {
			launch(args);
		}
	}
}
