package cz.kamenitxan.wsm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * Holds raid kill info.
 * Created by Kamenitxan (kamenitxan@me.com) on 20.01.15.
 */
public class Raid implements Serializable{
	public int id =0;
	public String name = "";
	public int lfrKills = 0;
	public int normalKills = 0;
	public int heroicKills = 0;
	public int mythicKills = 0;
	public int bosses = 0;
	public URL imgURL = null;
	private BufferedImage img = null;

	/**
	 *
	 * @param difficulty 1 to 4 -> lfr to mythic
	 * @return percentage of killed bosses
	 */
	public double getRaidProgress(int difficulty) {
		double killed = 0;
		switch (difficulty) {
			case 1: killed = lfrKills; break;
			case 2: killed = normalKills; break;
			case 3: killed = heroicKills; break;
			case 4: killed = mythicKills; break;
		}
		return (killed / bosses) * 100;
	}

	public BufferedImage getImg() {
		try {
			if (img == null) {
				img = ImageIO.read(imgURL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
