package cz.kamenitxan.wsm;

import com.aquafx_project.AquaFx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


@SuppressWarnings("HardcodedFileSeparator")
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(
				createScene(
						loadMainPane()
				)
		);

		if(System.getProperty("os.name").equals("Mac OS X")){
			AquaFx.style();
		}

	primaryStage.show();

	}

	/**
	 * Loads the main fxml layout.
	 * Sets up the vista switching VistaNavigator.
	 * Loads the first vista into the fxml layout.
	 *
	 * @return the loaded pane.
	 * @throws IOException if the pane could not be loaded.
	 */
	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Pane mainPane = loader.load(
				getClass().getResourceAsStream(VistaNavigator.MAIN)
		);
		MainController mainController = loader.getController();

		VistaNavigator.setMainController(mainController);
		VistaNavigator.loadVista(VistaNavigator.VISTA_2);

		return mainPane;
	}

	/**
	 * Creates the main application scene.
	 *
	 * @param mainPane the main application layout.
	 *
	 * @return the created scene.
	 */
	private Scene createScene(Pane mainPane) {
		return new Scene(
				mainPane
		);
	}

	public static void main(String[] args) {
		if (args.length != 0) {
			new TextMode(args);
		}else {
			launch(args);
		}
	}
}
