package me.starcrazzy.factions.datas.faction.data;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import me.starcrazzy.factions.datas.faction.dao.FactionDao;
import me.starcrazzy.factions.datas.user.data.FactionsUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

@Getter @Setter
public class Faction {

    private int id;
    private String name, tag;
    private static HashSet<Faction> updating = Sets.newHashSet();
    private static FactionDao dao = new FactionDao();
    private ArrayList<Role> roles = new ArrayList<>();
    private ArrayList<FactionsUser> members = new ArrayList<>();
    public double getPower(){
        double power = 0;
        for (FactionsUser user : members){
            power += user.getPower();
        }
        return power;

    }
    public double getPowerMax(){
        double powerMax = 0;
        for (FactionsUser user : members){
            powerMax+=user.getPowerMax();
        }
        return powerMax;

    }


    public Faction(String name, String tag) {
        this.name = name;
        this.tag = tag;

        for (Role role :Role.getRoles()){
            Role copia = role.clone();

            this.roles.add(copia);

        }

    }

    public static void updateFactions() {

        Iterator<Faction> it = updating.iterator();
        int dataActual = 0;
        final int limit = 50;
        while(it.hasNext() && dataActual < limit ){
            Faction dado = it.next();
            try {
                dado.update();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            dataActual++;
        }
    }
    public void update() throws SQLException {


        String updateManual = "UPDATE " + dao.getTable().getName()
                + " SET tag = ? WHERE name = ?";

        PreparedStatement prepare = dao.getMySQL().prepareStatement(updateManual);
        prepare.setString(1,tag);
        prepare.setString(2,name);
        prepare.executeUpdate();

        prepare.close();

    }
    public void updateQueue(){
        updating.add(this);

    }

}
