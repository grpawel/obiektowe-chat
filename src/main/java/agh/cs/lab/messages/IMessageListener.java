package agh.cs.lab.messages;

import java.util.function.Predicate;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public interface IMessageListener {
    void onNewMessage(Message message);
}
