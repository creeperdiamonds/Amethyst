package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.base.notify.NotifyManager;
import tech.amethyst.base.repository.RCTRepository;
import tech.amethyst.utility.game.server.ServerHandler;
import tech.amethyst.utility.interfaces.IClient;
public class RCTCommand extends CommandAbstract implements IClient {
   private final RCTRepository repository = Amethyst.getInstance().getRCTRepository();
   public RCTCommand() {
      super("rct");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      builder.executes((context) -> {
         ServerHandler serverHandler = Amethyst.getInstance().getServerHandler();
         if (!serverHandler.isHolyWorld()) {
            NotifyManager.getInstance().addNotification("[RCT]", Text.literal(" ÐÐµ Ñ€Ð°Ð±Ð¾Ñ‚Ð°ÐµÑ‚ Ð½Ð° ÑÑ‚Ð¾Ð¼ " + String.valueOf(Formatting.RED) + "ÑÐµÑ€Ð²ÐµÑ€Ðµ"));
            return 1;
         } else if (serverHandler.isPvp()) {
            NotifyManager.getInstance().addNotification("ï¸[RCT]", Text.literal(" Ð’Ñ‹ Ð½Ð°Ñ…Ð¾Ð´Ð¸Ñ‚ÐµÑÑŒ Ð² Ñ€ÐµÐ¶Ð¸Ð¼Ðµ " + String.valueOf(Formatting.RED) + "Ð¿Ð²Ð¿"));
            return 1;
         } else {
            this.repository.reconnect(serverHandler.getAnarchy());
            return 1;
         }
      });
      builder.then(CommandAbstract.arg("anarchy", IntegerArgumentType.integer(1, 63)).executes((context) -> {
         ServerHandler serverHandler = Amethyst.getInstance().getServerHandler();
         if (!serverHandler.isHolyWorld()) {
            NotifyManager.getInstance().addNotification("[RCT]", Text.literal(" ÐÐµ Ñ€Ð°Ð±Ð¾Ñ‚Ð°ÐµÑ‚ Ð½Ð° ÑÑ‚Ð¾Ð¼ " + String.valueOf(Formatting.RED) + "ÑÐµÑ€Ð²ÐµÑ€Ðµ"));
            return 1;
         } else if (serverHandler.isPvp()) {
            NotifyManager.getInstance().addNotification("[RCT]ï¸", Text.literal(" Ð’Ñ‹ Ð½Ð°Ñ…Ð¾Ð´Ð¸Ñ‚ÐµÑÑŒ Ð² Ñ€ÐµÐ¶Ð¸Ð¼Ðµ " + String.valueOf(Formatting.RED) + "Ð¿Ð²Ð¿"));
            return 1;
         } else {
            int anarchy = (Integer)context.getArgument("anarchy", Integer.class);
            this.repository.reconnect(anarchy);
            return 1;
         }
      }));
   }
}



