package me.discord.api.data.serverinfo;

import lombok.Getter;
import lombok.Setter;
import me.godlycoder.json.JsonFile;

import java.io.*;

public class ServerInfoManager extends JsonFile {
    private final File file;
    @Setter @Getter
    private ServerInformation serverInformation;

    public ServerInfoManager(File file) {
        this.file = file;
    }

    @Override
    public void create() {
        serverInformation = new ServerInformation();
        save();
    }

    @Override
    public void load() {
        try {
            FileReader reader = new FileReader(file);
            gson.fromJson(reader, ServerInformation.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            gson.toJson(serverInformation, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrLoad() {
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            boolean b = file.getParentFile().mkdir();
        }

        if (file.exists()) {
            create();
        }

        load();
    }
}
