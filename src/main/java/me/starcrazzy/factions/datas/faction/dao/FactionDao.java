package me.starcrazzy.factions.datas.faction.dao;

import me.starcrazzy.factions.database.dao.DaoBase;
import me.starcrazzy.factions.database.table.TableColumn;
import me.starcrazzy.factions.datas.faction.data.Faction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author oNospher
 **/
public class FactionDao extends DaoBase<Faction> {


    public FactionDao() {
        super("faction_factions");
    }



    public void createTable() {
        table.addColumn("faction_id", TableColumn.ID);
        table.addColumn("name", TableColumn.STRING);
        table.addColumn("tag", TableColumn.STRING);
        table.create();
    }


    public void insert(Faction element)  {
        try {
            getTable().insert(
                    "faction_id",
                    "name",
                    "tag"
            ).one(
                    element.getId(),
                    element.getName(),
                    element.getTag()
            );
        }catch (Exception ex){

        }

    }

    public void update(Faction faction)  {
        try {
            String updateManual = "UPDATE " + getTable().getName()
                    + " SET  name = ?, tag = ? WHERE faction_id = ?";

            PreparedStatement prepare = getMySQL().prepareStatement(updateManual);
            prepare.setString(1, faction.getName());
            prepare.setString(2, faction.getTag());
            prepare.setInt(3, faction.getId());
            prepare.executeUpdate();

            prepare.close();
        }catch (Exception ex){

        }

    }

    @Override
    public Faction newInstance(ResultSet query) {
        Faction faction = new Faction("","");
        updateCache(faction,query);
        return faction;
    }

    @Override
    public void updateCache(Faction faction, ResultSet query) {
        try {
            faction.setName(query.getString("name"));
            faction.setTag(query.getString("tag"));
            faction.setId(query.getInt("faction_id"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}