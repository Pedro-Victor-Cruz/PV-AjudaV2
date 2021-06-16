package me.pv.ajuda.Comandos;

import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AceitarAjuda implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser usado em in-game!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0 && args.length < 2) {
            Player target = Bukkit.getPlayer(args[0]);
            Main.eventos.aceitarSolcitacao(player, target);
        } else {
            player.sendMessage("§cComando utilizado de forma incorreta!\n§cUtilize: §7/aceitarajuda <nick> §cou" +
                    "\n§7/aa <nick>");
        }
        return true;
    }
}
