package agh.cs.lab.users;

import agh.cs.lab.channels.IChannel;
import agh.cs.lab.messages.IMessageListener;
import agh.cs.lab.messages.IMessageSender;
import agh.cs.lab.messages.Message;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Set;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class User implements IUser {
    private final String name;

    private final Set<IMessageListener> listeners = new ConcurrentHashSet<>();
    private IChannel channel = null;
    private final IMessageSender messageSender;
    //users with same name are not the same, so id is needed to distinguish them
    private final Integer id;
    private static Integer next_id = 0;

    public User(final String name, final IMessageSender messageSender) {
        this.name = name;
        this.messageSender = messageSender;
        id = next_id++;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setChannel(final IChannel channel) {
        if (this.channel != null) {
            removeMessageListener(this.channel);
        }
        if (channel != null) {
            addMessageListener(channel);
        }
        this.channel = channel;
    }

    @Override
    public IChannel getChannel() {
        return channel;
    }

    @Override
    public void addMessageListener(final IMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeMessageListener(final IMessageListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onNewMessage(final Message message) {
        messageSender.sendMessage(this, message);
    }

    public void plantMessage(final Message message) {
        listeners.forEach(listener -> listener.onNewMessage(message));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        if (!name.equals(user.name)) return false;
        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
