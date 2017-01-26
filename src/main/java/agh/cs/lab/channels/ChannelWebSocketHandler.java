package agh.cs.lab.channels;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


/**
 * Created by Paweł Grochola on 20.01.2017.
 */
@WebSocket
public class ChannelWebSocketHandler {

    private final ChannelMessageDispatcher dispatcher;

    public ChannelWebSocketHandler(ChannelMessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @OnWebSocketConnect
    public void onConnect(final Session session) throws Exception {
        dispatcher.handleOpenConnection(session);
    }

    @OnWebSocketClose
    public void onClose(final Session session, final int statusCode, final String reason) {
        dispatcher.handleClosedConnection(session);
    }

    @OnWebSocketMessage
    public void onMessage(final Session session, final String message) {
        //assumption: client's only message to this socket is new channel name
        dispatcher.handleNewChannel(message);
    }

}
