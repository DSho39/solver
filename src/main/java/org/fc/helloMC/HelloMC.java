package org.fc.helloMC;

import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public final class HelloMC extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        //getCommand("test").setExecutor(this);
        System.out.println("石です。");
    }

    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        if (label.equalsIgnoreCase("count")) {
            if (args.length == 1) {
                if (!StringUtils.isNumeric(args[0])) {
                    sender.sendMessage("§ccountは数字で指定してください");
                    return true;
                }
                final int count = Integer.valueOf(args[0]);
                if (count <= 0) {
                    sender.sendMessage("§ccountは1以上を指定してください");
                    return true;
                }
                sender.sendMessage("§b" + count + "s数える");
                BukkitRunnable task = new BukkitRunnable() {
                    int i = 0;

                    public void run() {
                        if (i == count) {
                            sender.sendMessage("§a" + (i) + "sのカウントが終了しました");
                            Player player = (Player) sender;
                            Bukkit.dispatchCommand(player, "time set night");
                            cancel();
                            return;
                        }
                        sender.sendMessage("カウント開始から" + i + "s経過");
                        i++;
                    }
                };
                task.runTaskTimer(getServer().getPluginManager().getPlugin("HelloMC"), 0L, 20L);
                return true;
            }
            sender.sendMessage("§c使い方: /count <count>");
        }
        if (label.equalsIgnoreCase("test")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                for (int i = 0; i < 10; i++) {
                    player.getWorld().spawn(player.getLocation(), Rabbit.class);
                }
            }
            sender.sendMessage("コマンドが実行されたよ");
            System.out.println("コマンドが実行されたよ");
        }
        if (label.equalsIgnoreCase("cats")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                for (int i = 0; i < 10; i++) {
                    player.getWorld().spawn(player.getLocation(), Cat.class);
                }
            }
            sender.sendMessage("コマンドが実行されたよ");
            System.out.println("コマンドが実行されたよ");
        }
        return true;
    }

    @Override

    public void onDisable() {


    }

    //@EventHandler
    //public void onPlayerMove(PlayerMoveEvent evt) {
       // // プレイヤーの位置を取得します。
       // Location loc = evt.getPlayer().getLocation();
       // // 位置のY座標を+5します。位置情報を変更しているだけで、実際にプレイヤーの位置が移動するわけではないことに注意してください。
       // loc.setY(loc.getY() + 5);
       // Block b = loc.getBlock();
       // Random rand = new Random();
      //  int r = rand.nextInt(2);
       // for (int i = 0; i < 10; i++) {
      //      if (r == 0) {
      //          b.setType(Material.SANDSTONE);
      //      } else {
      //          b.setType(Material.STONE);
      //      }
      //  }
     //   generateCube(loc,7);
   // }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){

        Player p = event.getPlayer(); // 入ってきたプレイヤーを取得

        event.setJoinMessage("§e" + p.getName() + "さんがサーバーに入ってきたぞー宴だー！"); // メッセージを変更

    }

    public void generateCube(Location loc, int length){
        // 与えられたLocationから、立方体の端の座標を取得します。
        // getN()メソッドを使うと intへキャストする必要がありますが、
        // getBlockN()メソッドを使えばそのままintで座標を取得できます。
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();
        // 一辺の長さを足すことで、立方体の反対側の座標を計算します。
        int x2 = x1 + length;
        int y2 = y1 + length;
        int z2 = z1 + length;
        World world = loc.getWorld();
        // x座標方向のループ
        for (int xPoint = x1; xPoint <= x2; xPoint++) {
            // y座標方向のループ
            for (int yPoint = y1; yPoint <= y2; yPoint++) {
                // z座標方向のループ
                for (int zPoint = z1; zPoint <= z2; zPoint++) {
                    // ループで処理する座標のブロックを取得します。
                    Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
                    // ダイアモンドブロックに設定します！
                    //currentBlock.setType(Material.AIR);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); // Joinしたプレイヤー
        PlayerInventory inventory = player.getInventory(); // プレイヤーのインベントリ
        ItemStack diamondstack = new ItemStack(Material.DIAMOND, 64); // 山積みのダイヤモンド！

        if (inventory.contains(diamondstack)) {
            inventory.addItem(diamondstack); // プレイヤーインベントリに山積みのダイヤモンドを加える
            player.sendMessage(ChatColor.GOLD + "よく来たな!もっとダイヤモンドをくれてやろう、このとんでもない成金め!!");
        }
    }
}