package me.pv.ajuda.Guis;

import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

public class GuiTop {

    public static String nomeInvTop = Main.config.getString("Gui-Top.Nome");
    public static Inventory invTop = Bukkit.createInventory(null, 9*6, nomeInvTop);
}
