package me.discord.api.data.serverinfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServerInformation {
    char prefix = '$';
    ChannelInfo verificationChannel;
    ChannelInfo roleChannel;
}
