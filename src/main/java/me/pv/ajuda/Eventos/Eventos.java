package me.pv.ajuda.Eventos;


import me.pv.ajuda.Comparator.Decrescente;
import me.pv.ajuda.Guis.GuiPedidos;
import me.pv.ajuda.Guis.GuiTop;
import me.pv.ajuda.Main;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Eventos {

    public void aceitarSolcitacao(Player player, Player target){

        if (!(player.hasPermission("pv.ajuda.staff"))) {
            player.sendMessage(Main.message.semPerm);
            return;
        }

        if (target == null) {
            player.sendMessage("§cEste jogador não está online ou não existe!");
            return;
        }

        if(!(Main.plugin.getDb().contains("Ajudas." + target.getDisplayName()))){
            player.sendMessage("§cEste jogador não solicitou ajuda!");
            return;
        }

        if(player.getDisplayName().equalsIgnoreCase(target.getDisplayName())){
            player.sendMessage("§cVocê não pode aceitar sua solicitação de ajuda!");
            return;
        }

        Main.plugin.getDb().set("Ajudas." + target.getDisplayName(), null);
        Main.plugin.saveDb();
        Main.plugin.reloadDb();
        player.teleport(target, PlayerTeleportEvent.TeleportCause.COMMAND);

        if(Main.plugin.getDb().contains("Top." + player.getDisplayName())){
            int pontos = Main.plugin.getDb().getInt("Top." + player.getDisplayName());
            Main.plugin.getDb().set("Top." + player.getDisplayName(), pontos + 1);
            Main.plugin.saveDb();
            Main.plugin.reloadDb();
        } else {
            Main.plugin.getDb().set("Top." + player.getDisplayName(), 1);
            Main.plugin.saveDb();
            Main.plugin.reloadDb();
        }

        Main.eventos.reloadGuiTop();

        Main.message.sendMessagePerm("§bSolicitação de ajuda do player §a" + target.getDisplayName() + "§b aceita!\n§bStaff: §a" + player.getDisplayName(), "pv.ajuda.staff");

    }

    public void fecharSolicitacao(Player player, String target){

        if(player.hasPermission("pv.ajuda.admin")){
            if(Main.plugin.getDb().contains("Ajudas." + target)){
                Main.plugin.getDb().set("Ajudas." + target, null);
                Main.plugin.saveDb();
                Main.plugin.reloadDb();
                reloadGuiPedidos();
                Main.message.sendMessagePerm("§eSolicitação de ajuda do player §6" + target + " §efoi cancelada" +
                        "\n§epelo o admin §6" + player.getDisplayName() + "§e.", "pv.ajuda.admin");
            } else {
                player.sendMessage("§cEste usuário não foi encontrado no banco de dados!");
            }
        } else {
            player.sendMessage(Main.message.semPerm);
        }
    }

    public void fecharSolicitacao(Player player){
        if(Main.plugin.getDb().contains("Ajudas." + player.getDisplayName())){
            Main.plugin.getDb().set("Ajudas." + player.getDisplayName(), null);
            Main.plugin.saveDb();
            Main.plugin.reloadDb();
            reloadGuiPedidos();
            player.sendMessage(Main.message.fechouSolicitacao);
        }
    }

    public void reloadGuiPedidos(){
        ConfigurationSection keys = Main.plugin.getDb().getConfigurationSection("Ajudas");

        for(int i = 0; i < GuiPedidos.invPedidos.getSize(); i++){
            GuiPedidos.invPedidos.setItem(i, null);
        }

        int i = 0;
        int amount = 1;
        for(String ajudas : keys.getKeys(false)){
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Motivo: §f" + Main.plugin.getDb().getString("Ajudas." + ajudas + ".Motivo"));
            lore.add(" ");
            lore.add(" §7Clique com o §2§lESQUERDO §7para aceitar!");
            lore.add(" §7Clique com o §4§lDIREITO §7para fechar!");
            lore.add(" ");
            ItemStack skullItem = newSkull(ajudas, ajudas, lore, amount);
            GuiPedidos.invPedidos.setItem(i, skullItem);
            i++;
            amount++;
        }

        ItemStack linha = newItem(" ", Material.STAINED_GLASS_PANE, 1);
        int slotMaximo = 45;
        int y = 36;
        while (slotMaximo > y){
            GuiPedidos.invPedidos.setItem(y, linha);
            y++;
        }

        ArrayList<String> loreFecharTudo = new ArrayList<>();
        loreFecharTudo.add(" ");
        loreFecharTudo.add("§cClique para fechar todos");
        loreFecharTudo.add("§cpedidos de ajuda aberto.");
        ItemStack fecharTudo = newItem(GuiPedidos.fecharTudoPedidos, Material.REDSTONE_BLOCK, loreFecharTudo, 1);
        GuiPedidos.invPedidos.setItem(49, fecharTudo);
    }

    public void reloadGuiTop(){
        ConfigurationSection keys = Main.plugin.getDb().getConfigurationSection("Top");

        for(int i = 0; i < GuiTop.invTop.getSize(); i++){
            GuiTop.invTop.setItem(i, null);
        }

        List<Top> top = new ArrayList<>();
        for(String tops : keys.getKeys(false)){
            top.add(new Top(tops, Main.plugin.getDb().getInt("Top." + tops)));
        }

        int posicao = 1;
        int slot = 0;
        Collections.sort(top, new Decrescente());
        for(Top staffs : top){
            ArrayList<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add(" §e§lInformações:");
            lore.add(" §bPosição: §a" + posicao + "º lugar");
            lore.add("§bSolicitações aceitas: §a" + staffs.getPontos());
            ItemStack staff = newSkull(staffs.getStaff(), "§7" + posicao + "# §b" + staffs.getStaff(), lore, posicao);
            GuiTop.invTop.setItem(slot, staff);
            posicao++;
            slot++;
        }
    }

    public static ItemStack newItem(String nome, Material material, List<String> lore, int amount){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(nome);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        return item;
    }

    public static ItemStack newItem(String nome, Material material, int amount){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(nome);
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        return item;
    }

    public ItemStack newSkull(String staff,String nome, List<String> lore, int amount){

        ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM,1,(short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta)playerSkull.getItemMeta();
        meta.setOwner(staff);
        meta.setDisplayName(nome);
        meta.setLore(lore);
        playerSkull.setItemMeta(meta);
        playerSkull.setAmount(amount);

        return playerSkull;
    }
}
