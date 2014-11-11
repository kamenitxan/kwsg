package cz.kamenitxan.wsm;

import java.util.HashMap;
import java.util.Map;

public class Lists {
	private static Lists singleton = new Lists();
	private Map<Integer, String> classes = null;

	private Lists(){
		classes = new HashMap<>();
		classes.put(1, "Warrior");
		classes.put(2, "Paladin");
		classes.put(3, "Hunter");
		classes.put(4, "Rogue");
		classes.put(5, "Priest");
		classes.put(6, "Death Knight");
		classes.put(7, "Shaman");
		classes.put(8, "Mage");
		classes.put(9, "Warlock");
		classes.put(10, "Monk");
		classes.put(11, "Druid");
	}

	public static Lists getInstance() {
		return singleton;
	}

	public String getPClass(int i){
		return classes.get(i);
	}


}
