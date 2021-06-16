package me.pv.ajuda.Comandos;

import me.pv.ajuda.Guis.GuiPedidos;
import me.pv.ajuda.Guis.GuiTop;
import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Staff implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("§cEste comando não pode ser executado aqui!");
            return true;
        }

        Player player = (Player)sender;

        if(!(player.hasPermission("pv.ajuda.staff"))){
            player.sendMessage(Main.message.semPerm);
            return true;
        }

        if(args.length > 0){

            if(args[0].equalsIgnoreCase("pedidos")){
                player.openInventory(GuiPedidos.invPedidos);
                return true;
            }

            if(args[0].equalsIgnoreCase("top")){

                if(args.length > 2) {

                        if (args[1].equalsIgnoreCase("remover") && Main.plugin.getDb().contains("Top." + args[2])) {

                            String staff = args[2];

                            Main.plugin.getDb().set("Top." + staff, null);
                            Main.plugin.saveDb();
                            Main.plugin.reloadDb();
                            Main.eventos.reloadGuiTop();

                            Main.message.sendMessagePerm("§eO admin §6" + player.getDisplayName() + " §eremoveu o staff §6" + staff + " §edo TOP Staffs.", "pv.ajuda.admin");

                            return true;
                        }

                        if (args[1].equalsIgnoreCase("remover") && args[2].equalsIgnoreCase("*")) {

                            ConfigurationSection key = Main.plugin.getDb().getConfigurationSection("Top");

                            for (String top : key.getKeys(false)) {
                                Main.plugin.getDb().set("Top." + top, null);
                                Main.plugin.saveDb();
                                Main.plugin.reloadDb();
                            }

                            Main.eventos.reloadGuiTop();
                            Main.message.sendMessagePerm("§eO admin §6" + player.getDisplayName() + " §eresetou todo o TOP Staffs!", "pv.ajuda.admin");

                            return true;
                        }

                        if(args[1].equalsIgnoreCase("remover")) {
                            player.sendMessage(" §cComando incorreto!");
                            player.sendMessage(" §c/staff top remover <player> §7- §4Remove um staff do TOP Staffs");
                            player.sendMessage(" §c/staff top remover * §7- §4Reseta todo o TOP Staffs");
                            return true;
                        }

                        player.sendMessage(" ");
                        player.sendMessage(" §cComando não encontrado!");
                        player.sendMessage(" ");
                        return true;
                }

                player.openInventory(GuiTop.invTop);
                return true;
            }

        } else {
            player.sendMessage("§bLista de comandos:");
            player.sendMessage(" ");
            player.sendMessage("§b/staff pedidos &7- §aLista de solicitações de ajuda.");
            player.sendMessage("§b/staff top  &7- §aTOP melhores staffs.");
        }

        return true;
    }
}
