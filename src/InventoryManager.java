import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class InventoryManager {

	private static File inventoryPath = new File("config/worldmanager/inventories");
	
	public static void loadInventory(Player player) {
		String playerworldname = player.getWorld().getName();
		String defaultworldname = etc.getServer().getDefaultWorld().getName();
		InventoryManager.loadInventory(player, playerworldname.equalsIgnoreCase(defaultworldname) ? 0 : WMWorldConfiguration.configs.get(playerworldname).getPropertiesConfiguration().getInt("inventoryId"));
	}

	private static void loadInventory(Player player, int inventoryID) {
		if(!inventoryPath.exists()) {
			inventoryPath.mkdir();
		}
		
		File basePath = new File(inventoryPath, "" + inventoryID + "/" + player.getName() + "/");
		File itemFile = new File( basePath, "/items.dat");
		
		ArrayList<String> lines = new ArrayList<String>();

		try {
			if(!basePath.exists()) {
				basePath.mkdirs();
				return;
			}
			if (!itemFile.exists()) {
				itemFile.getParentFile().mkdirs();
				itemFile.createNewFile();
				return;
			}

			Scanner sc = new Scanner(itemFile);
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
		} catch (IOException e) {
			WorldManager.mclogger.severe("[WorldManager] Failed to load Inventory of " + player.getName());
			WorldManager.mclogger.severe("Cause: "  + e.getMessage());
		}
		
		Item[] items = new Item[player.getInventory().getContentsSize()];
		for (int i = 0; i < lines.size(); i++){
			String[] splitLine = lines.get(i).split(":");
			
			Item item = new Item(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]));
			if (!splitLine[4].equals(item.getName())) {
				item.setName(splitLine[4]);
			}
			File nbtPath = new File(basePath, "/nbt/" + item.getSlot() + "/");
			if(nbtPath.exists()) {
				try {
					item.setDataTag(NBTManager.loadNBTData(nbtPath, ""));
				} catch (IOException e) {
					e.printStackTrace();
					WorldManager.mclogger.severe("[WorldManager] Could not Load NBT Data of item \"" + item.getName() + "\" at slot " + item.getSlot() + "! The item may be unusable now!");
				}
			}
			items[item.getSlot()] = item;
		}
		
		//WorldManager.mclogger.info("[WorldManager] Length of " + player.getName() + " is " + items.length);
		if (items != null && items.length > 0) {
			player.getInventory().clearContents();
			player.getInventory().setContents(items);
		}
		
		
	}
	
	
	public static void saveInventory(Player player) {
		String worldname = player.getWorld().getName();
		String defaultworldname = etc.getServer().getDefaultWorld().getName();
		InventoryManager.saveInventory(player, worldname.equalsIgnoreCase(defaultworldname) ? 0 : WMWorldConfiguration.configs.get(worldname).getPropertiesConfiguration().getInt("inventoryId"));
	}
	
	private static void saveInventory(Player player, int inventoryID) {
		Item[] items = player.getInventory().getContents();
		
		BufferedWriter br;
		try {
			File basePath = new File(inventoryPath, "" + inventoryID + "/" + player.getName() + "/");
			if(!basePath.exists()) {
				basePath.mkdirs();
			}
			File itemsFile = new File(basePath, "/items.dat");
			if(itemsFile.exists()) {
				itemsFile.delete();
			}
			itemsFile.createNewFile();
			br = new BufferedWriter(new FileWriter(itemsFile));

			for (Item i : items) {
				if (i != null) {
					br.write(i.getItemId() + ":" + i.getAmount() + ":" + i.getSlot() + ":" + i.getDamage() + ":" + i.getName() + "\n");
					if(i.getDataTag() != null) {
						NBTManager.saveNBTData(i.getDataTag().getValues(), new File(basePath, "/nbt/" + i.getSlot() + "/"));
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			WorldManager.mclogger.severe("[WorldManager] Failed to save Inventory of " + player.getName());
		}
	}
	
}