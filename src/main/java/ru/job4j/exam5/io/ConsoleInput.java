package ru.job4j.exam5.io;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private Scanner sc;

    public ConsoleInput() {
        sc = new Scanner(System.in);
    }

    @Override
    public String askExpression(String question) {
        System.out.print(question);
        return sc.nextLine();
    }

    @Override
    public int askInt(String question) {
        return Integer.parseInt(askExpression(question));
    }
}
