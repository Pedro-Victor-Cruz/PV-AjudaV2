package me.pv.ajuda.Eventos;

import me.pv.ajuda.Guis.GuiPedidos;
import me.pv.ajuda.Guis.GuiTop;
import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiEventos implements Listener {

    @EventHandler
    public void onGui(InventoryClickEvent event){

        Player player = (Player)event.getWhoClicked();

        if(event.getInventory().getName().equalsIgnoreCase(GuiPedidos.nomeInvPedidos)){
            event.setCancelled(true);

            if(!(event.getWhoClicked() instanceof Player)){ return; }
            if(event.getCurrentItem() == null){ return; }
            if(event.getCurrentItem().getType() == Material.AIR){ return; }

            if(event.getCurrentItem().getType().equals(Material.SKULL_ITEM)){
                if(Main.plugin.getDb().contains("Ajudas." + event.getCurrentItem().getItemMeta().getDisplayName())){
                    if(event.isRightClick()){
                        String target = event.getCurrentItem().getItemMeta().getDisplayName();
                        Main.eventos.fecharSolicitacao(player, target);
                    } else if (event.isLeftClick()){
                        Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                        Main.eventos.aceitarSolcitacao(player, target);
                        player.closeInventory();
                    }
                }
            } else if (event.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK) &&
            event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(GuiPedidos.fecharTudoPedidos)){
                if(player.hasPermission("pv.ajuda.admin")){
                    ConfigurationSection keys = Main.plugin.getDb().getConfigurationSection("Ajudas");
                    for(String ajudas : keys.getKeys(false)){
                        Main.plugin.getDb().set("Ajudas." + ajudas, null);
                        Main.plugin.saveDb();
                        Main.plugin.reloadDb();
                    }
                    Main.eventos.reloadGuiPedidos();
                } else {
                    player.sendMessage(Main.message.semPerm);
                }
            }
            return;
        }

        if(event.getInventory().getName().equalsIgnoreCase(GuiTop.nomeInvTop)){

            event.setCancelled(true);
            if(!(event.getWhoClicked() instanceof Player)){ return; }
            if(event.getCurrentItem() == null){ return; }
            if(event.getCurrentItem().getType() == Material.AIR){ return; }

            return;
        }

    }

    @EventHandler
    public void onOpenGui(InventoryOpenEvent event){
        if(event.getInventory().getName().equalsIgnoreCase(GuiPedidos.nomeInvPedidos)){
            Main.eventos.reloadGuiPedidos();
            return;
        }

        if(event.getInventory().getName().equalsIgnoreCase(GuiTop.nomeInvTop)){
            Main.eventos.reloadGuiTop();
            return;
        }
    }

    @EventHandler
    public void onCloseGui(InventoryCloseEvent event){
        if(event.getInventory().getName().equalsIgnoreCase(GuiPedidos.nomeInvPedidos)){
            Main.eventos.reloadGuiPedidos();
        }
    }
}
