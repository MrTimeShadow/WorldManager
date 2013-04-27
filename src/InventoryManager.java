import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
					item.setDataTag(loadNBTData(nbtPath, ""));
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
						saveNBTData(i.getDataTag().getValues(), new File(basePath, "/nbt/" + i.getSlot() + "/"));
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			WorldManager.mclogger.severe("[WorldManager] Failed to save Inventory of " + player.getName());
		}
	}
	
	private static void saveNBTData(Collection<NBTBase> c, File baseDir) throws IOException {
		if(!baseDir.exists()) {
			baseDir.mkdirs();
		}
		for(NBTBase b : c) {
			File nbtFile = new File(baseDir, b.getName() + ".nbt");
			if (nbtFile.exists()) {
				nbtFile.delete();
			}
			nbtFile.createNewFile();
			FileWriter fw = new FileWriter(nbtFile);
			
			fw.write("[" + b.getClass().getSimpleName() + "]\n");
			switch(b.getType()) {
				case 1:
					fw.append("" + ((NBTTagByte)b).getValue());
					break;
				case 2:
					fw.append("" + ((NBTTagShort)b).getValue());
					break;
				case 3:
					fw.append("" + ((NBTTagInt)b).getValue());
					break;
				case 4:
					fw.append("" + ((NBTTagLong)b).getValue());
					break;
				case 5:
					fw.append("" + ((NBTTagFloat)b).getValue());
					break;
				case 6:
					fw.append("" + ((NBTTagDouble)b).getValue());
					break;
				case 7:
					StringBuilder sb = new StringBuilder();
					for(byte by : ((NBTTagByteArray)b).getValue())
						sb.append(by + ":");
					fw.append(sb.toString().substring(0, sb.toString().length() - 1));
					break;
				case 8:
					fw.append(((NBTTagString)b).getValue());
					break;
				case 9:
					saveNBTData(Arrays.asList(((NBTTagList)b).getValues()), new File(baseDir, "/NBTTagList/" + b.getName() + "/"));
					break;
				case 10:
					saveNBTData(((NBTTagCompound)b).getValues(), new File(baseDir, "/" + b.getName() + "/"));
					break;
				case 11:
					StringBuilder sb2 = new StringBuilder();
					for(int i : ((NBTTagIntArray)b).getValue())
						sb2.append(i + ":");
					fw.append(sb2.toString().substring(0, sb2.toString().length() - 1));
					break;
			}
			fw.close();
		}
	}
	
	private static NBTTagCompound loadNBTData(File baseDir, String compoundName) throws IOException {
		NBTTagCompound compound = (NBTTagCompound) NBTBase.wrap(new ONBTTagCompound(compoundName));
		File[] files = baseDir.listFiles();
		if(files != null) {
			for(File f : files) {
				if(f.isDirectory() && !f.getName().equalsIgnoreCase("NBTTagList")) {
					NBTTagCompound subcompound = loadNBTData(f, f.getName());
					compound.add(subcompound.getName(), subcompound);
				} else if(f.isDirectory()) {
					if(f.listFiles() != null)
						for(File tagList : f.listFiles()) {
							NBTTagList l = (NBTTagList) NBTBase.wrap(new ONBTTagList(tagList.getName()));
							if(tagList.listFiles() != null)
								for(File tag : tagList.listFiles()) {
									if(tag.isDirectory()) {
										l.add(loadNBTData(tag, tag.getName()));
									} else {
										NBTBase nbttag = readNBTTag(tag);
										if(nbttag != null)
										l.add(nbttag);
									}
								}
							compound.add(l.getName(), l);
						}
				} else {
					if(f.getName().endsWith(".nbt")) {
						NBTBase tag = readNBTTag(f);
						if(tag != null)
							compound.add(tag.getName(), tag);
					}
				}
			}
		}
		return compound;
	}
	
	private static NBTBase readNBTTag(File nbtTag) throws IOException {
		if(nbtTag.isDirectory()) {
			throw new IllegalStateException("f shouldn't be null");
		}
		Scanner sc = new Scanner(nbtTag);
		ArrayList<String> lines = new ArrayList<String>();
		while(sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		if(lines.size() < 1) {
			return null;
		}
		NBTBase base = null;
		switch(lines.get(0)) {
			case "[NBTTagByte]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagByte(nbtTag.getName()));
				} else {
					byte b = Byte.parseByte(lines.get(1));
					base = NBTBase.wrap(new ONBTTagByte(nbtTag.getName(), b));
				}
				break;
			case "[NBTTagShort]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagShort(nbtTag.getName()));
				} else {
					short s = Short.parseShort(lines.get(1));
					base = NBTBase.wrap(new ONBTTagShort(nbtTag.getName(), s));
				}
				break;
			case "[NBTTagInt]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagInt(nbtTag.getName()));
				} else {
					int i = Integer.parseInt(lines.get(1));
					base = NBTBase.wrap(new ONBTTagInt(nbtTag.getName(), i));
				}
				break;
			case "[NBTTagLong]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagLong(nbtTag.getName()));
				} else {
					long l = Long.parseLong(lines.get(1));
					base = NBTBase.wrap(new ONBTTagLong(nbtTag.getName(), l));
				}
				break;
			case "[NBTTagFloat]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagFloat(nbtTag.getName()));
				} else {
					float f = Float.parseFloat(lines.get(1));
					base = NBTBase.wrap(new ONBTTagFloat(nbtTag.getName(), f));
				}
				break;
			case "[NBTTagDouble]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagDouble(nbtTag.getName()));
				} else {
					double d = Double.parseDouble(lines.get(1));
					base = NBTBase.wrap(new ONBTTagDouble(nbtTag.getName(), d));
				}
				break;
			case "[NBTTagByteArray]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagByteArray(nbtTag.getName()));
				} else {
					String[] sbytes = lines.get(1).split(":");
					byte[] bytes = new byte[sbytes.length];
					for(int i = 0; i < sbytes.length; i++) {
						bytes[i] = Byte.parseByte(sbytes[i]);
					}
					base = NBTBase.wrap(new ONBTTagByteArray(nbtTag.getName(), bytes));
				}
				break;
			case "[NBTTagString]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagString(nbtTag.getName()));
				} else {
					StringBuilder sb = new StringBuilder();
					for(int i = 1; i < lines.size(); i++) {
						sb.append(lines.get(i) + "\n");
					}
					base = NBTBase.wrap(new ONBTTagString(nbtTag.getName(), sb.toString()));
				}
				break;
			case "[NBTTagIntArray]":
				if(lines.size() < 2) {
					base = NBTBase.wrap(new ONBTTagIntArray(nbtTag.getName()));
				} else {
					String[] sintegers = lines.get(1).split(":");
					int[] integers = new int[sintegers.length];
					for(int i = 0; i < sintegers.length; i++) {
						integers[i] = Byte.parseByte(sintegers[i]);
					}
					base = NBTBase.wrap(new ONBTTagIntArray(nbtTag.getName(), integers));
				}
				break;
		}
		
		
		return base;
	}
	
	
}
