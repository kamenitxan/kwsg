package cz.kamenitxan.wsm;

import cz.kamenitxan.wsm.images.DataPkg;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Generators {
	private static Generators singleton = new Generators();
	private static final String host = "http://eu.battle.net/api/";
	private static final Lists lists = Lists.getInstance();
	private Character character = Character.getInstance();

	private Generators() {}

	public static Generators getInstance() {
		return singleton;
	}

	public String requestData() {
		InputStream is = null;
		try{
			URL url = new URL(host + "wow/character/" + character.getRealm() + "/" +  character.getName() +
					"?fields=guild,items,titles,talents");
			is = url.openStream();
		} catch (FileNotFoundException ex){
			String error = "Postava: " + character.getName() + " na serveru " + character.getRealm() + " nenalezena";
			System.out.println(error);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		System.out.println(jsonObject.toString());

		if (character.getTitle() == null) {
			character.setTitle("");
		}

		return "ok";
	}

	public BufferedImage generateImage() {
		URL url = DataPkg.class.getResource("1.png");
		String result;
		BufferedImage image = null;
		if (url == null){
			result = "CHYBA";
		} else {
			result = "ok";
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Helvetica", Font.PLAIN, 28));
		g.setFont(g.getFont().deriveFont(28f));
		g.drawString(character.getName(), 90, 30);
		g.setFont(g.getFont().deriveFont(17f));
		g.drawString(character.getTitle().replace("%s, ", "").replace("%s ", ""), 90, 50);
		g.drawString(String.valueOf(character.getLvl()) ,   5, 77);
		g.drawString(String.valueOf(character.getIlvl()), 410, 77);
		g.setFont(g.getFont().deriveFont(14f));
		g.drawString(character.getSpec() + " " + lists.getPClass(character.getPlayerClass()) + " of " + character.getGuild(), 90, 77);
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

		try {
			ImageIO.write(image, "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result + " - obrázek uložen ");

		return image;
	}

	public void setName(String name){
		character.setName(name);
	}
	public void setRealm(String realm){
		character.setRealm(realm);
	}
}
