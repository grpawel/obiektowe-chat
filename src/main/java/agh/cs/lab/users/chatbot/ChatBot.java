package agh.cs.lab.users.chatbot;

import agh.cs.lab.channels.IChannel;
import agh.cs.lab.messages.IMessageListener;
import agh.cs.lab.messages.Message;
import agh.cs.lab.users.IUser;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class ChatBot implements IUser {
    private final Set<IMessageListener> listeners = new ConcurrentHashSet<>();
    private IChannel channel = null;
    private final List<IQuestion> questions = new ArrayList<>();
    private final String name;

    public ChatBot(final String name) {
        this.name = name;
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
    public String getName() {
        return name;
    }

    @Override
    public void onNewMessage(final Message message) {
        final Optional<IQuestion> questionOptional = questions.stream()
                .filter(q -> q.matches(message))
                .findAny();
        if(questionOptional.isPresent()) {
            final IQuestion question = questionOptional.get();
            final Message response = new Message(() -> name, question.response(message));
            listeners.forEach(listener -> listener.onNewMessage(response));
        }
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

    public void registerQuestion(final IQuestion question) {
        questions.add(question);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatBot chatBot = (ChatBot) o;

        return name.equals(chatBot.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
