package com.attila.ui;

import java.util.Scanner;

public class UI {

    Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.nextLine();
    }

    public void printMessage(String string) {
        System.out.println(string);
    }

    public void close() {
        scanner.close();
    }
}
