package agh.cs.lab.channels;

import agh.cs.lab.names.NameAlreadyTakenException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 22.01.2017.
 */
public class ChannelManager {
    private final Map<String, IChannel> channelMap = new ConcurrentHashMap<>();

    public IChannel getChannelByName(final String channelName) {
        return channelMap.get(channelName);
    }

    public Collection<IChannel> getChannels() {
        return Collections.unmodifiableCollection(channelMap.values());
    }

    public List<String> getChannelNames() {
        return Collections.unmodifiableList(
                channelMap.values().stream().
                        map(IChannel::getName).
                        collect(Collectors.toList()));
    }

    public void addChannel(final IChannel channel) throws NameAlreadyTakenException {
        final String channelName = channel.getName();
        if (channelMap.containsKey(channelName)) {
            throw new NameAlreadyTakenException("Channel with name '" + channelName + "' already exists");
        }
        channelMap.put(channelName, channel);
    }
}
