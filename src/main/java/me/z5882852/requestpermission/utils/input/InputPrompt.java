package me.z5882852.requestpermission.utils.input;

import me.z5882852.requestpermission.RequestPermission;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class InputPrompt{

    public static void startInputConversation(Player player, Consumer<String> callback) {
        ConversationFactory factory = new ConversationFactory(RequestPermission.thisPlugin)
                .withModality(true)
                .withFirstPrompt(new InputPrompts())
                .withLocalEcho(false) // 禁止在玩家聊天框中显示输入的字符
                .withEscapeSequence("cancel") // 可以设置取消输入的逃逸序列
                .addConversationAbandonedListener(event -> {
                    if (event.gracefulExit()) {
                        // 输入对话顺利结束，可以获取玩家输入的字符串
                        String input = (String) event.getContext().getSessionData("input");
                        callback.accept(input);
                    } else {
                        callback.accept(null);
                    }
                });

        Conversation conversation = factory.buildConversation(player);
        conversation.begin();
    }



    public static class InputPrompts extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return ChatColor.YELLOW + "请输入给予玩家该物品权限的指令,不要包含'/' (输入'cancel'退出): ";
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            // 存储玩家输入的字符串
            context.setSessionData("input", input);
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
