package com.belhard.utils;

import java.math.BigDecimal;
import java.util.Scanner;

public class CommandReader {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static int readMenuNumber(String message) {
        System.out.print(message);
        return Integer.parseInt(SCANNER.nextLine());
    }

    public static long readIdNumber(String message) {
        System.out.print(message);
        return Long.parseLong(SCANNER.nextLine());
    }

    public static String readString(String message) {
        System.out.print(message);
        return SCANNER.nextLine();
    }

    public static BigDecimal readPrice(String message) {
        System.out.print(message);
        return BigDecimal.valueOf(Double.parseDouble(SCANNER.nextLine()));
    }
}
