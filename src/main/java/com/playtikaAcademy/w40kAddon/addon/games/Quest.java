package com.playtikaAcademy.w40kAddon.addon.games;


import java.util.Scanner;

/**
 * 28.09.2019 23:53
 *
 * @author Edward
 */
public class Quest {

    Scanner scanner = new Scanner(System.in);


    public String readFromConsole(){
        int i = scanner.nextInt();
        scanner.close();
        System.out.println(i);
        return String.valueOf(i);
    }
}
