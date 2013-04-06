public class PlayerLocation {

	public String playerName;
	public int x;
	public int y;
	public int z;
	public String worldName;

	public PlayerLocation(Player player) {
		this.playerName = player.getName();
		this.x = (int) Math.ceil(player.getX());
		this.y = (int) Math.floor(player.getY());
		this.z = (int) Math.ceil(player.getZ());
		this.worldName = player.getWorld().getName();
	}

	public PlayerLocation(String playerName, int x, int y, int z, String worldName) {
		this.playerName = playerName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
	}

	public String toString() {
		return playerName + ":" + x + ":" + y + ":" + z + ":" + worldName;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PlayerLocation) {
			return this.playerName.equalsIgnoreCase(((PlayerLocation) obj).playerName);
		}
		return false;
	}

	public static PlayerLocation fromString(String line) {
		String[] split = line.split(":");
		String playerName = split[0];
		int x = Integer.parseInt(split[1]);
		int y = Integer.parseInt(split[2]);
		int z = Integer.parseInt(split[3]);
		String worldName = split[4];
		return new PlayerLocation(playerName, x, y, z, worldName);
	}

}
