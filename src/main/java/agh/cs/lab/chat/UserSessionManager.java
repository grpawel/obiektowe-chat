package agh.cs.lab.chat;

import agh.cs.lab.messages.IMessageSender;
import agh.cs.lab.utils.BidirectionalMap;
import agh.cs.lab.channels.ChannelManager;
import agh.cs.lab.channels.IChannel;
import agh.cs.lab.messages.Message;
import agh.cs.lab.messages.MessageFormatter;
import agh.cs.lab.users.IUser;
import agh.cs.lab.users.User;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class UserSessionManager implements IMessageSender
{
    private final BidirectionalMap<Session, User> sessionUserMap = new BidirectionalMap<>();
    private final ChannelManager channelManager;
    private final MessageFormatter messageFormatter;

    public UserSessionManager(final ChannelManager channelManager, final MessageFormatter messageFormatter) {
        this.channelManager = channelManager;
        this.messageFormatter = messageFormatter;
    }

    public void handleNewUser(final Session session, final String userName, final String channelName) {
        final User user = new User(userName, this);
        sessionUserMap.put(session, user);
        final IChannel channel = channelManager.getChannelByName(channelName);
        channel.addUser(user);
        channel.onNewMessage(messageFormatter.userJoinedMessage(user));
    }

    public void handleIncomingMessage(final Session session, final String message) {
        final User user = sessionUserMap.get(session);
        final Message mes = new Message(user, message);
        user.plantMessage(mes);
    }

    @Override
    public void sendMessage(final User user, final Message message) {
        final Session session = sessionUserMap.getKey(user);
        try {
            session.getRemote().sendString(messageFormatter.formatJSONMessage(user, message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDisconnectedUser(final Session session, final String reason) {
        final IUser user = sessionUserMap.get(session);
        final IChannel channel = user.getChannel();
        channel.removeUser(user);
        sessionUserMap.removeByKey(session);
        channel.onNewMessage(messageFormatter.userDisconnectedMessage(user, reason));
    }
}
