package me.starcrazzy.factions.utils.cooldown;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Cooldown {

    public static Table<String, String, Long> cooldowns = HashBasedTable.create();

    public static void init(Player player, String key, long delay, TimeUnit unit){
        init(player.getName(), key, delay, unit);
    }

    public static void init(String playerName, String key, long delay, TimeUnit unit){
        cooldowns.put(playerName, key, System.currentTimeMillis() + unit.toMillis(delay));
    }

    public static boolean hasEnded(String playerName, String key){
        if (!cooldowns.contains(playerName, key)) {
            return true;
        }
        if (cooldowns.get(playerName, key) <= System.currentTimeMillis()){
            cooldowns.remove(playerName, key);
            return true;
        }
        return false;
    }

    public static boolean hasEnded(Player player, String key){
        return hasEnded(player.getName(), key);
    }

    public static boolean remove(String player, String key){
        if (cooldowns.contains(player, key)){
            cooldowns.remove(player, key);
            return true;
        }
        return false;
    }

    public static long getMillisLeft(String player, String key){
        if (!cooldowns.contains(player, key)) {
            return 0L;
        }
        if (cooldowns.get(player, key) <= System.currentTimeMillis()){
            cooldowns.remove(player, key);
            return 0L;
        }
        return cooldowns.get(player, key) - System.currentTimeMillis();
    }

    public static int getSecondsLeft(String playerName, String key){
        return (int)TimeUnit.MILLISECONDS.toSeconds(getMillisLeft(playerName, key)) + 1;
    }

    public static void reset(){
        cooldowns.clear();
    }

    public static String getTimeLeft(String player, String key){
        StringBuilder sb = new StringBuilder();
        int totalSeconds = getSecondsLeft(player, key);

        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int aux = totalSeconds;
        if (totalSeconds > 3600){
            hours = totalSeconds / 3600;
            aux -= hours * 3600;

            sb.append(hours).append(" horas, ");
        } else if(totalSeconds == 3600){
            hours = totalSeconds / 3600;
            aux -= hours * 3600;

            sb.append(hours).append(" hora, ");
        }
        if (aux > 60){
            minutes = aux / 60;
            aux -= minutes * 60;

            sb.append(minutes).append(" minutos, ");
        } else if (aux == 60){
            minutes = aux / 60;
            aux -= minutes * 60;

            sb.append(minutes).append(" minuto, ");
        }
        if (aux > 0){
            seconds = aux;
            aux -= seconds;

            sb.append(seconds).append(" segundos");
        } else if (aux == 0){
            seconds = aux;
            aux -= seconds;

            sb.append(seconds).append(" segundo");
        }
        return sb.toString().substring(0, sb.toString().length());
    }

    public static String formatIntoMMSS(int secs) {
        int remainder = secs % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;

        return ((minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
    }

    public static String formatTime(int secs) {
        int remainder = secs % 86400;

        int days     = secs / 86400;
        int hours     = remainder / 3600;
        int minutes    = (remainder / 60) - (hours * 60);
        int seconds    = (remainder % 3600) - (minutes * 60);

        if (days > 0) {
            return days+ "d" + hours + "h" + minutes + "m" + seconds + "s";
        } else if (hours > 0) {
            return hours + "h" + minutes + "m" + seconds + "s";
        } else if (minutes > 0) {
            return minutes + "m" + seconds + "s";
        } else {
            return seconds + "s";
        }
    }

    public static void calendario(){

    }
}