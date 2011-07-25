package util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class bChat {
    
    static Logger log = Logger.getLogger("Minecraft");
    public static List<String> Colors = new LinkedList<String>();
    static Server server;
    
    @SuppressWarnings("static-access")
    public bChat(Server server) {
        Colors.add("&black&");      Colors.add("&darkblue&");   Colors.add("&darkgreen&");
        Colors.add("&darkaqua&");   Colors.add("&darkred&");    Colors.add("&purple&");
        Colors.add("&gold&");       Colors.add("&gray&");       Colors.add("&darkgray&");
        Colors.add("&blue&");       Colors.add("&green&");      Colors.add("&aqua&");
        Colors.add("&red&");        Colors.add("&pink&");       Colors.add("&yellow&");
        Colors.add("&white&");
        Colors.add("&0");   Colors.add("&1");   Colors.add("&2");   Colors.add("&3");   Colors.add("&4");
        Colors.add("&5");   Colors.add("&6");   Colors.add("&7");   Colors.add("&8");   Colors.add("&9");
        Colors.add("&a");   Colors.add("&b");   Colors.add("&c");   Colors.add("&d");   Colors.add("&e");
        Colors.add("&f");
        Colors.add("&random");
        Colors.add("&rainbow");
        this.server = server;
    }
    
    public static String replaceColorCodes(String message) {
        message = replaceTags(message);
        message = message.replaceAll("(&([a-f0-9]))", "\u00A7$2");
        if(message.contains("&random")) {
            message = message.replaceAll("&random", "");
            message = replaceRandom(message);
        }
        if(message.contains("&rainbow")) {
            message = message.replaceAll("&rainbow", "");
            message = replaceRainbow(message);
        }
        return message;
    }
    static String replaceTags(String message) {
        message = message.replaceAll("&black&", "&0");
        message = message.replaceAll("&darkblue&", "&1");
        message = message.replaceAll("&darkgreen&", "&2");
        message = message.replaceAll("&darkaqua&", "&3");
        message = message.replaceAll("&darkred&", "&4");
        message = message.replaceAll("&purple&", "&5");
        message = message.replaceAll("&gold&", "&6");
        message = message.replaceAll("&gray&", "&7");
        message = message.replaceAll("&darkgray&", "&8");
        message = message.replaceAll("&blue&", "&9");
        message = message.replaceAll("&green&", "&a");
        message = message.replaceAll("&aqua&", "&b");
        message = message.replaceAll("&red&", "&c");
        message = message.replaceAll("&pink&", "&d");
        message = message.replaceAll("&yellow&", "&e");
        message = message.replaceAll("&white&", "&f");
        return message;
    }
    
    static String replaceRainbowTags(String message) {
        String oldmessage = message;
        if(oldmessage == message) message = message.replaceAll("&1", "&5");
        if(oldmessage == message) message = message.replaceAll("&2", "&d");
        if(oldmessage == message) message = message.replaceAll("&3", "&9");
        if(oldmessage == message) message = message.replaceAll("&4", "&2");
        if(oldmessage == message) message = message.replaceAll("&5", "&a");
        if(oldmessage == message) message = message.replaceAll("&6", "&e");
        if(oldmessage == message) message = message.replaceAll("&7", "&6");
        if(oldmessage == message) message = message.replaceAll("&8", "&c");
        if(oldmessage == message) message = message.replaceAll("&9", "&4");
        return message;
    }
    
    public static String replaceRandom(String message) {
        Random generator = new Random();
        String newmessage = "", sch, srand;
        char ch;
        int rand, i = 0;
        while(i < message.length()) {
            ch = message.charAt(i);
            sch = String.valueOf(ch);
            rand = generator.nextInt(15) + 1;
            srand = Integer.toString(rand);
            srand = "&" + srand;
            srand = srand.replaceAll("&10", "&a");
            srand = srand.replaceAll("&11", "&b");
            srand = srand.replaceAll("&12", "&c");
            srand = srand.replaceAll("&13", "&d");
            srand = srand.replaceAll("&14", "&e");
            srand = srand.replaceAll("&15", "&f");
            srand = replaceColorCodes(srand);
            newmessage += srand + sch;
            i++;
        }
        return newmessage;
    }
    
    public static String replaceRainbow(String message) {
        String newmessage = "", sch, srand;
        char ch;
        int rand = 1, i = 0;
        while(i < message.length()) {
            ch = message.charAt(i);
            sch = String.valueOf(ch);
            if(ch != ' ') {
                srand = "&" + rand;
                rand++;
                if(rand > 9) rand = 1;
                srand = replaceRainbowTags(srand);                
            }
            else {
                srand = "";
            }
            srand = replaceColorCodes(srand);
            newmessage += srand + sch;
            i++;
        }
        return newmessage;
    }
    
    public static void log(String message, int type) {
        switch(type) {
            case 1:
                log.info(message);
                break;
            case 2:
                log.warning(message);
                break;
            case 3:
                log.severe(message);
                break;
            default:
                log.info(message);
                break;
        }
    }
    
    public static void log(String message) {
        log.info(message);
    }
    
    public static void broadcastMessage(String message) {
        message = bChat.replaceColorCodes(message);
        server.broadcastMessage(message);
        log.info(message);
    }
    
    public static void sendMessageToPlayer(Player player, String message) {
        message = bChat.replaceColorCodes(message);
        player.sendMessage(message);
    }
    
    public static void sendMessageToServer(String message) {
        message = bChat.replaceColorCodes(message);
        log.info(message);
    }
    
    public static void sendMessageToCommandSender(CommandSender sender, String message) {
        if(sender instanceof Player) {
            bChat.sendMessageToPlayer((Player) sender, message);
        }
        else {
            bChat.sendMessageToServer(message);
        }
    }
    
    public static void showColors(Player player) {
        String message;
        sendMessageToPlayer(player, "&6Colors: \"&e& + ColorCode&6 + Text\"");
        sendMessageToPlayer(player, "&6ColorCodes:");
        message = "&00 = Black           &6||   &11 = Dark Blue";
        sendMessageToPlayer(player, message);
        message = "&22 = Dark Green   &6||   &33 = Dark Aqua";
        sendMessageToPlayer(player, message);
        message = "&44 = Dark Red      &6||   &55 = Purple";
        sendMessageToPlayer(player, message);
        message = "&66 = Gold            &6||   &77 = Gray";
        sendMessageToPlayer(player, message);
        message = "&88 = Dark Gray     &6||   &99 = Blue";
        sendMessageToPlayer(player, message);
        message = "&aa = Green          &6||   &bb = Aqua";
        sendMessageToPlayer(player, message);
        message = "&cc = Red             &6||   &dd = Pink";
        sendMessageToPlayer(player, message);
        message = "&ee = Yellow          &6||   &ff = White";
        sendMessageToPlayer(player, message);
    }
}
