package com.movie.backend.ultity;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtil {
    public static String formatToVND(long amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount);
    }
}
