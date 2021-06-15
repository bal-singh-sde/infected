package com.infected.util;

import java.util.Scanner;

public class TextScanner {
    public static String[] newScanner() {
        Scanner scan = new Scanner(System.in);
        Animation.printYellow("Enter your command here: ");

        String command = scan.nextLine();
        return command.toLowerCase().split(" ");
    }
}
