package me.pv.ajuda;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Message {

    public String erroApenasInGame = Main.config.getString("Messages.Apenas-InGame").replace("&", "§");
    public String erroPedidoDeAjudaAberto = Main.config.getString("Messages.Pedido-De-Ajuda-Aberto").replace("&", "§");
    String erroComandoIncorreto = Main.config.getString("Messages.Comando-Incorreto").replace("&", "§");
    public String erroNenhumPedidoAberto = Main.config.getString("Messages.Nenhum-Pedido-Aberto").replace("&", "§");
    public String solicitarAjuda = Main.config.getString("Messages.Solicitar-Ajuda").replace("&", "§");
    public List<String> novaSolicitacao = Main.config.getStringList("Messages.Nova-Solicitacao");
    public String fechouSolicitacao = Main.config.getString("Messages.Fechou-Solicitacao").replace("&", "§");
    public String semPerm = Main.config.getString("Messages.Sem-Permissao").replace("&", "§");

    public void erroComandoIncorreto(String comando, Player player){
        String comandoReplace = erroComandoIncorreto.replace("[comando]", comando);
        player.sendMessage(comandoReplace);
    }

    public String solicitaoFechada(String motivo, Player player){
        String message = "§aSua solicitação de ajuda foi cancelada!\n§aMotivo: §b" + motivo;
        return message;
    }

    public void sendMessagePerm(String message, String perm){
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission(perm)){
                players.sendMessage(" ");
                players.sendMessage(message);
                players.sendMessage(" ");
            }
        }
    }

}
