package agh.cs.lab.messages;

import agh.cs.lab.users.User;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public interface IMessageSender {
    void sendMessage(User user, Message message);
}
