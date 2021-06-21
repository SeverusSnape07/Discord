package me.discord.api.handlers.commandhandler;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public abstract class Command extends ListenerAdapter {
    @Getter
    private final CommandHandler commandHandler;

    public Command() {
        commandHandler = getClass().getDeclaredAnnotation(CommandHandler.class);
    }

    public void execute(GuildMessageReceivedEvent event, String[] args) { }
}
