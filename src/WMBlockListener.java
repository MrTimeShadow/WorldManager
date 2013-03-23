
public class WMBlockListener extends PluginListener {

	public boolean onBlockBreak(Player player, Block block) {
		
		
		return false;
	}
	
	public boolean onBlockCreate(Player player, Block blockPlaced, Block blockClicked, int itemInHand) {
		
		return false;
	}
	
	public boolean onBlockDestroy(Player player, Block block) {
		
		return false;
	}
	
	public boolean onBlockPhysics(Block block, boolean placed) {
		
		return false;
	}
	
	public boolean onBlockPlace(Player player, Block blockPlaced, Block blockClicked, Item itemInHand) {
		
		return false;
	}
	
	public void onBlockRightClicked(Player player, Block blockClicked, Item itemInHand) {
		
	}
	
	public boolean onBlockUpdate(Block block, int newBlockId) {
		
		return false;
	}
	
	
	
}
