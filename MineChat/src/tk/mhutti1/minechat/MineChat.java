package tk.mhutti1.minechat;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
public final class MineChat extends JavaPlugin{
	 private static final Plugin MineChat = null;
	 boolean shutdown;
	@Override
	    public void onEnable(){
	        // TODO Insert logic to be performed when the plugin is enabled
		 getLogger().info("MineChat has been initialized.");
		 shutdown = true;
		 try{
			 
		 //set the update checker url
		 final URL url = new URL("http://www.buildcast-tinywebdb.appspot.com/update");
		 URLConnection con = url.openConnection();
		 InputStream in = con.getInputStream();
		 String encoding = con.getContentEncoding();
		 encoding = encoding == null ? "UTF-8" : encoding;
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 byte[] buf = new byte[8192];
		 int len = 0;
		 while ((len = in.read(buf)) != -1) {
		     baos.write(buf, 0, len);
		 }
		 String body = new String(baos.toByteArray(), encoding);
		 getLogger().info(body);
	    }
	        catch (UnknownHostException e)
	        {
	           
	        }
	        catch (IOException e)
	        {
	           
	        }
		 Thread t = new Thread() {
			    @Override
			    public void run() {
			        while(shutdown = true) {
			        	try {
							Thread.sleep(1000*5);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			            try {
			             
			             final URL url = new URL("http://www.buildcast-tinywebdb.appspot.com/update");
			       		 URLConnection con = url.openConnection();
			       		 InputStream in = con.getInputStream();
			       		 String encoding = con.getContentEncoding();
			       		 encoding = encoding == null ? "UTF-8" : encoding;
			       		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			       		 byte[] buf = new byte[8192];
			       		 int len = 0;
			       		 while ((len = in.read(buf)) != -1) {
			       		     baos.write(buf, 0, len);
			       		 }
			       		 String body = new String(baos.toByteArray(), encoding);
			       	  getServer().broadcastMessage(body);
			       	    }
			       	        catch (UnknownHostException e)
			       	        {
			       	           
			       	        }
			       	        catch (IOException e)
			       	        {
			       	           
			       	        }
			             
			            }
			        
			    }
			};
			t.start();
	}

	 
	    @Override
	    public void onDisable() {
	        // TODO Insert logic to be performed when the plugin is disabled
	    	getLogger().info("MineChat has been shutdown.");
	    	shutdown = false;
	    }
	    public void doSubmit(String url, Map<String, String> data) throws Exception {
			URL siteUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			
			Set keys = data.keySet();
			Iterator keyIter = keys.iterator();
			String content = "";
			for(int i=0; keyIter.hasNext(); i++) {
				Object key = keyIter.next();
				if(i!=0) {
					content += "&";
				}
				content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
			}
			System.out.println(content);
			out.writeBytes(content);
			out.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while((line=in.readLine())!=null) {
				System.out.println(line);
			}
			in.close();
		}


	    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    	if(cmd.getName().equalsIgnoreCase("getverifcode")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		//args[0];
	    		Random generator = new Random();
	    		int randomIndex = generator.nextInt( 10000000 );
	    		Map<String, String> data = new HashMap<String, String>();
	    		data.put("user", args[0]);
	    		data.put("code", String.valueOf(randomIndex));
	    		return true;
	    	} //If this has happened the function will return true. 
	    	/*if(cmd.getName().equalsIgnoreCase("pickaxe")){ // If the player typed /basic then do the following...
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
	    		
	    	} *///If this has happened the fun // If this hasn't happened the a value of false will be returned.
	    	return false; 
	    }
}
//permission: <plugin name>.mhutti1.special
//permission-message: You don't have <permission>
//permissions:
//special:
//default: op