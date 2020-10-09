package me.starcrazzy.factions.datas.faction;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.starcrazzy.factions.datas.faction.dao.FactionDao;
import me.starcrazzy.factions.datas.faction.data.Faction;
import me.starcrazzy.factions.datas.user.dao.FactionUserDao;
import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FactionManager {
    private static FactionDao factionDao = new FactionDao();
    private static FactionUserDao userDao = new FactionUserDao();
    @Getter
    private static  HashMap<String, FactionsUser> users = Maps.newHashMap();
    private static Map<Integer, Faction> by_id = new HashMap<>();
    private static Map<String, Faction> by_name = new HashMap<>();
    private static Map<String, Faction> by_tag = new HashMap<>();

    public static FactionsUser find(Player player) {
        String username = player.getName();

        if (users.containsKey(username.toLowerCase())) {
            return users.get(username.toLowerCase());
        }
        FactionsUser user = new FactionsUser();
        user.setUsername(player.getName());

        userDao.insert(user);
        users.put(username.toLowerCase(), user);
        return user;

    }



    public static void reloadFactions(){
        by_id.clear();
        //hashcode
        by_tag.clear();
        by_name.clear();
        for (Faction fac : factionDao.selectAll()){
            by_id.put(fac.getId(),fac);
            by_name.put(fac.getName().toLowerCase(),fac);
            by_tag.put(fac.getTag().toLowerCase() ,  fac );
        }

    }

    public static void reloadUsers() {
        users.clear();
        for(FactionsUser factionsUser : userDao.selectAll()) {
            users.put(factionsUser.getFactionName(), factionsUser);
        }
    }
    public static Faction getFactionByName(String name) {
        return by_name.get(name.toLowerCase());
    }

    public static Faction getFactionByTag(String tag) {
        return by_tag.get(tag.toLowerCase());
    }
    public static Faction createNewFaction(String name, String tag)  {
        try {
            Faction faction = new Faction(name, tag);
            factionDao.insert(faction);

            by_name.put(name.toLowerCase(), faction);
            by_tag.put(tag.toLowerCase(), faction);
            by_id.put(faction.getId(), faction);


            //  by_name.get("STaGe".toLowerCase());
            return faction;
        }catch (Exception ex){
            ex.printStackTrace();
        }
      return null;
    }

}
