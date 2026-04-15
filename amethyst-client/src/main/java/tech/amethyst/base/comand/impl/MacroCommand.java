package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Iterator;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.base.comand.impl.args.CommandArgumentType;
import tech.amethyst.base.comand.impl.args.MacroArgumentType;
import tech.amethyst.base.comand.impl.args.MacroRemoveArgumentType;
import tech.amethyst.base.macro.Macro;
import tech.amethyst.utility.game.other.MessageUtil;
import tech.amethyst.utility.render.display.Keyboard;
public class MacroCommand extends CommandAbstract {
   public MacroCommand() {
      super("macro");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      builder.then(literal("add").then(arg("bind", MacroArgumentType.create()).then(arg("text", CommandArgumentType.create()).executes((context) -> {
         String bind = (String)context.getArgument("bind", String.class);
         String text = (String)context.getArgument("text", String.class);
         String var10000;
         if (Keyboard.getKeyCode(bind) != -1) {
            var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "Ð”Ð»Ñ ÐºÐ»Ð°Ð²Ð¸ÑˆÐ¸ " + String.valueOf(Formatting.WHITE) + bind.toUpperCase() + String.valueOf(Formatting.GRAY) + " Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½ Ð¼Ð°ÐºÑ€Ð¾Ñ Ñ Ñ‚ÐµÐºÑÑ‚Ð¾Ð¼ " + String.valueOf(Formatting.WHITE) + text);
            Amethyst.getInstance().getMacroManager().add(new Macro(Keyboard.getKeyCode(bind), text));
         } else {
            var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "ÐšÐ»Ð°Ð²Ð¸ÑˆÐ° " + String.valueOf(Formatting.WHITE) + bind.toUpperCase() + String.valueOf(Formatting.GRAY) + " Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð°");
         }
         return 1;
      }))));
      builder.then(literal("remove").then(arg("bind", MacroRemoveArgumentType.create()).executes((context) -> {
         String bind = (String)context.getArgument("bind", String.class);
         String var10000;
         if (Amethyst.getInstance().getMacroManager().getItems().stream().anyMatch((macro) -> {
            return macro.getBind() == Keyboard.getKeyCode(bind);
         })) {
            if (Keyboard.getKeyCode(bind) != -1) {
               var10000 = String.valueOf(Formatting.GRAY);
               MessageUtil.displayInfo(var10000 + "Ð¡ ÐºÐ»Ð°Ð²Ð¸ÑˆÐ¸ " + String.valueOf(Formatting.WHITE) + bind.toUpperCase() + String.valueOf(Formatting.GRAY) + " ÑƒÐ´Ð°Ð»ÐµÐ½ Ð¼Ð°ÐºÑ€Ð¾Ñ");
               Amethyst.getInstance().getMacroManager().getItems().stream().filter((macro) -> {
                  return macro.getBind() == Keyboard.getKeyCode(bind);
               }).forEach((macro) -> {
                  Amethyst.getInstance().getMacroManager().removeMacro(macro);
               });
            } else {
               var10000 = String.valueOf(Formatting.GRAY);
               MessageUtil.displayInfo(var10000 + "ÐšÐ»Ð°Ð²Ð¸ÑˆÐ° " + String.valueOf(Formatting.WHITE) + bind.toUpperCase() + String.valueOf(Formatting.GRAY) + " Ð½Ðµ Ð½Ð°Ð¹Ð´ÐµÐ½Ð°");
            }
         } else {
            var10000 = String.valueOf(Formatting.GRAY);
            MessageUtil.displayInfo(var10000 + "ÐœÐ°ÐºÑ€Ð¾ÑÐ° Ð¿Ñ€Ð¸Ð²ÑÐ·Ð°Ð½Ð½Ð¾Ð³Ð¾ Ðº ÐºÐ»Ð°Ð²Ð¸ÑˆÐµ " + String.valueOf(Formatting.WHITE) + bind + String.valueOf(Formatting.GRAY) + " Ð½Ðµ ÑÑƒÑ‰ÐµÑÑ‚Ð²ÑƒÐµÑ‚");
         }
         return 1;
      })));
      builder.then(literal("list").executes((commandContext) -> {
         StringBuilder stringBuilder = new StringBuilder();
         Iterator var2 = Amethyst.getInstance().getMacroManager().getItems().iterator();
         while(var2.hasNext()) {
            Macro macro = (Macro)var2.next();
            String var10001 = String.valueOf(Formatting.GRAY);
            stringBuilder.append("\n" + var10001 + macro.getText()).append(String.valueOf(Formatting.WHITE) + " [").append(Keyboard.getKeyName(macro.getBind())).append("]");
         }
         if (stringBuilder.isEmpty()) {
            MessageUtil.displayInfo(String.valueOf(Formatting.GRAY) + "ÐÐµÐ¼Ð°");
         } else {
            MessageUtil.displayInfo(stringBuilder);
         }
         return 1;
      }));
   }
}



