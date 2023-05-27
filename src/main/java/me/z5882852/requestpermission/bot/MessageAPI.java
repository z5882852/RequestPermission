package me.z5882852.requestpermission.bot;

import me.albert.amazingbot.bot.Bot;
import me.z5882852.requestpermission.RequestPermission;

import java.util.List;

public class MessageAPI {
    public MessageAPI() {

    }
    public static void send(String message) {
        List<Long> groupIds = RequestPermission.thisPlugin.getConfig().getLongList("groups");
        for (Long groupId : groupIds) {
            Bot.getApi().sendGroupMsg(groupId, message, true);
        }
    }
}
