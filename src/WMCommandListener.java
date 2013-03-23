import java.util.Random;



public class WMCommandListener extends PluginListener {
	
	public boolean onCommand(Player player, String[] split) {
		if(split[0].equalsIgnoreCase("/wm") && player.canUseCommand("/wm")) {
			
			if(split.length == 1) {
				this.sendHelpMsg(player);
			}
			WMCommandExecutor executor = new WMCommandExecutor();
			
			switch(split[1].toLowerCase()) {
				case "load":
					if(split.length < 3) {
						this.sendHelpMsg(player);
					}
					executor.executeLoadWorld(split[2]);
					break;
				case "unload":
					WMWorldManager.getInstance().unloadWorld(etc.getServer().getWorld(split[2]));
					break;
					
				case "create":
					
					World.Type type;
					switch(split[3].toLowerCase()) {
						case "default":
							type = World.Type.DEFAULT;
							break;
						case "flat":
							type = World.Type.FLAT;
							break;
						case "largebiomes":
							type = World.Type.LARGE_BIOMES;
							break;
						default:
							this.sendHelpMsg(player);
							return true;
					}
					
					if (split.length == 4) {
						executor.executeCreateWorld(split[2], type, new Random(System.currentTimeMillis()).nextLong(), "");
					} else if (split.length == 5) {
						long seed = 0;
						try {
							seed = Long.parseLong(split[4]);
							executor.executeCreateWorld(split[2], type, seed, "");
						} catch(Exception e) {
							executor.executeCreateWorld(split[2], type, new Random(System.currentTimeMillis()).nextLong(), split[4]);
						}
					} else if(split.length == 6) {
						executor.executeCreateWorld(split[2], type, Long.parseLong(split[4]), split[5]);
					} else {
						this.sendHelpMsg(player);
					}
					
					break;
					
				case "delete":
					break;
					
				case "tp":
					if(split.length < 3) {
						this.sendHelpMsg(player);
					}
					World[] world = etc.getServer().getWorld(split[2]);
					player.switchWorlds(world[World.Dimension.NORMAL.toIndex()]);
					int gamemode = WMWorldConfiguration.configs.get(world[0].getName()).getPropertiesConfiguration().getInt("gamemode");
					player.setCreativeMode(gamemode);
					break;
				default:
					this.sendHelpMsg(player);
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean onConsoleCommand(String[] split) {
		return this.onCommand(new PlayerConsole(), split);
	}
	
	public PluginLoader.HookResult canPlayerUseCommand(Player player, String command) {
		return PluginLoader.HookResult.DEFAULT_ACTION;
	}
	
	private void sendHelpMsg(Player player) {
		player.notify("WorldManager Help:");
		player.notify("Commands:");
		player.notify("/wm load <name>");
		player.notify("     Loads an existing World");
		player.notify("/wm unload <name>");
		player.notify("     Unloads an existing World");
		player.notify("/wm create <name> <DEFAULT|FLAT|LARGEBIOMES> [Seed] [Generator Settings]");
		player.notify("     Creates a new World");
		player.notify("/wm delete <name>");
		player.notify("     Deletes an existing World");
		player.notify("/wm tp <worldname>");
		player.notify("     Teleports you to a World");
	}

}
