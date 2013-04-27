import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerLocationsFile {
	private static PlayerLocationsFile instance = new PlayerLocationsFile(new File(WorldManager.configurationPath, "playerlocations.txt"));

	private File playerLocationsFile;
	private ArrayList<PlayerLocation> playerLocations = new ArrayList<>();

	private PlayerLocationsFile(File playerLocationsFile) {
		this.playerLocationsFile = playerLocationsFile;
	}

	public void addPlayer(Player player) {
		if (this.playerLocations.contains(new PlayerLocation(player))) {
			return;
		}
		this.playerLocations.add(new PlayerLocation(player));
	}

	public void updateLocation(Player player) {
		for (int i = 0; i < this.playerLocations.size(); i++) {
			if (playerLocations.get(i).playerName.equalsIgnoreCase(player.getName())) {
				this.playerLocations.set(i, new PlayerLocation(player));
				return;
			}
		}
	}

	public void removePlayer(Player player) {
		for (int i = 0; i < this.playerLocations.size(); i++) {
			if (this.playerLocations.get(i).equals(player.getName())) {
				this.playerLocations.remove(i);
			}
		}
	}

	public boolean hasPlayer(Player player) {
		return this.playerLocations.contains(new PlayerLocation(player));
	}

	public PlayerLocation getLocationOf(Player player) {
		for (int i = 0; i < this.playerLocations.size(); i++) {
			if (this.playerLocations.get(i).playerName.equalsIgnoreCase(player.getName())) {
				return this.playerLocations.get(i);
			}
		}
		return null;
	}

	public void save() {
		FileWriter fw;
		try {
			fw = new FileWriter(playerLocationsFile);
			for (PlayerLocation lc : this.playerLocations) {
				fw.append(lc.toString());
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		Scanner sc;
		try {
			sc = new Scanner(playerLocationsFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (!line.startsWith("#")) {
					this.playerLocations.add(PlayerLocation.fromString(line));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static PlayerLocationsFile getInstance() {
		return instance;
	}
}
