package agh.cs.lab.messages;

import agh.cs.lab.users.IUser;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Objects;

import static j2html.TagCreator.*;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class MessageFormatter {
    public String formatJSONMessage(final IUser recipient, final Message message) {
        try {
            return String.valueOf(new JSONObject()
                    .put("userMessage", createHtmlMessageFromSender(message))
                    .put("userlist", recipient.getChannel().getUserNames()));
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Message userJoinedMessage(final IUser user) {
        return createServerMessage(user.getName() + " dołączył.");
    }

    public Message userDisconnectedMessage(final IUser user, final String reason) {
        if(reason != null && !Objects.equals(reason, "")) {
            return createServerMessage(user.getName() + " rozłączył się. Powód: " + reason);
        }
        return createServerMessage(user.getName() + " rozłączył się.");
    }

    //Builds a HTML element with a sender-name, a message, and a timestamp.
    private String createHtmlMessageFromSender(final Message message) {
        return article().with(
                b(message.getSender().getName() + " pisze:"),
                p(message.getMessage()),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(message.getDate()))
        ).render();
    }

    private Message createServerMessage(final String message) {
        return new Message(() -> "SERWER", message);
    }
    
}
