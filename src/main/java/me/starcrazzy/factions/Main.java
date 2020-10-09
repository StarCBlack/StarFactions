package me.starcrazzy.factions;

import lombok.Getter;
import me.starcrazzy.factions.datas.faction.FactionManager;
import me.starcrazzy.factions.datas.faction.task.FactionsUpdater;
import me.starcrazzy.factions.datas.user.task.FactionsUsersUpdater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {

    @Getter
    public static Main instance;
    public static Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getLogger().info(ChatColor.GREEN + "Plugin iniciado com sucesso!");
        new FactionsUsersUpdater().runTaskTimerAsynchronously(this,20,20);
        new FactionsUpdater().runTaskTimerAsynchronously(this,20,20);
        FactionManager.reloadFactions();
        FactionManager.reloadUsers();
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "Plugin desabilitado com sucesso!");
    }
}
