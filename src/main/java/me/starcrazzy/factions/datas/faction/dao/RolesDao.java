package me.starcrazzy.factions.datas.faction.dao;

import lombok.Getter;
import lombok.Setter;
import me.starcrazzy.factions.database.table.TableColumn;
import me.starcrazzy.factions.datas.faction.data.Role;
import me.starcrazzy.factions.database.dao.DaoBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter @Setter
public class RolesDao extends DaoBase<Role> {



    public RolesDao() {
        super("faction_roles");

    }


    public void createTable() {

        table.addColumn("id", TableColumn.ID);
        table.addColumn("faction_id", TableColumn.INTEGER);
        table.addColumn("position", TableColumn.INTEGER);
        table.addColumn("name", TableColumn.STRING);
        table.addColumn("tag", TableColumn.STRING);
        table.addColumn("description", TableColumn.STRING);
        table.addColumn("permissions", TableColumn.TEXT);
        table.create();
    }


    public void insert(Role element)  {
        try {
            getTable().insert(
                    "faction_id",
                    "position",
                    "name",
                    "tag",
                    "description",
                    "permissions"
            ).one(
                    element.getFactionId(),
                    element.getPosition(),
                    element.getName(),
                    element.getTag(),
                    element.getDescription(),
                    element.getPermissionString()
            );
        }catch (Exception ex){

        }

    }

    public void update(Role role)  {
        try {
            String updateManual = "UPDATE " + getTable().getName()
                    + " SET faction_id = ?, position = ?, name = ?, tag = ?, description = ?, permissionString = ?  WHERE id = ?";

            PreparedStatement prepare = getMySQL().prepareStatement(updateManual);
            prepare.setInt(1, role.getFactionId());
            prepare.setInt(3, role.getPosition());
            prepare.setString(3, role.getName());
            prepare.setString(4, role.getTag());
            prepare.setString(5, role.getDescription());
            prepare.setString(6, role.getPermissionString());
            prepare.setInt(7, role.getId());
            prepare.executeUpdate();

            prepare.close();
        }catch (Exception ex){

        }

    }

    @Override
    public Role newInstance(ResultSet query) {
        Role role =  new Role();
        updateCache(role,query);
        return role;
    }

    @Override
    public void updateCache(Role role, ResultSet query) {
        try {
            role.setPosition(query.getInt("position"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
