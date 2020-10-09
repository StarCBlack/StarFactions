package me.starcrazzy.factions.utils;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

public class TickEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    private static long lastId=0;

    @Getter
    private final long id;

    public TickEvent() {
        this.id = lastId++;
    }
}
