package me.starcrazzy.factions.datas.faction.data;

import lombok.Getter;
import lombok.Setter;
import me.starcrazzy.factions.datas.faction.dao.RolesDao;
import me.starcrazzy.factions.datas.faction.enums.FactionPermissions;

import java.util.ArrayList;
public class Role implements Cloneable{

    @Getter @Setter
    private static RolesDao dao = new RolesDao();

    @Override
    protected Role clone(){
        try {
            return (Role) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert()
    {
        dao.insert(this);
    }

    @Getter @Setter
    private static ArrayList<Role> roles = new ArrayList<>();
    @Getter @Setter
    private  ArrayList<FactionPermissions> permissions = new ArrayList<>();
    @Getter @Setter
    private String name, description, tag;
    @Getter @Setter
    private int position, factionId, id;


    public static void simplesRoles(){
        {
            Role role = new Role();
            role.setName("Recruta");
            role.setPosition(1);
            roles.add(role);

        }
        {
            Role role = new Role();
            role.setName("Membro");
            role.setPosition(2);
            roles.add(role);
        }
        {
            Role role = new Role();
            role.setName("Capitão");
            role.setPosition(3);
            roles.add(role);

        }
        {
            Role role = new Role();
            role.setName("Líder");
            role.setPosition(4);
            roles.add(role);
        }
    }
    public String getPermissionString() {
        StringBuilder builder = new StringBuilder();
        for (FactionPermissions permission : permissions) {
            builder.append(permission);
            builder.append("");

        }
        return builder.toString();
    }
    public void  updatePermission(String permissionString ){

        String[] split = permissionString.split(";");
        this.permissions.clear();
        for (String str : split){
            if (str.equals(""))continue;
            try {
                this.permissions.add(FactionPermissions.valueOf(str.toUpperCase()));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


    }

}
