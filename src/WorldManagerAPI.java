
public class WorldManagerAPI implements PluginInterface {

	public static final WorldManagerAPI instance = new WorldManagerAPI();
	
	private WorldManagerAPI() {};
	
	public static final String CREATE_WORLD = "CREATE_WORLD";
	public static final String LOAD_WORLD = "LOAD_WORLD";
	public static final String DELETE_WORLD = "DELETE_WORLD";
	public static final String UNLOAD_WORLD = "UNLOAD_WORLD";
	public static final String TELEPORT_PLAYER = "TELEPORT_PLAYER";
	public static final String GET_INVENTORY_ID = "GET_INVENTORY_ID";
	public static final String GET_ALLOWED_PLAYERS = "GET_ALLOWED_PLAYERS";
	public static final String ADD_TO_ALLOWED_PLAYERS = "ADD_TO_ALLOWED_PLAYERS";
	public static final String REMOVE_FROM_ALLOWED_PLAYERS = "REMOVE_FROM_ALLOWED_PLAYERS";
	public static final String GET_ALLOWED_GROUPS = "GET_ALLOWED_GROUPS";
	public static final String ADD_TO_ALLOWED_GROUPS = "ADD_TO_ALLOWED_GROUPS";
	public static final String REMOVE_FROM_ALLOWED_GROUPS = "REMOVE_FROM_ALLOWED_GROUPS";
	public static final String SAVE_PLAYER_INVENTORY = "SAVE_PLAYER_INVENTORY";
	public static final String UPDATE_PLAYER_LOCATION = "UPDATE_PLAYER_LOCATION";
	
	@Override
	public String checkParameters(Object[] arg0) {
		return null; //null == valid, i am to lazy to check the parameters for every hook
	}

	@Override
	public String getName() {
		return "WorldManager-API";
	}

	@Override
	public int getNumParameters() {
		return -1;
	}

	@Override
	public Object run(Object[] arg0) {
		return null;
	}


}
