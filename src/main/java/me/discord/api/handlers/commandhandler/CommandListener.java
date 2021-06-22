package me.discord.api.handlers.commandhandler;

import me.discord.api.Discord;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (event.getMessage().getContentRaw().startsWith(String.valueOf(Discord.getServerInfoManager().getServerInformation().getPrefix()))) {
            String[] arguments = event.getMessage().getContentRaw().split(" ");

            Command command = Discord.getCommand(arguments[0].replace(String.valueOf(Discord.getServerInfoManager().getServerInformation().getPrefix()), ""));

            if (command == null) {
                return;
            }

            if (command.getCommandHandler().channelName().length > 0 || command.getCommandHandler().channelId().length > 0) {
                if (containsChannelByName(event.getChannel().getName(), command)) {
                    command.execute(event, arguments);
                } else if (containsChannelById(event.getChannel().getId(), command)) {
                    command.execute(event, arguments);
                }
            }else if (command.getCommandHandler().channelName().length < 1 || command.getCommandHandler().channelId().length < 1) {
                command.execute(event, arguments);
            }
        }
    }

    private boolean containsChannelByName(String string, Command command) {
        for (String str : command.getCommandHandler().channelName()) {
            if (str.equals(string)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsChannelById(String string, Command command) {
        for (String str : command.getCommandHandler().channelId()) {
            if (str.equals(string)) {
                return true;
            }
        }
        return false;
    }
}
