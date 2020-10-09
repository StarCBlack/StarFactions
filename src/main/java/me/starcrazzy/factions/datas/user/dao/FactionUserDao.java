package me.starcrazzy.factions.datas.user.dao;

import me.starcrazzy.factions.database.table.TableColumn;
import me.starcrazzy.factions.database.dao.DaoBase;
import me.starcrazzy.factions.datas.user.data.FactionsUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author oNospher
 **/
public class FactionUserDao extends DaoBase<FactionsUser> {

    public FactionUserDao() {
       super("factions_users");
    }


    public void createTable() {
        table.addColumn("username", TableColumn.UUID);
        table.addColumn("power", TableColumn.DOUBLE);
        table.addColumn("powerMax", TableColumn.DOUBLE);
        table.addColumn("faction", TableColumn.STRING);
        table.create();
    }

    public void update(FactionsUser factionsUser)  {
        try {
            String updateManual = "UPDATE " + getTable().getName()
                    + " SET power = ?, powerMax = ?, faction =  WHERE username = ?";

            PreparedStatement prepare = getMySQL().prepareStatement(updateManual);
            prepare.setDouble(1, factionsUser.getPower());
            prepare.setDouble(2, factionsUser.getPowerMax());
            prepare.setString(3, factionsUser.getFaction());
            prepare.setString(4, factionsUser.getUsername());
            prepare.executeUpdate();

            prepare.close();
        }catch (Exception ex){

        }

    }

    @Override
    public FactionsUser newInstance(ResultSet query) {
        FactionsUser factionsUser =  new FactionsUser();
        updateCache(factionsUser,query);
        return factionsUser;
    }

    @Override
    public void updateCache(FactionsUser factionsUser, ResultSet query) {
        try {
            factionsUser.setFaction(query.getString("faction"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(FactionsUser element)  {


        try {
            table.insert(
                    "username",
                    "power",
                    "powerMax",
                    "faction"
            ).one(
                    element.getUsername(),
                    element.getPower(),
                    element.getPowerMax(),
                    element.getFaction()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}