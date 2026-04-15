package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.io.File;
import java.io.IOException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.utility.game.other.MessageUtil;
public class ConfigCommand extends CommandAbstract {
   public ConfigCommand() {
      super("cfg");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(literal("save").then(arg("name", StringArgumentType.word()).executes((context) -> {
         String name = (String)context.getArgument("name", String.class);
         boolean success = Amethyst.getInstance().getConfigManager().saveConfig(name);
         if (success) {
            MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐšÐ¾Ð½Ñ„Ð¸Ð³ÑƒÑ€Ð°Ñ†Ð¸Ñ ÑÐ¾Ñ…Ñ€Ð°Ð½ÐµÐ½Ð°");
         } else {
            MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐžÑˆÐ¸Ð±ÐºÐ° Ð¿Ñ€Ð¸ ÑÐ¾Ñ…Ñ€Ð°Ð½ÐµÐ½Ð¸Ð¸ ÐºÐ¾Ð½Ñ„Ð¸Ð³ÑƒÑ€Ð°Ñ†Ð¸Ð¸");
         }
         return 1;
      })));
      builder.then(literal("load").then(arg("name", StringArgumentType.word()).executes((context) -> {
         String name = (String)context.getArgument("name", String.class);
         boolean success = Amethyst.getInstance().getConfigManager().loadConfig(name);
         if (success) {
            MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐšÐ¾Ð½Ñ„Ð¸Ð³ÑƒÑ€Ð°Ñ†Ð¸Ñ Ð·Ð°Ð³Ñ€ÑƒÐ¶ÐµÐ½Ð°");
         } else {
            MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐžÑˆÐ¸Ð±ÐºÐ° Ð¿Ñ€Ð¸ Ð·Ð°Ð³Ñ€ÑƒÐ·ÐºÐµ ÐºÐ¾Ð½Ñ„Ð¸Ð³ÑƒÑ€Ð°Ñ†Ð¸Ð¸");
         }
         return 1;
      })));
      builder.then(literal("dir").executes((context) -> {
         try {
            File dir = new File("javelin/configs/");
            if (!dir.exists()) {
               MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "Ð¢Ñ‹ Ð½Ð°Ñ…ÑƒÑ Ð¿Ð°Ð¿ÐºÑƒ ÑƒÐ´Ð°Ð»Ð¸Ð» Ñ„Ñ€Ð¸Ðº");
               dir.mkdirs();
            } else {
               MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐžÑ‚ÐºÑ€Ñ‹Ð²Ð°ÑŽ Ð¿Ð°Ð¿ÐºÑƒ Ñ ÐºÐ¾Ð½Ñ„Ð¸Ð³Ð°Ð¼Ð¸...");
            }
            Runtime.getRuntime().exec("explorer " + dir.getAbsolutePath());
         } catch (IOException var2) {
            String var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "ÐžÑˆÐ¸Ð±ÐºÐ° Ð¿Ñ€Ð¸ Ð¾Ñ‚ÐºÑ€Ñ‹Ñ‚Ð¸Ð¸ Ð¿Ð°Ð¿ÐºÐ¸: " + String.valueOf(Formatting.WHITE) + var2.getMessage());
         }
         return 1;
      }));
   }
}



