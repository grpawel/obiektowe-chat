package agh.cs.lab.channels;

import agh.cs.lab.names.NameAlreadyTakenException;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class ChannelMessageDispatcher {
    private final ChannelManager channelManager;
    private final Set<Session> openSessions = new ConcurrentHashSet<>();

    public ChannelMessageDispatcher(final ChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    public void handleNewChannel(final String channelName) {
        try {
            channelManager.addChannel(new Channel(channelName));
        } catch (NameAlreadyTakenException e) {
            //send message to user
        }
        openSessions.forEach((session) -> {
            try {
                sendChannelList(session);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendChannelList(final Session session) throws JSONException, IOException {
        session.getRemote().sendString(String.valueOf(new JSONObject()
                .put("channellist", channelManager.getChannelNames().stream()
                        .sorted((s, str) -> str.compareToIgnoreCase(s))
                        .collect(Collectors.toList()))));
    }

    public void handleOpenConnection(final Session session) {
        try {
            sendChannelList(session);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        openSessions.add(session);
    }

    public void handleClosedConnection(final Session session) {
        openSessions.remove(session);
    }
}
