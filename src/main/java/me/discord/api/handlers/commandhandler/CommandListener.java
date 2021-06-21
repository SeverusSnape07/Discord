package me.discord.api.handlers.commandhandler;

import me.discord.api.Discord;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (!event.getMessage().getContentRaw().startsWith("$")) {
            event.getMessage().delete().queue();
        }

        String[] arguments = event.getMessage().getContentRaw().split(" ");

        Command command = Discord.get(arguments[0].replace("$", ""));

        if (command == null) {
            return;
        }

        if (!command.getCommandHandler().channel().isEmpty()) {
            if (event.getChannel().getName().equals(command.getCommandHandler().channel())) {
                command.execute(event, arguments);
            }
        }
        command.execute(event, arguments);
    }
}
