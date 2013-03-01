


public class WMWorldManager {
	private static WMWorldManager instance = new WMWorldManager();
	
	public ODedicatedServer oserver = etc.getServer().getMCServer();
	
	private WMWorldManager() {}
	
	
	
	public void unloadWorld(World[] world) {
		OWorldServer[] oworld = new OWorldServer[world.length];
		for(int i = 0; i < world.length; i++) {
			oworld[i] = world[i].getWorld();
		}
		oserver.worlds.remove(oworld);
	}
	
	public static WMWorldManager getInstance() {
		return instance;
	}
}
