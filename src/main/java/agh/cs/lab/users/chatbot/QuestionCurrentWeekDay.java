package agh.cs.lab.users.chatbot;

import agh.cs.lab.messages.Message;
import agh.cs.lab.utils.LevenshteinMatcher;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class QuestionCurrentWeekDay implements IQuestion {
    private static final Integer MAX_DISTANCE = 6;
    private static final String MATCHING_QUESTION = "jaki dziś dzień tygodnia?";
    private static final Map<Integer, String> dayNames = new HashMap<>();
    static {
        dayNames.put(1,"Poniedziałek");
        dayNames.put(2,"Wtorek");
        dayNames.put(3,"Środa");
        dayNames.put(4,"Czwartek");
        dayNames.put(5,"Piątek");
        dayNames.put(6,"Sobota");
        dayNames.put(7,"Niedziela");
    }

    @Override
    public Boolean matches(final Message message) {
        return new LevenshteinMatcher().matches(MATCHING_QUESTION, message.getMessage().toLowerCase(), MAX_DISTANCE);
    }

    @Override
    public String response(final Message message)
    {
        final Integer dayNumber = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        return dayNames.get(dayNumber);
    }
}
