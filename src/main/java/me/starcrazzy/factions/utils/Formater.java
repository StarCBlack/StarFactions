package me.starcrazzy.factions.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formater {

    private static final String[] suffix = { "", "K", "M", "B", "T", "QD", "QQ", "S", "SEP", "O", "N", "DEC" };

    public static BigDecimal backwards(String string) {
        int zeros = 3;
        for (String suffix : suffix) {
            if (suffix.isEmpty()) continue;
            if (string.endsWith(suffix)) {
                return new BigDecimal(string.substring(0, string.length()-suffix.length())).movePointRight(zeros);
            }
            zeros+= 3;
        }
        return new BigDecimal(string);
    }

    public static String format(Number number) {
        return format(new BigDecimal(number.toString()));
    }

    public static String format(BigDecimal decimal) {
        decimal = decimal.setScale(0, RoundingMode.DOWN);
        int zeros = decimal.precision() - 1;
        if (zeros >= 3) {
            int k = zeros / 3;
            if (k >= suffix.length) {
                return "+999.9 DEC";
            }
            return decimal.movePointLeft((k * 3)).setScale(1, RoundingMode.DOWN).toString() + suffix[k];
        } else {
            return decimal.setScale(0).toString();
        }
    }

}