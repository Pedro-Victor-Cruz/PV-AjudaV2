package me.pv.ajuda.Eventos;

import me.pv.ajuda.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class JoinExitEvent implements Listener {

    private ArrayList<String> ajudasFechadas = new ArrayList<>();


    @EventHandler
    public void joinPlayerServer(PlayerJoinEvent event){

        Player player = event.getPlayer();
        String namePlayer = player.getDisplayName();

        if(ajudasFechadas.contains(namePlayer)){
            ajudasFechadas.remove(namePlayer);
            String message = Main.message.solicitaoFechada("§bDeslogou em meio a solicitação.", player);
            player.sendMessage(message);
        }
        return;
    }

    @EventHandler
    public void exitPlayerServer(PlayerQuitEvent event){

        OfflinePlayer playerOff = event.getPlayer();
        Player player = playerOff.getPlayer();
        String namePlayer = player.getDisplayName();

        if(Main.plugin.getDb().contains("Ajudas." + namePlayer)){
            Main.plugin.getDb().set("Ajudas." + namePlayer, null);
            Main.plugin.saveDb();
            Main.plugin.reloadDb();
            Main.eventos.reloadGuiPedidos();
            ajudasFechadas.add(namePlayer);
            Main.message.sendMessagePerm("§bSolicitação de ajuda do player §a" + namePlayer + "§b foi cancelada!" +
                    "\n§bMotivo: §aDesconectou em meio a solicitação!.", "pv.ajuda.staff");
        }

        return;
    }
}
