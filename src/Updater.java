import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


public class Updater {

	private static String updateURL = "https://dl-web.dropbox.com/get/worldmanager.txt?w=AAD5MCRloP_vbZo2luROQibyp234MoBPb5FxhzAQ6Uz3gg";
	
	
	public static boolean isUpdateAvailable() throws IOException {
		URL url = new URL(updateURL);
		Scanner sc = new Scanner(url.openStream());
		
		String line = "";
		if(sc.hasNextLine()) {
			line = sc.nextLine();
		}
		sc.close();
		
		return new Double(Double.parseDouble(line)).compareTo(new Double(Double.parseDouble(WorldManager.version))) > 0 ;
	}
	
	public static String getNewestVersion() throws IOException {
		URL url = new URL(updateURL);
		Scanner sc = new Scanner(url.openStream());
		
		String line = "";
		if(sc.hasNextLine()) {
			line = sc.nextLine();
		}
		sc.close();
		return line;
		
	}

}
