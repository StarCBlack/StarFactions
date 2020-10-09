package me.starcrazzy.factions.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {

    public static void send(Player player, String text) {
        JsonObject json = new JsonObject();
        json.addProperty("text", text);

        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(json.toString()), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
