package cz.kamenitxan.wsm;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds character raids progress
 * Created by Kamenitxan (kamenitxan@me.com) on 20.01.15.
 */
public class Progress implements Serializable{
	Map<Integer, Raid> raids = null;
	private static final String IMGS = "/images/";

	public Progress() {
		raids = new HashMap<>();
		raids.put(32, new Raid() {{
			id = 32;
			name = "HM";
			bosses = 7;
			imgURL = this.getClass().getResource(IMGS + "hm.png");
		}});
		raids.put(33, new Raid() {{
			id = 33;
			name = "BFR";
			bosses = 10;
			imgURL =this.getClass().getResource(IMGS + "brf.png");
		}});
		raids.put(34, new Raid() {{
			id = 34;
			name = "HFC";
			bosses = 13;
			imgURL = this.getClass().getResource(IMGS + "hfc.png");
		}});
	}

	public Raid getRaid(int raid) {
		return raids.get(raid);
	}

	public Map<Integer, Raid> getRaids() {
		return raids;
	}

	void setRaidProgress(int id, int lfr, int normal, int heroic, int mythic) {
		Raid raid = raids.get(id);
		raid.lfrKills = lfr;
		raid.normalKills = normal;
		raid.heroicKills = heroic;
		raid.mythicKills = mythic;
	}
}
