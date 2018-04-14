package matrixdeity.rosello.core;

import static matrixdeity.rosello.util.Consts.*;

import matrixdeity.rosello.event.TimerEvent;
import matrixdeity.rosello.util.Timer;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class RoselloBot {

    private static RoselloBot instance = new RoselloBot();

    public static RoselloBot getInstance() {
        return instance;
    }

    private IDiscordClient client;
    private Interlocuter interlocuter;
    private Timer timer;

    private RoselloBot() {
        try {
            ClientBuilder builder = new ClientBuilder();
            builder.withToken(TOKEN);
            client = builder.login();
            client.getDispatcher().registerListener(this);
            interlocuter = new Interlocuter();
            timer = new Timer(SLEEP_TIME, () -> client.getDispatcher().dispatch(new TimerEvent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        if (message.getAuthor().isBot())
            return;
        if (throwDice(ANSWER_CHANCE)) {
            answerToMessage(message);
        }
    }

    @EventSubscriber
    public void onTimerEvent(TimerEvent event) {
        List<IChannel> channels = event.getClient().getChannels();
        for (IChannel channel : channels) {
            if (throwDice(GIG_CHANCE)) {
                speakOut(channel);
            }
        }
    }

    @EventSubscriber
    public void onUserJoinEvent(UserJoinEvent event) {
        IChannel channel = event.getGuild().getDefaultChannel();
        IUser user = event.getUser();
        String welcome = interlocuter.generateWelcome(user.mention());
        channel.sendMessage(welcome);
    }

    @EventSubscriber
    public void onShardReadyEvent(ShardReadyEvent event) {
        List<IChannel> channels = event.getClient().getChannels();
        for (IChannel channel : channels) {
            String greeting = interlocuter.generateGreetings();
            channel.sendMessage(greeting);
        }
    }

    public String getClientID() {
        return client.getApplicationClientID();
    }

    private void answerToMessage(IMessage message) {
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String content = message.getContent().toLowerCase();
        String answer = interlocuter.generateAnswer(user.mention(), content);
        channel.sendMessage(answer);
    }

    private void speakOut(IChannel channel) {
        List<IUser> users = channel.getGuild().getUsers();
        IUser user;
        do {
            user = users.get(GENERATOR.nextInt(users.size()));
        } while (user.isBot());
        String gag = interlocuter.generateGag(user.mention());
        channel.sendMessage(gag);
    }

    private boolean throwDice(double chance) {
        return Math.random() < chance;
    }

}
