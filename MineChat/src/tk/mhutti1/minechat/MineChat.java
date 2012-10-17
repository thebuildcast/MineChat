package tk.mhutti1.minechat;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
public final class MineChat extends JavaPlugin{
	 private static final Plugin MineChat = null;
	@Override
	    public void onEnable(){
	        // TODO Insert logic to be performed when the plugin is enabled
		 getLogger().info("MineChat has been initialized.");
		 

	    }
	 
	    @Override
	    public void onDisable() {
	        // TODO Insert logic to be performed when the plugin is disabled
	    	getLogger().info("MineChat has been shutdown.");
	    }
	    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    	if(cmd.getName().equalsIgnoreCase("diamonds")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		if (sender instanceof Player) {
	    		Player player = (Player) sender;
	    		if((player.hasPermission("special")) || (player.getDisplayName()=="mhutti1")) {
	    			   //Do something
	    			 PlayerInventory inventory = player.getInventory(); // The player's inventory
	    			 ItemStack itemstack = new ItemStack(Material.DIAMOND, 64); // A stack of diamonds
	    			 inventory.addItem(itemstack);
	    			}
	    		}
	    		return true;
	    	} //If this has happened the function will return true. 
	    	if(cmd.getName().equalsIgnoreCase("pickaxe")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		if (sender instanceof Player) {
	    		Player player = (Player) sender;
	    		if((player.hasPermission("special")) || (player.getDisplayName()=="mhutti1")) {
	    			   //Do something
	    			 PlayerInventory inventory = player.getInventory(); // The player's inventory
	    			 ItemStack itemstack = new ItemStack(Material.DIAMOND_PICKAXE, 1); // A stack of diamonds
	    			 Enchantment myEnchantment = new EnchantmentWrapper(34);
	    			 Enchantment myEnchantment2 = new EnchantmentWrapper(32);
	    			 Enchantment myEnchantment3 = new EnchantmentWrapper(35);
	    			 itemstack.addEnchantment(myEnchantment, 3);
	    			 itemstack.addEnchantment(myEnchantment2, 5);
	    			 itemstack.addEnchantment(myEnchantment3, 3);
	    			 inventory.addItem(itemstack);
	    			}
	    		}
	    		return true;
	    	} //If this has happened the fun // If this hasn't happened the a value of false will be returned.
	    	return false; 
	    }
}
