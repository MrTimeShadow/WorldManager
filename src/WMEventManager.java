import java.io.File;


public class WMEventManager {

	
	public static boolean processWorldLoad(String name) {
		World[] loadedWorlds = etc.getServer().loadWorld(name);
		if(loadedWorlds[0] == null && loadedWorlds[1] == null && loadedWorlds[2] == null) {
			return false;
		}
		new WMWorldConfiguration(loadedWorlds);
		return true;
	}
	
	@Deprecated
	public static boolean processWorldUnload(String name) { //TODO: This doesn't work...
		World[] world = etc.getServer().getWorld(name);
		OWorldServer[] oworld = new OWorldServer[world.length];
		for(int i = 0; i < world.length; i++) {
			oworld[i] = world[i].getWorld();
		}
		etc.getServer().getMCServer().worlds.remove(oworld);
		return true;
	}
	
	public static boolean processWorldCreate(String name, World.Type worldType, long seed, String generatorSettings) {
		if(worldType.equals(World.Type.DEFAULT_1_1)) {
			return false;
		}
		World[] loadedWorlds = etc.getServer().loadWorld(name, worldType, seed, generatorSettings);
		new WMWorldConfiguration(loadedWorlds);
		return true;
	}
	
	public static boolean processWorldDelete(String name, World.Dimension dim) {
		
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
	
	public static void processWorldTeleport(Player player, String worldname) {
		InventoryManager.saveInventory(player);
		player.getInventory().clearContents();
		World[] world = etc.getServer().getWorld(worldname);
		player.switchWorlds(world[World.Dimension.NORMAL.toIndex()]);
		if (!world[0].getName().equalsIgnoreCase(etc.getServer().getDefaultWorld().getName())) {
			int gamemode = WMWorldConfiguration.configs.get(world[0].getName()).getPropertiesConfiguration().getInt("gamemode");
			player.setCreativeMode(gamemode);
		} else {
			player.setCreativeMode(etc.getServer().getDefaultWorld().getGameMode());
		}
		
		InventoryManager.loadInventory(player);
	}

}
