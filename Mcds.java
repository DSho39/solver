package org.fc.mcds;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Container;
import org.bukkit.loot.Lootable;

import java.awt.*;
import java.util.Random;

public final class Mcds extends JavaPlugin implements Listener {
    private int namber;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this); // このクラスのイベントを処理するように指定
        // テスト用にチェストを生成

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Location loc = new Location(Bukkit.getWorld("world"), 74, -60, 52);
            createLootChest(loc);
        }, 20L);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("test")) {
            sender.sendMessage("コマンドが実行されたよ");
            System.out.println("コマンドが実行されたよ");
        }

        if (label.equalsIgnoreCase("message")) {
            if( args.length != 0){
            showMessage((Player)sender,args[0] );
            }
        }

        if (label.equalsIgnoreCase("send00")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("a:動いてるー？");
                en(player); // メソッド呼び出し
                player.sendMessage("b:動いてるよー");
            }
            return true;
        }

        if (label.equalsIgnoreCase("kuji")) {
            Player player = (Player)sender;
            PlayerInventory inventory = player.getInventory(); // プレイヤーのインベントリを取得
            //乱数生成
            Random rand = new Random();
            ItemStack Stack;
            player.sendMessage("a:動いてるー？");
            int r = rand.nextInt(2) ;
            if(r == 0){
                Stack = new ItemStack(Material.TNT, 1);
                player.sendMessage("TNTあげるー");
            }else {
                Stack = new ItemStack(Material.DIAMOND, 1);
                player.sendMessage("ダイヤモンドあげるー");
            }
            inventory.addItem(Stack);
            player.sendMessage("b:動いてるよー");
        }
        return true;
    }

    public void showMessage(Player player, String message){

        // 本当はCommandSenderでも送れますが、例示のためにPlayerで
        //Player player = (Player)sender;
        // 送信したいメッセージ
        //String message = "ActionBar!!";

        // Stringでは送れません、TextComponentを作成しましょう
        TextComponent component = new TextComponent();
        component.setText(message);

        // アクションバーのメッセージ送信はコレ！
        // ChatMessageType.ACTION_BARを指定しましょう
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,component);

        // ちなみに、タイトルの送信はこれを使用します
        player.sendTitle("鮭のムニエル","～そこら辺にいたエスカルゴを添えて～",10,70,20);
        //メッセージの送信はこれ
        //player.sendMessage(ChatColor.GOLD + message);


    }

    public void en(Player player){

        // 新しい石の剣を生成します。

        ItemStack myItem = new ItemStack(Material.STICK);
        // エンチャントを付与します。
        myItem.addUnsafeEnchantment(Enchantment.SHARPNESS, (int) Math.pow(2, namber));
        PlayerInventory inventory = player.getInventory();
        inventory.addItem(myItem);
        namber = namber + 1;
    }

    private void createLootChest(Location location) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);

        // チェストとして取得
        Container container = (Container) block.getState();

        // LootTable を設定（ここでは村の武器鍛冶チェスト）
        Lootable lootable = (Lootable) container;
        NamespacedKey key = NamespacedKey.minecraft("chests/village/village_weaponsmith");
        LootTable lootTable = Bukkit.getLootTable(key);
        lootable.setLootTable(lootTable);


        lootable.setSeed(System.currentTimeMillis());

        // 変更を確定
        container.update(); //updateが赤文字になっている。

        getLogger().info("ルートテーブルチェストを生成しました！");
    }






    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        namber = 1;
        Player p = event.getPlayer(); // 入ってきたプレイヤーを取得
        event.setJoinMessage("§e" + p.getName() + "がサーバーにログインしました"); // メッセージを変更
    }

}