package agh.cs.lab;

import agh.cs.lab.channels.Channel;
import agh.cs.lab.channels.ChannelManager;
import agh.cs.lab.channels.ChannelMessageDispatcher;
import agh.cs.lab.channels.ChannelWebSocketHandler;
import agh.cs.lab.chat.ChatMessageDispatcher;
import agh.cs.lab.chat.ChatWebSocketHandler;
import agh.cs.lab.chat.UserSessionManager;
import agh.cs.lab.messages.MessageFormatter;
import agh.cs.lab.names.NameAlreadyTakenException;
import agh.cs.lab.users.chatbot.*;
import agh.cs.lab.utils.Downloader;
import agh.cs.lab.utils.WeatherService;

import static spark.Spark.*;


/**
 * Created by Paweł Grochola on 20.01.2017.
 */
public class Chat {
    public static void main(final String[] args) {
        final ChannelManager channelManager = new ChannelManager();
        final ChannelMessageDispatcher channelMessageDispatcher = new ChannelMessageDispatcher(channelManager);
        final UserSessionManager userSessionManager = new UserSessionManager(channelManager, new MessageFormatter());
        final ChatMessageDispatcher chatMessageDispatcher = new ChatMessageDispatcher(userSessionManager);
        final ChatWebSocketHandler chatWebSocketHandler = new ChatWebSocketHandler(chatMessageDispatcher);
        final ChannelWebSocketHandler channelWebSocketHandler = new ChannelWebSocketHandler(channelMessageDispatcher);
        try {
            final Channel chatbotChannel = new Channel("chatbot");
            final ChatBot chatBot = new ChatBot("ChatBot");
            chatBot.registerQuestion(new QuestionCurrentHour());
            chatBot.registerQuestion(new QuestionCurrentWeekDay());
            chatBot.registerQuestion(new QuestionWeather(new WeatherService(new Downloader())));
            chatBot.registerQuestion(new QuestionHelp());
            chatbotChannel.addUser(chatBot);
            channelManager.addChannel(chatbotChannel);

        } catch (NameAlreadyTakenException e) {
            //pass
        }
        staticFiles.location("/public"); //index.html is served at localhost:4567 (default port)
        staticFiles.expireTime(600);
        webSocket("/chat", chatWebSocketHandler);
        webSocket("/channels", channelWebSocketHandler);
        init();
    }

}