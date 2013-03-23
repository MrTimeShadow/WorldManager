import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class WMWorldConfigFile {

	public static HashMap<String, WMWorldConfigFile> worldConfigs = new HashMap<String, WMWorldConfigFile>();
	
	private String worldName;
	public final PropertiesFile propertiesFile;
	
	
	public WMWorldConfigFile(String worldName) {
		this.worldName = worldName;
		try {
			this.createFileIfNotExisting();
			this.sortFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		propertiesFile = new PropertiesFile("/worldmanager/worlds/",worldName + ".properties");
		worldConfigs.put(worldName, this);
	}
	
	private void createFileIfNotExisting() throws IOException {
		File f = new File("/worldmanager/worlds/" + this.worldName + ".properties");
		File path = new File("/worldmanager/worlds/");
		if(!path.exists()) {
			path.mkdirs();
		}
		if(!f.exists()) {
			f.createNewFile();
			Scanner sc = new Scanner(this.getClass().getResourceAsStream("worldname.properties"));
			ArrayList<String> lines = new ArrayList<String>();
			while(sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
			FileWriter fw = new FileWriter(f);
			for(String line : lines) {
				fw.append(line + "\n");
			}
			fw.flush();
			fw.close();
		}
	}
	
	private void sortFile() throws IOException {
		File f = new File("/worldmanager/worlds/" + this.worldName + ".properties");
		Scanner sc = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		while(sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		String[] linesArray = new String[lines.size()];
		linesArray = lines.toArray(linesArray);
		Arrays.sort(linesArray);
		FileWriter fw = new FileWriter(f);
		for(String line : linesArray) {
			fw.append(line + "\n");
		}
		fw.flush();
		fw.close();
	}
	
}
