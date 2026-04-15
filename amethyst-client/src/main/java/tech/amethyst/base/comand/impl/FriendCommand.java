package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.base.comand.impl.args.FriendArgumentType;
import tech.amethyst.base.comand.impl.args.PlayerArgumentType;
import tech.amethyst.utility.game.other.MessageUtil;
public class FriendCommand extends CommandAbstract {
   public FriendCommand() {
      super("friend");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(literal("add").then(arg("player", PlayerArgumentType.create()).executes((context) -> {
         String name = (String)context.getArgument("player", String.class);
         String var10000;
         if (Amethyst.getInstance().getFriendManager().getItems().contains(name)) {
            var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "Ð˜Ð³Ñ€Ð¾Ðº Ñ Ð½Ð¸ÐºÐ¾Ð¼ " + String.valueOf(Formatting.WHITE) + name + String.valueOf(Formatting.GRAY) + " ÑƒÐ¶Ðµ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð² ÑÐ¿Ð¸ÑÐ¾Ðº Ð´Ñ€ÑƒÐ·ÐµÐ¹");
            return 1;
         } else {
            Amethyst.getInstance().getFriendManager().add(name);
            var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "Ð˜Ð³Ñ€Ð¾Ðº Ñ Ð½Ð¸ÐºÐ¾Ð¼ " + String.valueOf(Formatting.WHITE) + name + String.valueOf(Formatting.GRAY) + " Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð² ÑÐ¿Ð¸ÑÐ¾Ðº Ð´Ñ€ÑƒÐ·ÐµÐ¹");
            return 1;
         }
      })));
      builder.then(literal("remove").then(arg("player", FriendArgumentType.create()).executes((context) -> {
         String nickname = (String)context.getArgument("player", String.class);
         Amethyst.getInstance().getFriendManager().removeFriend(nickname);
         String var10000 = String.valueOf(Formatting.GRAY);
         MessageUtil.displayInfo(var10000 + "Ð˜Ð³Ñ€Ð¾Ðº Ñ Ð½Ð¸ÐºÐ¾Ð¼ " + String.valueOf(Formatting.WHITE) + nickname + String.valueOf(Formatting.GRAY) + " ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¸Ð· ÑÐ¿Ð¸ÑÐºÐ° Ð´Ñ€ÑƒÐ·ÐµÐ¹");
         return 1;
      })));
      builder.then(literal("list").executes((commandContext) -> {
         MessageUtil.displayInfo(Amethyst.getInstance().getFriendManager().getItems().toString());
         return 1;
      }));
   }
}



