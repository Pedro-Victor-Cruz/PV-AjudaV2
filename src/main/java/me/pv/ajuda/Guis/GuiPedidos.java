package me.pv.ajuda.Guis;

import me.pv.ajuda.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;


public class GuiPedidos {

    public static int slotsPedidos = Main.config.getInt("Gui-Solicitacoes.Linhas-de-slots") * 9;
    public static String nomeInvPedidos = Main.config.getString("Gui-Solicitacoes.Nome").replace("&", "§");
    public static Inventory invPedidos = Bukkit.createInventory(null, slotsPedidos, nomeInvPedidos);
    public static String fecharTudoPedidos = Main.config.getString("Gui-Solicitacoes.Fechar-Tudo.nome").replace("&", "§");


}
