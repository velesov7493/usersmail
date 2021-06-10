package ru.job4j.exam5.io;

public class StubInput implements Input {

    private String[] answers;
    private int position;

    public StubInput(String[] aAnswers) {
        answers = aAnswers;
    }

    @Override
    public String askExpression(String question) {
        if (position >= answers.length) {
            position = 0;
        }
        return answers[position++];
    }

    @Override
    public int askInt(String question) {
        return Integer.parseInt(askExpression(question));
    }
}
