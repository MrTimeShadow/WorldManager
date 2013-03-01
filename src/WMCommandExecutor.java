
public class WMCommandExecutor {

	
	public boolean executeLoadWorld(String name) {
		World[] loadedWorlds = etc.getServer().loadWorld(name);
		if(loadedWorlds[0] == null && loadedWorlds[1] == null && loadedWorlds[2] == null) {
			return false;
		}
		return true;
	}
	
	public boolean executeUnloadWorld(String name) {
		WMWorldManager.getInstance().unloadWorld(etc.getServer().getWorld(name));
		return true;
	}
	
	public boolean executeCreateWorld(String name, World.Type worldType, long seed, String generatorSettings) {
		if(worldType.equals(World.Type.DEFAULT_1_1)) {
			return false;
		}
		etc.getServer().loadWorld(name, worldType, seed, generatorSettings);
		return true;
	}
	
	public boolean executeDeleteWorld(String name) {
		World[] dimensions = etc.getServer().getWorld(name);
		for(World w : dimensions) {
			
		}
		return true;
	}

}
