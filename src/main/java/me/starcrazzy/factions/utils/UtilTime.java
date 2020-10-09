package me.starcrazzy.factions.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UtilTime {

    private static final Pattern timePattern = Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
    private static final HashMap<Character, Integer> timeUnit = new HashMap<>();
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    static {
        timeUnit.put('d', 86400);
        timeUnit.put('h', 3600);
        timeUnit.put('m', 60);
        timeUnit.put('s', 1);
    }

    public static String formatMMSS(int i) {
        int m = i / 60;
        int s = i % 60;
        return m+":"+(s > 9 ? s : "0"+s);
    }

    public static String formatDDHHMMSS(int reaming) {
        int days = reaming / 86400;
        int hours = (reaming % 86400) / 3600;
        int minutes = (reaming % 86400 % 3600) / 60;
        int seconds = (reaming % 86400 % 3600 % 60);
        String string = "";
        if (days > 0) string+= days+"d ";
        if (hours > 0) string+= hours+"h ";
        if (minutes > 0) string+= minutes+"m ";
        if (seconds > 0) string+= seconds+"s";
        return string.trim();
    }

    public static long toMillis(String time) {
        int seconds = 0;

        Integer i = null;
        Character ch = null;

        String[] array = timePattern.split(time);

        if (array.length % 2 != 0)
            return -1;

        for (String s : array) {
            if (i == null) {
                i = Integer.parseInt(s);
            } else {
                if (s.length() > 1) {
                    return -1;
                }
                ch = s.charAt(0);
                if (!timeUnit.containsKey(ch)) {
                    return -1;
                }
                seconds += (i * timeUnit.get(ch));
                i = null;
            }
        }
        return (seconds <= 0 ? -1 : seconds * 1000L);
    }

    public static String formatDate(long time) {
        return formatDate(new Date(time));
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
