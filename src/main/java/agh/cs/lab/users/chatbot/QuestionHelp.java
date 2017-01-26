package agh.cs.lab.users.chatbot;

import agh.cs.lab.messages.Message;
import agh.cs.lab.utils.LevenshteinMatcher;

/**
 * Created by Paweł Grochola on 24.01.2017.
 */
public class QuestionHelp implements IQuestion {
    private static final Integer MAX_DISTANCE = 1;
    private static final String QUESTION_1 = "pomoc";
    private static final String QUESTION_2 = "help";
    @Override
    public Boolean matches(final Message message) {
        return new LevenshteinMatcher().matches(QUESTION_1, message.getMessage().toLowerCase(), MAX_DISTANCE)
                || new LevenshteinMatcher().matches(QUESTION_2, message.getMessage().toLowerCase(), MAX_DISTANCE);
    }

    @Override
    public String response(final Message message) {
        return "Wpisz jedna z poniższych wiadomości aby ChatBot odpowiedział:\n"
                + "Która godzina?\n"
                + "Jaki dziś dzień tygodnia?\n"
                + "Jaka jest pogoda w Krakowie?";
    }
}

