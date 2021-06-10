package ru.job4j.exam5;

import ru.job4j.exam5.io.*;

import java.util.*;

public class AppUI {

    final class Record {

        private String oldValue;
        private String newValue;

        public Record(String aOldValue, String aNewValue) {
            oldValue = aOldValue;
            newValue = aNewValue;
        }
    }

    private static AppUI instance;

    private Input input;
    private Output output;
    // <Email, МножествоПользователей>
    private HashMap<String, HashSet<String>> mails;

    public AppUI(Input aInput, Output aOutput) {
        input = aInput;
        output = aOutput;
        mails = new HashMap<>();
    }

    /**
     * Время вполнения ≈ O(2nm),где
     * n - количество одинаковых пользователей
     * m - количество уникальных email
     */

    private void eliminateUsers() {
        ArrayDeque<Record> replaces = new ArrayDeque<>();
        for (Set<String> users : mails.values()) {
            if (users.size() > 1) {
                String[] elements = users.toArray(new String[users.size()]);
                for (int i = 1; i < elements.length; i++) {
                    replaces.push(new Record(elements[i], elements[0]));
                }
            }
        }
        while (!replaces.isEmpty()) {
            Record entry = replaces.pop();
            for (Set<String> users : mails.values()) {
                if (!users.contains(entry.oldValue)) {
                    continue;
                }
                users.remove(entry.oldValue);
                users.add(entry.newValue);
            }
        }
    }

    /**
     * Время вполнения - O(2n), где n - количество адресов email
     */

    private void report() {
        HashMap<String, HashSet<String>> packMails = new HashMap<>();
        for (String email : mails.keySet()) {
            Set<String> users = mails.get(email);
            for (String user : users) {
                HashSet<String> ms = packMails.get(user);
                if (ms == null) {
                    ms = new HashSet<>();
                    ms.add(email);
                    packMails.put(user, ms);
                } else {
                    ms.add(email);
                }
            }
        }
        for (String user : packMails.keySet()) {
            String line = user + " -> ";
            for (String email : packMails.get(user)) {
                line += email + ", ";
            }
            line = line.substring(0, line.length() - 2);
            output.println(line);
        }
    }

    /**
     * Время вполнения - O(n), где n - количество адресов email
     */

    private void readData() {
        int n = input.askInt("Количество пользователей: ");
        HashSet<String> users;
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
                    users = new HashSet<>();
                    users.add(msg[0]);
                    mails.put(m[j], users);
                }
            }
        }
    }

    /**
     * Время вполнения ≈ O(n + 2n + 2nm) = O(n(3 + 2m)),где
     * n - количество одинаковых пользователей
     * m - количество уникальных email
     */
    public void execute() {
        readData();
        eliminateUsers();
        report();
    }

    public static void main(String[] args) {
        Input in = new ConsoleInput();
        Output out = new ConsoleOutput();
        instance = new AppUI(new ValidateInput(in, out), out);
        instance.execute();
    }
}
