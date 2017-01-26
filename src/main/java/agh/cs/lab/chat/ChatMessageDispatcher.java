package agh.cs.lab.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class ChatMessageDispatcher {

    private final UserSessionManager manager;

    public ChatMessageDispatcher(final UserSessionManager manager) {
        this.manager = manager;
    }

    public void parseAndDispatchMessage(final Session session, final String json) {
        final JsonParser parser = new JsonParser();
        final JsonObject object = parser.parse(json).getAsJsonObject();
        final String type = object.getAsJsonPrimitive("type").getAsString();
        final String userName = object.getAsJsonPrimitive("name").getAsString();
        switch (type) {
            case "newuser":
                final String channel = object.getAsJsonPrimitive("channel").getAsString();
                manager.handleNewUser(session, userName, channel);
                break;
            case "message":
                final String message = object.getAsJsonPrimitive("message").getAsString();
                manager.handleIncomingMessage(session, message);
                break;
            default:
                break;

        }
    }

    public void dispatchClosedConnection(final Session session, final String reason) {
        manager.handleDisconnectedUser(session, reason);
    }
}
