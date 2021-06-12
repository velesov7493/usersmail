package ru.job4j.exam5;

import ru.job4j.exam5.io.*;

import java.util.*;

public class AppUI {

    private static AppUI instance;

    private Input input;
    private Output output;
    // <Email, МножествоПользователей>
    private Map<String, Set<String>> mails;

    public AppUI(Input aInput, Output aOutput) {
        input = aInput;
        output = aOutput;
        mails = new HashMap<>();
    }

    /**
     * Время выполнения операций ввода зависит от пользователя
     * Поэтому в этой задаче производительность зависит от того,
     * как будет обработан ввод пользователя.
     * Время вполнения без учета ввода-вывода - O(m), где m - количество адресов email
     */
    private void readData() {
        int n = input.askInt("Количество пользователей: ");
        Set<String> users;
        for (int i = 0; i < n; i++) {
            String line = input.askExpression((i + 1) + ": ");
            String[] msg = line.split("->", 2);
            String[] m = msg[1].split(",");
            msg[0] = msg[0].trim();
            for (int j = 0; j < m.length; j++) {
                m[j] = m[j].trim();
                users = mails.get(m[j]);
                if (users != null) {
                    users.add(msg[0]);
                } else {
                    users = new LinkedHashSet<>();
                    users.add(msg[0]);
                    mails.put(m[j], users);
                }
            }
        }
    }

    /**
     * Время вполнения ≈ O(m + 2m + 2nm) = O(m(3 + 2n)),где
     * n - количество одинаковых пользователей
     * m - количество уникальных email
     */
    public void execute() {
        readData();
        Map<String, Set<String>> data = Logic.reduceUsers(mails);
        for (String user : data.keySet()) {
            String line = user + " -> ";
            for (String email : data.get(user)) {
                line += email + ", ";
            }
            line = line.substring(0, line.length() - 2);
            output.println(line);
        }
    }

    public static void main(String[] args) {
        Input in = new ConsoleInput();
        Output out = new ConsoleOutput();
        instance = new AppUI(new ValidateInput(in, out), out);
        instance.execute();
    }
}