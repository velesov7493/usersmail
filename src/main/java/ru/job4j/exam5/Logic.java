package ru.job4j.exam5;

import java.util.*;

public interface Logic {

    final class Record {

        private String oldValue;
        private String newValue;

        public Record(String aOldValue, String aNewValue) {
            oldValue = aOldValue;
            newValue = aNewValue;
        }
    }

    /**
     * Перевернуть Map
     * @param data - Map<Key, Set<Values>>
     * @return Map<Value, Set<Keys>>
     */
    static Map<String, Set<String>> invert(Map<String, Set<String>> data) {
        Map<String, Set<String>> result = new HashMap<>();
        for (String key : data.keySet()) {
            Set<String> values = data.get(key);
            for (String value : values) {
                Set<String> subValues = result.get(value);
                if (subValues == null) {
                    subValues = new LinkedHashSet<>();
                    subValues.add(key);
                    result.put(value, subValues);
                } else {
                    subValues.add(key);
                }
            }
        }
        return result;
    }

    /**
     * Сократить пользователей в карте <Email, Set<UserName>> и выдать обратную карту
     * @param data - карта мейлов со множествами пользователей <Email, Set<UserName>>
     * @return карта пользователей со множествами мейлов <UserName, Set<Email>>
     */
    static Map<String, Set<String>> reduceUsers(Map<String, Set<String>> data) {
        ArrayDeque<Record> replaces = new ArrayDeque<>();
        for (Set<String> users : data.values()) {
            if (users.size() > 1) {
                String[] elements = users.toArray(new String[users.size()]);
                for (int i = 1; i < elements.length; i++) {
                    replaces.push(new Record(elements[i], elements[0]));
                }
            }
        }
        while (!replaces.isEmpty()) {
            Record entry = replaces.pop();
            for (Set<String> users : data.values()) {
                if (!users.contains(entry.oldValue)) {
                    continue;
                }
                users.remove(entry.oldValue);
                users.add(entry.newValue);
            }
        }
        return invert(data);
    }

    /**
     * Объединение списков пользователей
     *
     * @param data - карта пользователей со множествами мейлов
     * @return карта пользователей со множествами мейлов
     */
    static Map<String, Set<String>> merge(Map<String, Set<String>> data) {
        Map<String, Set<String>> invertData = invert(data);
        return reduceUsers(invertData);
    }
}
