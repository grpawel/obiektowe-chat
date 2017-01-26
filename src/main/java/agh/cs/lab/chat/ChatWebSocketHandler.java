package agh.cs.lab.chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


/**
 * Created by Paweł Grochola on 20.01.2017.
 */
@WebSocket
public class ChatWebSocketHandler {

    private final ChatMessageDispatcher dispatcher;

    public ChatWebSocketHandler(final ChatMessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @OnWebSocketConnect
    public void onConnect(final Session user) throws Exception {
    }

    @OnWebSocketClose
    public void onClose(final Session user, final int statusCode, final String reason) {
        dispatcher.dispatchClosedConnection(user, reason);
    }

    @OnWebSocketMessage
    public void onMessage(final Session user, final String message) {
        dispatcher.parseAndDispatchMessage(user, message);
    }

}
