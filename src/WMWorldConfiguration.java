import java.util.HashMap;


public class WMWorldConfiguration {
	
	public static HashMap<String, WMWorldConfiguration> configs = new HashMap<String, WMWorldConfiguration>();
	
	public String worldName;
	private World[] world;
	public final WMWorldConfigFile configurationFile;
	
	public WMWorldConfiguration(World[] world) {
		this.worldName = world[0].getName();
		this.world = world;
		this.configurationFile = new WMWorldConfigFile(world[0].getName());
		configs.put(worldName, this);
	}

	public World[] getWorld() {
		return world;
	}
	
	
	

}
