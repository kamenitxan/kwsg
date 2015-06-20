package cz.kamenitxan.wsm;

public class Character {
	private static Character singleton = new Character();
	private String name = "Kamenitxania";
	private String realm = "Thunderhorn";
	private String avatar = null;
	private String guild = "";
	private String title = null;
	private String spec = null;
	private String primaryProf = null;
	private int primaryProfLvl = 0;
	private String secondaryProf = null;
	private int secondaryProfLvl = 0;
	private int lvl = 0;
	private int ilvl = 0;
	private int playerClass = 0;
	private int race = 0;
	private int gender = 0;
	private int achievementPoints = 0;
	private Progress raidProgress = null;

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getAchievementPoints() {
		return achievementPoints;
	}

	public void setAchievementPoints(int achievementPoints) {
		this.achievementPoints = achievementPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(int playerClass) {
		this.playerClass = playerClass;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGuild() {
		return guild;
	}

	public void setGuild(String guild) {
		this.guild = guild;
	}

	public int getIlvl() {
		return ilvl;
	}

	public void setIlvl(int ilvl) {
		this.ilvl = ilvl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getPrimaryProf() {
		return primaryProf;
	}

	public void setPrimaryProf(String primaryProf) {
		this.primaryProf = primaryProf;
	}

	public String getSecondaryProf() {
		return secondaryProf;
	}

	public void setSecondaryProf(String secondaryProf) {
		this.secondaryProf = secondaryProf;
	}

	public int getPrimaryProfLvl() {
		return primaryProfLvl;
	}

	public void setPrimaryProfLvl(int primaryProfLvl) {
		this.primaryProfLvl = primaryProfLvl;
	}

	public int getSecondaryProfLvl() {
		return secondaryProfLvl;
	}

	public void setSecondaryProfLvl(int secondaryProfLvl) {
		this.secondaryProfLvl = secondaryProfLvl;
	}

	public Progress getRaidProgress() {
		return raidProgress;
	}

	public void nullRaidProgress() {
		raidProgress = null;
	}

	public void setRaidProgress(int id, int lfr, int normal, int heroic, int mythic) {
		if (raidProgress == null) {
			raidProgress = new Progress();
		}
		raidProgress.setRaidProgress(id, lfr, normal, heroic, mythic);
	}

	private Character() {}

	/**
	 * @return Singleton of Character
	 */
	public static Character getInstance( ) {
		return singleton;
	}
}
