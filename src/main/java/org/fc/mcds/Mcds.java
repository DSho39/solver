package org.fc.Mcds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Mcds extends JavaPlugin implements Listener {

    
    
    
    private final Map<String, String[]> hints = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this); 
        setupHints();                                              
    }

    @Override
    public void onDisable() {
    }

    
    
    
    
    
    private void setupHints() {

        hints.put("1-1", new String[]{
                "§8──────── §6§l謎1  ヒント1§r§8 ────────",
                "",
                "§f買う ＝ §e§lB  U  Y",
                ""
        });

        hints.put("1-2", new String[]{
                "§8──────── §6§l謎1  ヒント2§r§8 ────────",
                "",
                "§fbee、you、why",
                ""
        });

        hints.put("1-3", new String[]{
                "§8──────── §6§l謎1  ヒント3§r§8 ────────",
                "",
                "§fsea、you、tea",
                ""
        });

        hints.put("2-1", new String[]{
                "§8──────── §6§l謎2  ヒント1§r§8 ────────",
                "",
                "§f盤面に「§bしろくま§f」がいる。",
                ""
        });

        hints.put("2-2", new String[]{
                "§8──────── §6§l謎2  ヒント2§r§8 ────────",
                "",
                "§fし→ろ→く→ま の順に線で結ぶ",
                ""
        });

        hints.put("2-3", new String[]{
                "§8──────── §6§l謎2  ヒント3§r§8 ────────",
                "",
                "§f「§bたこやき§f」でも同じことを。",
                ""
        });

        hints.put("2-4", new String[]{
                "§8──────── §6§l謎2  ヒント4§r§8 ────────",
                "",
                "§f「いのうえ」＝「§eいの上§f」。",
                ""
        });

        hints.put("3-1", new String[]{
                "§8──────── §6§l謎3  ヒント1§r§8 ────────",
                "",
                "§f「§bうみのかぜ§f」をスマホで打ってみて。",
                ""
        });

        hints.put("3-2", new String[]{
                "§8──────── §6§l謎3  ヒント2§r§8 ────────",
                "",
                "§fすいり ＝ §e§l↑ ← ←",
                ""
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hint")) {

            
            if (args.length == 0) {
                sendIndex(sender);
                return true;
            }

            
            
            
            String key = String.join("-", args);

            String[] lines = hints.get(key);

            
            
            if (lines == null) {
                sender.sendMessage("§cヒント " + key + " はありません。 §7/hint §cで一覧を確認してください。");
                return true;
            }

            for (String line : lines) {
                sender.sendMessage(line);
            }

            
            
            
            getLogger().info("[hint] " + sender.getName() + " -> " + key);

            return true;
        }
        return false;
    }
\
    private void sendIndex(CommandSender sender) {
        sender.sendMessage("§8──────── §6ヒント一覧§8 ────────");

        
        for (String key : hints.keySet()) {

            
            
            Component line = Component.text("  /hint " + key, NamedTextColor.YELLOW)
                    .clickEvent(ClickEvent.runCommand("/hint " + key))
                    .hoverEvent(HoverEvent.showText(Component.text("クリックで開く", NamedTextColor.GRAY)));

            sender.sendMessage(line);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer(); 
        event.setJoinMessage("§e" + p.getName() + "がサーバーにログインしました"); 
    }
}
 
