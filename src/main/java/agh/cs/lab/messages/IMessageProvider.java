package agh.cs.lab.messages;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public interface IMessageProvider {
    void addMessageListener(IMessageListener listener);
    void removeMessageListener(IMessageListener listener);
}