package agh.cs.lab.channels;

import agh.cs.lab.messages.IMessageListener;
import agh.cs.lab.messages.IMessageProvider;
import agh.cs.lab.users.IUser;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public interface IChannel
        extends IMessageListener, IMessageProvider {
    void addUser(IUser user);

    void removeUser(IUser user);

    String getName();

    Set<IUser> getUsers();

    Collection<String> getUserNames();
}
