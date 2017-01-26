package agh.cs.lab.users.chatbot;

import agh.cs.lab.messages.Message;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public interface IQuestion {
    Boolean matches(Message message);
    String response(Message message);
}
