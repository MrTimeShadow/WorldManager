
public class WMHookListener extends PluginListener {

	public void onLogin(Player player) {
		if(!PlayerLocationsFile.getInstance().hasPlayer(player)) {
			PlayerLocationsFile.getInstance().addPlayer(player);
		} else {
			PlayerLocation pl = PlayerLocationsFile.getInstance().getLocationOf(player);
			/*World playerWorld = player.getWorld();
			playerWorld.removePlayerFromWorld(player);*/
			World[] world = etc.getServer().getWorld(pl.worldName);
			//world[0].addPlayerToWorld(player);
			player.switchWorlds(world[World.Dimension.NORMAL.toIndex()]);
			player.setX(pl.x);
			player.setY(pl.y);
			player.setZ(pl.z);
		}
	}
	
	public void onDisconnect(Player player) {
		PlayerLocationsFile.getInstance().updateLocation(player);
	}
	public void onBan(Player mod, Player player, String reason) {
		PlayerLocationsFile.getInstance().updateLocation(player);
	}
	public void onKick(Player mod, Player player, String reason) {
		PlayerLocationsFile.getInstance().updateLocation(player);
	}
	
}
