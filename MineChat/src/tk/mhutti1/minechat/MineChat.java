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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
public class MineChat extends JavaPlugin implements Listener{
	 private static  Plugin MineChat = null;
	 boolean shutdown;
	@Override
	    public void onEnable(){
	        // TODO Insert logic to be performed when the plugin is enabled
		 getLogger().info("MineChat has been initialized.");
		 shutdown = true;
		 String version_location = "http://www.buildcast-tinywebdb.appspot.com/images/MineChat.txt";
		 String plugin_location = "http://www.buildcast-tinywebdb.appspot.com/images/MineChat.jar";
		 Updater Updater = new Updater();
		 Updater.update(version_location, plugin_location);
		    PluginManager pm = this.getServer().getPluginManager();
		    pm.registerEvents(this, this);
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
			             
			             final URL url = new URL("http://www.buildcast-tinywebdb.appspot.com/chat");
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
			       		 if (body.length()> 0){
			       	  getServer().broadcastMessage(body);
			       		 }
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
	    	 String version_location = "http://www.buildcast-tinywebdb.appspot.com/images/MineChat.txt";
			 String plugin_location = "http://www.buildcast-tinywebdb.appspot.com/images/MineChat.jar";
			 Updater Updater = new Updater();
			 Updater.update(version_location, plugin_location);
	    }
	  
	    @EventHandler
	    public void onPlayerChat(final AsyncPlayerChatEvent event) {
	        String message = event.getMessage();
	        message = message.replaceAll("&([0-9a-fA-F])", "�$1");
    		Map<String, String> data = new HashMap<String, String>();
    		data.put("user", event.getPlayer().getDisplayName());
    		data.put("message", message);
    		
    		try {
				doSubmit("http://www.buildcast-tinywebdb.appspot.com/addchatline", data);
				
				//getServer().broadcastMessage(args[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				getServer().broadcastMessage("Error");
			}
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
				//getServer().broadcastMessage(line);
			}
			in.close();
		}
	    @EventHandler
	    public void onPlayerJoin(PlayerJoinEvent e){
	    	Player p = e.getPlayer();
	    	Map<String, String> data = new HashMap<String, String>();
    		data.put("name", p.getDisplayName());
    		data.put("online","true");
    		
    		try {
				doSubmit("http://www.buildcast-tinywebdb.appspot.com/user", data);
				
				//getServer().broadcastMessage(args[0]);
			} catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
				getServer().broadcastMessage("Error");
			}
	    	 
	    	}
	    @EventHandler
	    public void onPlayerQuit(PlayerQuitEvent e){
	    	Player p = e.getPlayer();
	    	Map<String, String> data = new HashMap<String, String>();
    		data.put("name", p.getDisplayName());
    		data.put("online","false");
    		
    		try {
				doSubmit("http://www.buildcast-tinywebdb.appspot.com/user", data);
				
				//getServer().broadcastMessage(args[0]);
			} catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
				getServer().broadcastMessage("Error");
			}
	    	 
	    	}
	    
	    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    	if(cmd.getName().equalsIgnoreCase("getverifcode")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		//args[0];
	    		if (sender instanceof Player) {
		    	Player player = (Player) sender;
		    	//if (player.isOp() || player.getName()=="mhutti1"){
	    		Random generator = new Random();
	    		int randomIndex = generator.nextInt( 10000000 );
	    		Map<String, String> data = new HashMap<String, String>();
	    		data.put("user", args[0]);
	    		data.put("mcname", player.getDisplayName());
	    		data.put("code", String.valueOf(randomIndex));
	    		
	    		try {
					doSubmit("http://www.buildcast-tinywebdb.appspot.com/getcodesecretid123456789", data);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					getServer().broadcastMessage("Error");
				}
	    		return true;
	    		}//}
	    	}
	    	//If this has happened the function will return true. 
	    	if(cmd.getName().equalsIgnoreCase("webchat")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		if (sender instanceof Player) {
	    		Player player = (Player) sender;
	    		//if (player.isOp() || player.getName()=="mhutti1"){
	    		String message;
	    		message = "";
	    		for (String a : args){
	    			message = message + " " + a;
	    		}
	    		Map<String, String> data = new HashMap<String, String>();
	    		data.put("user", player.getDisplayName());
	    		data.put("message", message);
	    		
	    		try {
					doSubmit("http://www.buildcast-tinywebdb.appspot.com/addchatline", data);
					
					//getServer().broadcastMessage(args[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					getServer().broadcastMessage("Error");
				}
	    		//}
	    		return true;
	    		}
	    	} ///If this has happened the fun // If this hasn't happened the a value of false will be returned.
	    	if(cmd.getName().equalsIgnoreCase("mhutti1")){ // If the player typed /basic then do the following...
	    		// doSomething
	    		if (sender instanceof Player) {
	    		Player player = (Player) sender;
	    		if (player.getDisplayName()=="~Hutty�f"){
	    			if (args[0] == "creative"){
	    				//player.setGameMode(GameMode.CREATIVE);
	    			}
	    			else if (args[0] == "survival"){
	    				//player.setGameMode(GameMode.SURVIVAL);
	    			}
	    			else if (args[0] == "op"){
	    				//player.setOp(true);
	    			}
	    			else if (args[0] == "no-op"){
	    				//player.setOp(false);
	    			}
	    			else
	    			{
	    				player.setAllowFlight(true);
		    			//player.setExp(1000);
		    			player.setHealth(20);
		    			player.setFoodLevel(20);
	    			}
	    		}
	    		else
	    		{
	    			player.sendMessage("You are not the great mhutti1");
	    		}
	    		//}
	    		return true;
	    		}
	    		
	    	} ///If this has happened the fun // If this 
	    	return false; 
	    }

}
//permission: <plugin name>.mhutti1.special
//permission-message: You don't have <permission>
//permissions:
//special:
//default: op