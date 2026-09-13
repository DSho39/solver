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

    // ヒント本文の辞書。 キー("3-1") → 本文(複数行)
    // HashMap ではなく LinkedHashMap なのは、put した順番を覚えていてほしいから。
    // HashMap だと /hint の一覧が 2-3, 1-1, 3-2 ... のようにバラバラに並ぶ。
    private final Map<String, String[]> hints = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this); // このクラスのイベントを処理するように指定
        setupHints();                                              // ヒント本文を辞書に詰める
    }

    @Override
    public void onDisable() {
    }

    // ===================================================================
    //  ヒント本文
    //  文言を直すときはここだけ触れば済む。
    //  謎を増やしたいときは put をもう1つ足すだけで /hint 4-1 が使えるようになる。
    // ===================================================================
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

            // 引数なし → 一覧を出す
            if (args.length == 0) {
                sendIndex(sender);
                return true;
            }

            // 「/hint 3-1」も「/hint 3 1」も同じ "3-1" というキーにそろえる。
            //   {"3-1"}     → つなぐ相手がいないので "3-1"
            //   {"3", "1"}  → "-" ではさんで   "3-1"
            String key = String.join("-", args);

            String[] lines = hints.get(key);

            // get() は見つからないと null を返す。
            // ここを飛ばして for に渡すと NullPointerException で落ちる。
            if (lines == null) {
                sender.sendMessage("§cヒント " + key + " はありません。 §7/hint §cで一覧を確認してください。");
                return true;
            }

            for (String line : lines) {
                sender.sendMessage(line);
            }

            // 誰がどのヒントを開いたかを記録する。
            // logs/latest.log に残るので、本番が終わった後に読み返せる。
            // null チェックより後ろに置くこと。前に置くと打ち間違いまで記録されてしまう。
            getLogger().info("[hint] " + sender.getName() + " -> " + key);

            return true;
        }
        return false;
    }

    /**
     * 用意されているヒントを一覧表示する。
     * 各行はクリックするとそのままコマンドが実行される。
     */
    private void sendIndex(CommandSender sender) {
        sender.sendMessage("§8──────── §6ヒント一覧§8 ────────");

        // keySet() で「キーだけ」を全部取り出す。 → 1-1, 1-2, 1-3, 2-1, ...
        for (String key : hints.keySet()) {

            // §a のような文字コードでは色と装飾しか付けられず、クリックは表現できない。
            // クリックやホバーを持たせたいときは Component を組み立てる。
            Component line = Component.text("  /hint " + key, NamedTextColor.YELLOW)
                    .clickEvent(ClickEvent.runCommand("/hint " + key))
                    .hoverEvent(HoverEvent.showText(Component.text("クリックで開く", NamedTextColor.GRAY)));

            sender.sendMessage(line);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer(); // 入ってきたプレイヤーを取得
        event.setJoinMessage("§e" + p.getName() + "がサーバーにログインしました"); // メッセージを変更
    }
}
 
