import java.util.logging.Logger;


public class PlayerConsole extends Player {

	private static Logger consoleLogger = Logger.getLogger("Minecraft");
	
	
	@Override public void sendMessage(String message) {
		consoleLogger.info(message);
	}
	@Override public void notify(String message) {
		consoleLogger.info(message);
	}
}
