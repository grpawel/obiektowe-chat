package agh.cs.lab.channels;

import agh.cs.lab.utils.Named;
import agh.cs.lab.messages.IMessageListener;
import agh.cs.lab.messages.Message;
import agh.cs.lab.users.IUser;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class Channel implements IChannel {
    private final Set<IUser> users = new ConcurrentHashSet<>();
    private final String name;
    private final Set<IMessageListener> listeners = new ConcurrentHashSet<>();

    public Channel(String name) {
        this.name = name;
    }

    @Override
    public void addUser(IUser user) {
        users.add(user);
        addMessageListener(user);
        user.setChannel(this);
    }

    @Override
    public void removeUser(final IUser user) {
        users.remove(user);
        removeMessageListener(user);
        user.setChannel(null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<IUser> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public Collection<String> getUserNames() {
        return Collections.unmodifiableCollection(users.stream()
                .map(Named::getName)
                .collect(Collectors.toList()));
    }

    @Override
    public void addMessageListener(final IMessageListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onNewMessage(final Message message) {
        listeners.forEach(listener -> listener.onNewMessage(message));
    }

    @Override
    public void removeMessageListener(final IMessageListener listener) {
        listeners.remove(listener);
    }

}
