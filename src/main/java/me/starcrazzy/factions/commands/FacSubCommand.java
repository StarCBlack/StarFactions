package me.starcrazzy.factions.commands;

import me.starcrazzy.factions.datas.faction.data.Faction;
import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract  public class FacSubCommand {

    public FacSubCommand(String name,String... aliases) {
        this.name = name;
        this.aliases.addAll(Arrays.asList(aliases));

    }

    private String name;
    private ArrayList<String> aliases = new ArrayList<>();

    public String permission(){ return "faction.command."+name;};

    public boolean checkPermission(CommandSender player){
        if (!player.hasPermission(permission())){
            player.sendMessage("");
            return false;
        }

        return true;
    }


    abstract public void process(Player player, FactionsUser user, Faction faction, List<String> args);



    public boolean isUsed(String argument){
        if (name.equalsIgnoreCase(argument))return  true;
        for (String alias : aliases){
            if (alias.equalsIgnoreCase(argument))return true;
        }
        return false;
    }




}
