package agh.cs.lab.users.chatbot;

import agh.cs.lab.messages.Message;
import agh.cs.lab.utils.LevenshteinMatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class QuestionCurrentHour implements IQuestion {
    private static final Integer MAX_DISTANCE = 4;
    private static final String MATCHING_QUESTION = "która godzina?";
    @Override
    public Boolean matches(final Message message) {
        return new LevenshteinMatcher().matches(MATCHING_QUESTION, message.getMessage().toLowerCase(), MAX_DISTANCE);
    }

    @Override
    public String response(final Message message) {
        return new SimpleDateFormat("k:mm:ss").format(new Date());
    }
}
