package cz.kamenitxan.wsm;

import cz.kamenitxan.wsm.images.DataPkg;


import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javafx.scene.control.Alert;

public class Generators {
	private static Generators singleton = new Generators();
	private static final Lists lists = Lists.getInstance();
	private Character character = Character.getInstance();
	private String backgroudImage = "1.png";
	private String lastName = "";
	private String lastRealm = "";
	private Color fontColor = new Color(255, 255, 255);
	private String folder = "";

	private Generators() {

	}

	public static Generators getInstance() {
		return singleton;
	}

	/**
	 * Gets character data from Blizzard API and stores it in Character class
	 * @return status of request. "ok" is success.
	 */
	public String requestData() {
		// TODO: save list of favourite chars
		if (lastName.equals(character.getName()) & lastRealm.equals(character.getRealm())) {
			return "ok";
		}

		lastName = character.getName();
		lastRealm = character.getRealm();

		InputStream is = null;
		try{
			String host = "http://eu.battle.net/api/";
			URL url = new URL(host + "wow/character/" + character.getRealm() + "/" +  character.getName() +
					"?fields=guild,items,titles,talents,professions");
			is = url.openStream();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getLocalizedMessage());
			System.out.println(ex.getMessage());
			String error = "Postava " + character.getName() + " na serveru " + character.getRealm() + " nenalezena";
			System.out.println(error);
			lastName = "";

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Postava nenalezena");
			alert.setHeaderText(null);
			alert.setContentText("Error 404: " + error);
			alert.showAndWait();

			return error;
		} catch (IOException ex) {
			String error = ex.getLocalizedMessage();
			System.out.println(error);
			lastName = "";

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Chyba API");
			alert.setHeaderText("50x: Chyba v API");
			alert.setContentText(error);
			alert.showAndWait();

			return error;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// FIX: ošetření, když nejde internet
		JsonReader jsonReader = Json.createReader(is);
		JsonObject jsonObject = jsonReader.readObject();
		JsonObject guild = jsonObject.getJsonObject("guild");
		JsonObject items = jsonObject.getJsonObject("items");
		JsonArray titles = jsonObject.getJsonArray("titles");
		JsonArray talents = jsonObject.getJsonArray("talents");
		JsonObject spec = talents.getJsonObject(0);
		if (spec.size() == 7) {
			spec = spec.getJsonObject("spec");
		}else {
			spec = talents.getJsonObject(1);
			spec = spec.getJsonObject("spec");
		}
		JsonObject professions = jsonObject.getJsonObject("professions");
		JsonObject primary_prof = professions.getJsonArray("primary").getJsonObject(0);
		JsonObject secondary_prof = professions.getJsonArray("primary").getJsonObject(1);


		jsonReader.close();

		character.setLvl(jsonObject.getInt("level"));
		character.setPlayerClass(jsonObject.getInt("class"));
		character.setRace(jsonObject.getInt("race"));
		character.setPlayerClass(jsonObject.getInt("class"));
		character.setSpec(spec.getString("name"));
		character.setGender(jsonObject.getInt("gender"));
		character.setAchievementPoints(jsonObject.getInt("achievementPoints"));
		character.setAvatar(jsonObject.getString("thumbnail"));
		character.setGuild(guild.getString("name"));
		character.setIlvl(items.getInt("averageItemLevelEquipped"));
		for (JsonValue i : titles) {
			JsonObject title = (JsonObject) i;
			if (title.size() == 3) {
				character.setTitle(title.getString("name"));
			}
		}
		character.setPrimaryProf(primary_prof.getString("name"));
		character.setPrimaryProfLvl(primary_prof.getInt("rank"));
		character.setSecondaryProf(secondary_prof.getString("name"));
		character.setSecondaryProfLvl(secondary_prof.getInt("rank"));

		System.out.println(jsonObject.toString());

		if (character.getTitle() == null) {
			character.setTitle("");
		}

		return "ok";
	}

	/**
	 * Generates image from character class data a saves it to HDD.
	 * @param save image is saved to HDD if true
	 * @return generated image
	 */
	public BufferedImage generateImage(boolean save) {
		URL url = DataPkg.class.getResource(backgroudImage);
		URL pp = DataPkg.class.getResource(character.getPrimaryProf() + ".jpeg");
		URL sp = DataPkg.class.getResource(character.getSecondaryProf() + ".jpeg");
		String result;
		BufferedImage image = null, primaryProfImg = null, secondaryProfImg = null;
		if (url == null){
			result = "CHYBA";
		} else {
			result = "ok";
			try {
				image = ImageIO.read(url);
				primaryProfImg = ImageIO.read(pp);
				secondaryProfImg = ImageIO.read(sp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Helvetica", Font.PLAIN, 28));
		g.setFont(g.getFont().deriveFont(28f));
		g.setColor(fontColor);
		g.drawString(character.getName(), 90, 30);
		g.setFont(g.getFont().deriveFont(17f));
		g.drawString(character.getTitle().replace("%s, ", "").replace("%s ", "").replace("%s", ""), 90, 50);
		g.drawString(String.valueOf(character.getPrimaryProfLvl()), 405, 15);
		g.drawImage(primaryProfImg, 432, 3, null);
		g.drawString(String.valueOf(character.getSecondaryProfLvl()), 405, 33);
		g.drawImage(secondaryProfImg, 432, 18, null);

		// white text on bottom line
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(character.getLvl()) ,   5, 77);
		g.drawString(String.valueOf(character.getIlvl()), 410, 77);
		g.setFont(g.getFont().deriveFont(14f));
		g.drawString("lvl " + character.getLvl() + " " + character.getSpec() + " " +
				lists.getPClass(character.getPlayerClass()) + " of " + character.getGuild(), 90, 77);

		URL avatarUrl = null;
		java.awt.Image avatar = null;
		try {
			if (character.getAvatar() != null) {
				avatarUrl = new URL("http://eu.battle.net/static-render/eu/" + character.getAvatar());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			if (avatarUrl != null) {
				avatar = ImageIO.read(avatarUrl);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(avatar, 0, 0, 80, 80, null);
		g.dispose();

		if (save) {
			try {
				if (!Objects.equals(folder, "")) {
					String path = folder + System.getProperty("file.separator") + character.getName() + ".png";
					ImageIO.write(image, "png", new File(path));
				} else {
					ImageIO.write(image, "png", new File(character.getName() + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(result + " - obrázek uložen ");
		}
		// TODO: Save over scp
		return image;
	}

	/**
	 * Sets name of character. before queriyng Blizzard API
	 * @param name of the character
	 */
	public void setName(String name){
		character.setName(name);
	}

	/**
	 * Sets name of realm. before queriyng Blizzard API
	 * @param realm of the character
	 */
	public void setRealm(String realm){
		character.setRealm(realm);
	}

	/**
	 * Sets color of font on image
	 * @param fontColor java.awt.color
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return current color of font
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 *
	 * @param folder in which will image be saved
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * Sets name of selected background image
	 * @param backgroudImage filename of background image
	 */
	public void setBackgroudImage(String backgroudImage) {
		this.backgroudImage = backgroudImage;
	}
}
