package com.company.main.utils;

/**
 * Created by VeaVictis on 09/11/16.
 * From http://martin.ankerl.com/?s=pow
 */
public final class Pow {
    public static double pow(final double a, final double b) {
        final long tmp = Double.doubleToLongBits(a);
        final long tmp2 = (long) (b * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
    }
}
