package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
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
	
	
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String name;
	
	
	private Theme(String name) {
		this.setName(name);
	}
	
	
	public static final Theme getDefault() {
		return Theme.HOT_SNEAKS;
	}
	
	
	@Override
	public String toString() {
		try {
			return StringHelper.isNullOrEmpty(this.getName()) ? "" : this.getName();
		} catch (Exception e) {
			Theme.log.error(e.getMessage(), e);
		}
		return "";
	}
}
