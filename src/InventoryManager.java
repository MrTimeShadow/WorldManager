import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class InventoryManager {

	private static File inventoryPath = new File("config/worldmanager/inventories");
	
	public static void loadInventory(Player player, int inventoryID) {
		if(!inventoryPath.exists()) {
			inventoryPath.mkdir();
		}
		
		File f = new File(inventoryPath, "" + inventoryID + "/" + player.getName() + ".dat");
		
		ArrayList<String> lines = new ArrayList<String>();

		try {
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
				return;
			}

			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
		} catch (IOException e) {
			WorldManager.mclogger.severe("[WorldManager] Failed to load Inventory of " + player.getName());
			WorldManager.mclogger.severe("Cause: "  + e.getMessage());
		}
		
		ArrayList<Item> tempList = new ArrayList<Item>();
		for (int i = 1; i < lines.size(); i++){
			String[] splitLine = lines.get(i).split(":");
			
			Item item = new Item(Integer.valueOf(splitLine[0]), Integer.valueOf(splitLine[1]), Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3]));
			item.setName(String.valueOf(splitLine[4]));
			tempList.add(item);
		}
		
		Item[] items = new Item[tempList.size()];
		
		for (int i = 0; i < tempList.size(); i++) {
			items[i] = tempList.get(i);
		}
		
		WorldManager.mclogger.info("[WorldManager] Length of " + player.getName() + " is " + items.length);
		if (items != null && items.length > 0) {
			player.getInventory().clearContents();
			player.getInventory().setContents(items);
		}
		
		
	}
	
	
	public static void saveInventory(Player player, int inventoryID) {
		Item[] items = player.getInventory().getContents();
		
		BufferedWriter br;
		try {
			br = new BufferedWriter(new FileWriter(new File(inventoryPath, "" + inventoryID + "/" + player.getName() + ".dat")));

			for (Item i : items) {
				if (i != null) {
					br.write(i.getItemId() + ":" + i.getAmount() + ":" + i.getSlot() + ":" + i.getDamage() + ":" + i.getName() + "\n");
				}
			}
			br.close();
		} catch (IOException e) {
			WorldManager.mclogger.severe("[WorldManager] Failed to save Inventory of " + player.getName());
		}
	}
}
