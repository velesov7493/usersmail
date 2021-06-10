package ru.job4j.exam5.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput implements Input {

    private static final Pattern VALID_LINE = Pattern.compile(
            "^\\w+\\s?\\->\\s?(\\w+([\\.\\w-_]+)*\\w@\\w((\\.\\w)*\\w+)*\\.\\w{2,3})"
            + "(,\\s?\\w+([\\.\\w-_]+)*\\w@\\w((\\.\\w)*\\w+)*\\.\\w{2,3})*$"
    );

    private final Input input;
    private final Output output;

    public ValidateInput(Input aInput, Output aOutput) {
        input = aInput;
        output = aOutput;
    }

    @Override
    public int askInt(String question) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question);
                invalid = false;
            } catch (NumberFormatException nfe) {
                output.println("Неправильный ввод. Введите число.");
            }
        } while (invalid);
        return value;
    }

    @Override
    public String askExpression(String question) {
        boolean invalid;
        String in;
        do {
            in = input.askExpression(question);
            Matcher m = VALID_LINE.matcher(in);
            invalid = !m.matches();
            if (invalid) {
                output.println("Ошибка синтаксиса в строке!");
                output.println("Повторите ввод в формате: userName -> email1, email2, ... emailN");
            }
        } while (invalid);
        return in;
    }
}