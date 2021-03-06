package me.discord.api;

import lombok.Getter;
import lombok.Setter;
import me.discord.api.data.serverinfo.ServerInfoManager;
import me.discord.api.handlers.commandhandler.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Discord {
    @Getter @Setter
    private static JDA jda = null;
    @Getter
    private static final List<Command> commands = new ArrayList<>();
    private static final File file = new File("server-info");
    @Getter @Setter
    private static ServerInfoManager serverInfoManager = new ServerInfoManager(file);

    protected static void registerEvents(ListenerAdapter... listenerAdapter) {
        Arrays.stream(listenerAdapter).forEach(jda::addEventListener);
    }

    public static Command getCommand(String name) {
        return commands
                .stream()
                .filter(cmd -> cmd.getCommandHandler().name().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static void addCommand(Command... command) {
        commands.addAll(Arrays.asList(command));
    }


}
