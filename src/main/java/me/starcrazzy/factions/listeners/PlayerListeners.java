package me.starcrazzy.factions.listeners;

import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FactionsUser.getDao().find("username", event.getPlayer().getName());
    }

}
