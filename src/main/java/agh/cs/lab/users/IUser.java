package agh.cs.lab.users;

import agh.cs.lab.utils.Named;
import agh.cs.lab.channels.IChannel;
import agh.cs.lab.messages.IMessageListener;
import agh.cs.lab.messages.IMessageProvider;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public interface IUser
        extends IMessageListener, IMessageProvider, Named {

    void setChannel(IChannel channel);

    IChannel getChannel();

}