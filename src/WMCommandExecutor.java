import java.io.File;


public class WMCommandExecutor {

	
	public boolean executeLoadWorld(String name) {
		World[] loadedWorlds = etc.getServer().loadWorld(name);
		if(loadedWorlds[0] == null && loadedWorlds[1] == null && loadedWorlds[2] == null) {
			return false;
		}
		new WMWorldConfiguration(loadedWorlds);
		return true;
	}
	
	public boolean executeUnloadWorld(String name) {
		World[] world = etc.getServer().getWorld(name);
		OWorldServer[] oworld = new OWorldServer[world.length];
		for(int i = 0; i < world.length; i++) {
			oworld[i] = world[i].getWorld();
		}
		etc.getServer().getMCServer().worlds.remove(oworld);
		return true;
	}
	
	public boolean executeCreateWorld(String name, World.Type worldType, long seed, String generatorSettings) {
		if(worldType.equals(World.Type.DEFAULT_1_1)) {
			return false;
		}
		World[] loadedWorlds = etc.getServer().loadWorld(name, worldType, seed, generatorSettings);
		new WMWorldConfiguration(loadedWorlds);
		return true;
	}
	
	public boolean executeDeleteWorld(String name, World.Dimension dim) {
		File directory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParentFile();
		File worldDirectory = new File(directory, name);
		File overWorldDirectory = new File(worldDirectory, "region");
		File netherWorldDirectory = new File(worldDirectory, "DIM-1");
		File endWorldDirectory = new File(worldDirectory, "DIM1");
		if(dim == null) {
			worldDirectory.delete();
		} else if(dim == World.Dimension.NORMAL) {
			overWorldDirectory.delete();
		} else if(dim == World.Dimension.NETHER) {
			netherWorldDirectory.delete();
		} else if(dim == World.Dimension.END) {
			endWorldDirectory.delete();
		} else {
			return false;
		}
		
		return true;
	}
	

}
