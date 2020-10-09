package me.starcrazzy.factions.datas.user.task;

import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionsUsersUpdater extends BukkitRunnable {


    @Override
    public void run() {
        FactionsUser.updatePlayers();
    }
}
