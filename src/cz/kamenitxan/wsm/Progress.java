package cz.kamenitxan.wsm;

import cz.kamenitxan.wsm.images.DataPkg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds character raids progress
 * Created by Kamenitxan (kamenitxan@me.com) on 20.01.15.
 */
public class Progress implements Serializable{
	Map<Integer, Raid> raids = null;

	public Progress() {
		raids = new HashMap<>();
		raids.put(32, new Raid() {{
			id = 32;
			name = "HM";
			bosses = 7;
			imgURL = DataPkg.class.getResource("hm.png");
		}});
		raids.put(33, new Raid() {{
			id = 33;
			name = "BFR";
			bosses = 10;
			imgURL = DataPkg.class.getResource("brf.png");
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
