package cz.kamenitxan.wsm;

import java.util.Objects;

public class TextMode {

	/**
	 * Generates image from terminal.
	 * @param args ["name", "realm"]
	 */
	public TextMode(String[] args) {
		Character character = Character.getInstance();
		character.setName(args[0]);
		character.setRealm(args[1]);

		Generators generators = Generators.getInstance();
		String getStatus = generators.requestData();
		if (!Objects.equals(getStatus, "ok")) {
			System.out.println(getStatus);
		} else {
			generators.generateImage();
		}
	}
}