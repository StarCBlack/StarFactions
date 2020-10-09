package me.starcrazzy.factions.datas.user.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.starcrazzy.factions.datas.faction.data.Faction;
import me.starcrazzy.factions.datas.user.dao.FactionUserDao;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@Getter @Setter
public class FactionsUser {

    @Getter @Setter
    private static HashSet<FactionsUser> updating = Sets.newHashSet();
    @Getter @Setter
    private static FactionUserDao dao = new FactionUserDao();
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String factionName;
    private Faction faction;

    @Getter @Setter
    private double power, powerMax;
    @Getter @Setter
    private final List<Integer> invites = Lists.newArrayList();
    @Getter @Setter
    private Boolean mapAutoUpdating = false, flying = false, seeingChunks = false, overriding = false;
public FactionsUser(){}
    public FactionsUser(String username, Faction faction, double power, double powerMax) {
        this.username = username;
        this.power = power;
        this.powerMax = powerMax;
        this.faction = faction;
    }



    public boolean hasFaction(){
        return this.faction != null;
    }

    public static void updatePlayers() {

        Iterator<FactionsUser> it = updating.iterator();
        int dataActual = 0;
        final int limit = 50;
        while(it.hasNext() && dataActual < limit ){
            FactionsUser dado = it.next();
            try {
                dado.update();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            dataActual++;
        }
    }


    public Player getPlayer() {
        return Bukkit.getPlayer(this.username);
    }

    public UUID getUuid() {
        return getPlayer().getUniqueId();
    }

    public void update() throws SQLException {


        String updateManual = "UPDATE " + dao.getTable().getName()
                + " SET power = ? WHERE username = ?";

        PreparedStatement prepare = dao.getMySQL().prepareStatement(updateManual);
        prepare.setDouble(1,power);
        prepare.setDouble(2,powerMax);
        prepare.executeUpdate();

        prepare.close();


    }
    public void updateQueue(){
        updating.add(this);

    }


}
