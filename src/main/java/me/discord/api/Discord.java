package me.discord.api;

import lombok.Getter;
import lombok.Setter;
import me.discord.api.handlers.commandhandler.Command;
import me.discord.api.handlers.commandhandler.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Discord {
    @Getter @Setter
    private static String TOKEN;
    @Getter
    private static JDA jda = null;
    @Getter
    private static final List<Class<? extends Command>> commands = new ArrayList<>();

    static {
        try {
            jda = JDABuilder.createDefault(TOKEN).build();
            registerEvents(new CommandListener());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    protected static void registerEvents(ListenerAdapter listenerAdapter) {
        jda.addEventListener(listenerAdapter);
    }

    public static Class<? extends Command> get(String name) {
        return commands
                .stream()
                .filter(cmd -> {
                    try {
                        return cmd.getDeclaredConstructor().newInstance().getCommandHandler().name().equals(name);
                    }catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    @SafeVarargs
    public static void addCommand(Class<? extends Command>... command) {
        commands.addAll(Arrays.asList(command));
    }

}
