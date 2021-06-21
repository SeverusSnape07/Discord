package me.discord.api.handlers.commandhandler;

import me.discord.api.Discord;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.InvocationTargetException;

public class CommandListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (!event.getMessage().getContentRaw().startsWith("$")) {
            event.getMessage().delete().queue();
        }

        String[] arguments = event.getMessage().getContentRaw().split(" ");

        Class<? extends Command> command = Discord.get(arguments[0].replace("$", ""));

        if (command == null) {
            return;
        }

        try {
            if (!command.getDeclaredConstructor().newInstance().getCommandHandler().channel().isEmpty()) {
                if (event.getChannel().getName().equals(command.getDeclaredConstructor().newInstance().getCommandHandler().channel())) {
                    command.getDeclaredConstructor().newInstance().execute(event, arguments);
                }
            }
            command.getDeclaredConstructor().newInstance().execute(event, arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
