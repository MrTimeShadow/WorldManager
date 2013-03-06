import java.util.HashMap;


public class WMWorldConfiguration {
	
	public static HashMap<String, WMWorldConfiguration> configs = new HashMap<String, WMWorldConfiguration>();
	
	public String worldName;
	private World[] world;
	public final WMWorldConfigFile conf;
	
	public WMWorldConfiguration(World[] world) {
		this.worldName = world[0].getName();
		this.world = world;
		this.conf = new WMWorldConfigFile(world[0].getName());
		configs.put(worldName, this);
	}

	public World[] getWorld() {
		return world;
	}
	
	
	

}
