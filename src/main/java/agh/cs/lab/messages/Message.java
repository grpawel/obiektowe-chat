package agh.cs.lab.messages;

import agh.cs.lab.utils.Named;

import java.util.Date;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class Message {
    private final Named sender;
    private final String message;
    private final Date date;

    public Message(final Named sender, final String message) {
        this.sender = sender;
        this.message = message;
        this.date = new Date();

    }

    public Named getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        //to prevent changes
        return new Date(date.getTime());
    }
}
