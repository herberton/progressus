package br.com.hcs.progressus.enumerator;

public enum Theme {
	AFTERDARK("afterdark"),
	AFTERNOON("afternoon"),
	AFTERWORK("afterwork"),
	ARISTO("aristo"),
	BLACK_TIE("black-tie"),
	BLITZER("blitzer"),
	BLUESKY("bluesky"),
	CASABLANCA("casablanca"),
	CRUZE("cruze"),
	CUPERTINO("cupertino"),
	DARK_HIVE("dark-hive"),
	DELTA("delta"),
	DOT_LUV("dot-luv"),
	EGGPLANT("eggplant"),
	EXCITE_BIKE("excite-bike"),
	FLICK("flick"),
	GLASS_X("glass-x"),
	HOME("home"),
	HOT_SNEAKS("hot-sneaks"),
	HUMANITY("humanity"),
	LE_FROG("le-frog"),
	MIDNIGHT("midnight"),
	MINT_CHOC("mint-choc"),
	OVERCAST("overcast"),
	PEPPER_GRINDER("pepper-grinder"),
	REDMOND("redmond"),
	ROCKET("rocket"),
	SAM("sam"),
	SMOOTHNESS("smoothness"),
	SOUTH_STREET("south-street"),
	START("start"),
	SUNNY("sunny"),
	SWANKY_PURSE("swanky-purse"),
	TRONTASTIC("trontastic"),
	TWITTER_BOOTSTRAP("twitter bootstrap"),
	UI_DARKNESS("ui-darkness"),
	UI_LIGHTNESS("ui-lightness"),
	VADER("vader");
	
	private String name; 
	
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	
	private Theme(String name) {
		this.setName(name);
	}
	
	public static Theme getDefault() {
		return Theme.CRUZE;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
