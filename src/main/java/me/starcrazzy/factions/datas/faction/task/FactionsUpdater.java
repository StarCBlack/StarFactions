package me.starcrazzy.factions.datas.faction.task;

import me.starcrazzy.factions.datas.faction.data.Faction;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionsUpdater extends BukkitRunnable {


    @Override
    public void run() {
        Faction.updateFactions();
    }
}
