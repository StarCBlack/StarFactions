package me.starcrazzy.factions.utils;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
@Getter
public class Title {

    private final String title;
    private final String subTitle;

    public Title(String title, String subtitle) {
        this.title = title;
        this.subTitle = subtitle;
    }

    public PacketPlayOutTitle[] build() {
        final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title +"\"}")
        );
        final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle +"\"}")
        );

        return new PacketPlayOutTitle[] { titlePacket, subtitlePacket };
    }

    public void direct(Iterable<? extends Player> players) {
        PacketPlayOutTitle[] packets = build();

        players.forEach(it -> {
            ((CraftPlayer)it).getHandle().playerConnection.sendPacket(packets[0]);
            ((CraftPlayer)it).getHandle().playerConnection.sendPacket(packets[1]);
        });
    }
}
