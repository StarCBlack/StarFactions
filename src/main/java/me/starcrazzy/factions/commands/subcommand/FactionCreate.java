package me.starcrazzy.factions.commands.subcommand;

import me.starcrazzy.factions.commands.FacSubCommand;
import me.starcrazzy.factions.datas.faction.FactionManager;
import me.starcrazzy.factions.datas.faction.data.Faction;
import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionCreate extends FacSubCommand {


    public FactionCreate() {
        super("create", "criar");
    }

    @Override
    public void process(Player player, FactionsUser user, Faction faction, List<String> args) {

        String[] strings = args.toArray(new String[0]);


        if (user.hasFaction()) {
            player.sendMessage("§cVocê já tem uma facção!");
            return;
        }
        String name = strings[1], tag = strings[2];
        if (name.length() > 20 || name.length() < 5) {
            player.sendMessage("§cO nome de sua facção deve conter de 5 a 20 caracteres.");
            return;
        }
        if (tag.length() != 3) {
            player.sendMessage("§cA tag da facção deve conter 3 caracteres.");
            return;
        }
        if (FactionManager.getFactionByName(name) != null) {
            player.sendMessage("§cJá existe uma facção com esse nome!");
            return;
        }
        if (FactionManager.getFactionByTag(tag) != null) {
            player.sendMessage("§cJá existe um facção com essa tag!");
            return;
        }
        FactionManager.createNewFaction(name, tag);


        player.sendMessage("§eYAY! Sua facção foi criada com sucesso!");

    }
}
