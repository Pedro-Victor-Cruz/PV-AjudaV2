package me.pv.ajuda;

import me.pv.ajuda.Comandos.AceitarAjuda;
import me.pv.ajuda.Comandos.Ajuda;
import me.pv.ajuda.Comandos.Staff;
import me.pv.ajuda.Eventos.Eventos;
import me.pv.ajuda.Eventos.GuiEventos;
import me.pv.ajuda.Eventos.JoinExitEvent;
import me.pv.ajuda.Guis.GuiPedidos;
import me.pv.ajuda.Guis.GuiTop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static PluginManager pm;
    public static Main plugin;
    public static Eventos eventos;
    public static GuiEventos guiEventos;
    public static GuiPedidos guiPedidos;
    public static GuiTop guiTop;
    public static Message message;
    private File file = null;
    private FileConfiguration fileConfig = null;

    @Override
    public void onEnable() {
        enablePlugin();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ============ PV-Ajuda ============");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "PV-Ajuda habilitado com sucesso!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Criado por: Pedro_Victor");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Versão: 2.0");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ============ PV-Ajuda ============");
        pm.registerEvents(new GuiEventos(), this);
        pm.registerEvents(new JoinExitEvent(), this);
        getCommand("staff").setExecutor(new Staff());
        getCommand("aceitarajuda").setExecutor(new AceitarAjuda());
        getCommand("ajuda").setExecutor(new Ajuda());

        File fileRegistro = new File(getDataFolder(), "db.yml");
        if(!fileRegistro.exists()){
            saveResource("db.yml", false);
        }

    }

    public void enablePlugin(){
        plugin = this;
        config = getConfig();
        pm = Bukkit.getPluginManager();
        eventos = new Eventos();
        guiPedidos = new GuiPedidos();
        message = new Message();
        guiTop = new GuiTop();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {disablePlugin();}

    public void disablePlugin(){
        try{
            HandlerList.unregisterAll(this);
            Bukkit.getScheduler().cancelTasks(this);
        } catch (Throwable e){}
    }

    public FileConfiguration getDb(){
        if(this.fileConfig == null){
            this.file = new File(getDataFolder(), "db.yml");
            this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileConfig;
    }

    public void saveDb(){
        try{
            getDb().save(this.file);
        }catch (Exception e){}
    }


    public void reloadDb(){
        if(this.file == null){
            this.file = new File(getDataFolder(), "db.yml");
        }

        this.fileConfig = YamlConfiguration.loadConfiguration(this.file);

        if(this.fileConfig != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
            this.fileConfig.setDefaults(defaults);
        }
    }

}
