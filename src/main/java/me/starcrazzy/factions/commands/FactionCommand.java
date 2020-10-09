package me.starcrazzy.factions.commands;

import lombok.SneakyThrows;
import me.starcrazzy.factions.commands.subcommand.FactionCreate;
import me.starcrazzy.factions.datas.faction.FactionManager;
import me.starcrazzy.factions.datas.faction.data.Faction;
import me.starcrazzy.factions.datas.user.data.FactionsUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactionCommand implements CommandExecutor {

    private List<FacSubCommand> subCommands = new ArrayList<>();
    public FactionCommand(){
        subCommands.add(new FactionCreate());

    }


    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                //
            } else {
                String sub = args[0];
                for (FacSubCommand cmd : subCommands) {
                    if (cmd.isUsed(sub)) {

                        FactionsUser user = FactionManager.find(player);
                        Faction faction = user.getFaction();
                        cmd.process(player, user, faction, Arrays.asList(args));
                        return true;
                    }
                }
                sender.sendMessage("§cEsse comando de faction não existe.");
            }
        }
        return false;
    }
}
