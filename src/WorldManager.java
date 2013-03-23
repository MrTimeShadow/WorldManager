import java.util.logging.Logger;

public class WorldManager extends Plugin {

	public static final String NAME = "WorldManager";
	public static final String VERSION = "0.0.1";
	public static final String AUTHOR = "MrTimeShadow";
	public static final Logger mclogger = Logger.getLogger("Minecraft");
	
	private WMCommandListener commandListener = new WMCommandListener();
	private WMPlayerListener playerListener = new WMPlayerListener();
	private WMBlockListener blockListener = new WMBlockListener();
	
	@Override
	/**
	 * Registers the Listener for the needed Hooks
	 */
	public void initialize() {
		this.setName("WorldManager v0.0.1 by MrTimeShadow");
		mclogger.info("[WorldManager] Initializing WorldManager!");
		PluginLoader loader = etc.getLoader();
		
		this.addCommandListeners(loader);
		this.addPlayerListeners(loader);
		this.addBlockListeners(loader);
		
		mclogger.info("[WorldManager] Successfully enabled WorldManager");
		
	}
	
	@Override
	/**
	 * Registers the commands
	 */
	public void enable() {
		mclogger.info("[WorldManager] Enabling WorldManager!");
		etc.getInstance().addCommand("/wm", "Displays the Help of WorldManager"); //Adds the Command to the help list
		PlayerLocationsFile.getInstance().load();
	}

	@Override
	/**
	 * Removes the commands
	 */
	public void disable() {
		etc.getInstance().removeCommand("/wm"); //Removes the Command from the Help list
		PlayerLocationsFile.getInstance().save();
		mclogger.info("[WorldManager] Successfully disabled WorldManager!");
	}
	
	
	private void addCommandListeners(PluginLoader loader) {
		loader.addListener(PluginLoader.Hook.COMMAND, commandListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.COMMAND_CHECK, commandListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.SERVERCOMMAND, commandListener, this, PluginListener.Priority.MEDIUM);
	}
	
	private void addPlayerListeners(PluginLoader loader) {
		loader.addListener(PluginLoader.Hook.LOGIN, playerListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.DISCONNECT, playerListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BAN, playerListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.KICK, playerListener, this, PluginListener.Priority.MEDIUM);
	}
	
	private void addBlockListeners(PluginLoader loader) {
		loader.addListener(PluginLoader.Hook.BLOCK_BROKEN, blockListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BLOCK_CREATED, blockListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BLOCK_DESTROYED, blockListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BLOCK_PHYSICS, blockListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BLOCK_PLACE, blockListener, this, PluginListener.Priority.MEDIUM);
		loader.addListener(PluginLoader.Hook.BLOCK_UPDATE, blockListener, this, PluginListener.Priority.MEDIUM);
	}

}
