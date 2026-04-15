package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.base.comand.impl.args.FriendArgumentType;
import tech.amethyst.base.comand.impl.args.PlayerArgumentType;
import tech.amethyst.utility.game.other.MessageUtil;
public class StaffCommand extends CommandAbstract {
   public StaffCommand() {
      super("friend");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(literal("add").then(arg("player", PlayerArgumentType.create()).executes((context) -> {
         String name = (String)context.getArgument("player", String.class);
         if (Amethyst.getInstance().getStaffManager().getItems().contains(name)) {
            MessageUtil.displayMessage(MessageUtil.LogLevel.WARN, "Ð£Ð¶Ðµ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ " + name);
            return 1;
         } else {
            Amethyst.getInstance().getStaffManager().add(name);
            MessageUtil.displayMessage(MessageUtil.LogLevel.INFO, "Ð”Ð¾Ð±Ð°Ð²Ð¸Ð»Ð¸ " + name);
            return 1;
         }
      })));
      builder.then(literal("remove").then(arg("player", FriendArgumentType.create()).executes((context) -> {
         String nickname = (String)context.getArgument("player", String.class);
         Amethyst.getInstance().getStaffManager().remove(nickname);
         MessageUtil.displayMessage(MessageUtil.LogLevel.INFO, nickname + " ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¸Ð· ÑÑ‚Ð°Ñ„Ñ„Ð°");
         return 1;
      })));
      builder.then(literal("list").executes((commandContext) -> {
         MessageUtil.displayMessage(MessageUtil.LogLevel.INFO, Amethyst.getInstance().getStaffManager().getItems().toString());
         return 1;
      }));
   }
}



