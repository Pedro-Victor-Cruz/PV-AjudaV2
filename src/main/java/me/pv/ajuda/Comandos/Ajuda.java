package me.pv.ajuda.Comandos;

import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ajuda implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.message.erroApenasInGame);
            return true;
        }

        Player player = (Player) sender;

        if(args.length > 0){

            if(args[0].equalsIgnoreCase("fechar")){
                if(Main.plugin.getDb().contains("Ajudas." + player.getDisplayName())){
                    Main.eventos.fecharSolicitacao(player);
                } else {
                    player.sendMessage(Main.message.erroNenhumPedidoAberto);
                }
                return true;
            }

            if(Main.plugin.getDb().contains("Ajudas." + player.getDisplayName())){
                player.sendMessage(Main.message.erroPedidoDeAjudaAberto);
                return true;
            }

            String motivo = String.join(" ", args);

            player.sendMessage(" ");
            player.sendMessage(Main.message.solicitarAjuda);
            player.sendMessage(" ");
            for(Player players : Bukkit.getOnlinePlayers()){
                if (players.hasPermission("pv.ajuda.staff")) {
                    for(String message : Main.message.novaSolicitacao){
                        players.sendMessage(ChatColor.translateAlternateColorCodes('&', message
                                .replace("[player]", player.getDisplayName())
                                .replace("[motivo]", motivo)));
                    }
                }
            }

            Main.plugin.getDb().set("Ajudas." + player.getDisplayName() + ".Motivo", motivo);
            Main.plugin.saveDb();
            Main.plugin.reloadDb();
            Main.eventos.reloadGuiPedidos();
        } else {
            Main.message.erroComandoIncorreto("/ajuda <motivo>", player);
        }

        return true;
    }
}
