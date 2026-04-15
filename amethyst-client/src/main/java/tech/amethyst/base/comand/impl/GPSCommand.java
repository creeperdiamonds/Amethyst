package tech.amethyst.base.comand.impl;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.comand.api.CommandAbstract;
import tech.amethyst.base.waypoint.Waypoint;
import tech.amethyst.utility.game.other.MessageUtil;
public class GPSCommand extends CommandAbstract {
   public GPSCommand() {
      super("gps");
   }
   @Native
   public void execute(LiteralArgumentBuilder<CommandSource> builder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(arg("X", IntegerArgumentType.integer()).then(arg("Z", IntegerArgumentType.integer()).executes((context) -> {
         int x = (Integer)context.getArgument("X", Integer.class);
         int z = (Integer)context.getArgument("Z", Integer.class);
         Waypoint waypoint = new Waypoint((double)x, (double)z);
         Amethyst.getInstance().getWaypointManager().set(waypoint);
         MessageUtil.displayInfo("GPS ÑÐ¾Ð·Ð´Ð°Ð½ Ð¸ ÑƒÐºÐ°Ð·Ñ‹Ð²Ð°ÐµÑ‚ Ð½Ð° XZ: %s, %s".formatted(new Object[]{x, z}));
         return 1;
      })))).then(literal("player").then(arg("name", StringArgumentType.word()).then(arg("X", IntegerArgumentType.integer()).then(arg("Z", IntegerArgumentType.integer()).executes((context) -> {
         String name = (String)context.getArgument("name", String.class);
         int x = (Integer)context.getArgument("X", Integer.class);
         int z = (Integer)context.getArgument("Z", Integer.class);
         Waypoint waypoint = new Waypoint(name, (double)x, (double)z);
         Amethyst.getInstance().getWaypointManager().setPlayerWaypoint(waypoint);
         MessageUtil.displayInfo("GPS ÑÐ¾Ð·Ð´Ð°Ð½ Ð¸ ÑƒÐºÐ°Ð·Ñ‹Ð²Ð°ÐµÑ‚ Ð½Ð° XZ: %s, %s".formatted(new Object[]{x, z}));
         return 1;
      })))))).then(literal("player").then(literal("remove").executes((context) -> {
         if (!Amethyst.getInstance().getWaypointManager().isEmptyPlayerWaypoint()) {
            Amethyst.getInstance().getWaypointManager().clearPlayerWaypoint();
            MessageUtil.displayInfo("GPS ÑƒÐ´Ð°Ð»ÐµÐ½");
         } else {
            MessageUtil.displayInfo("ÐÐµÑ‚ Ð°ÐºÑ‚Ð¸Ð²Ð½Ð¾Ð³Ð¾ GPS");
         }
         return 1;
      })))).then(literal("remove").executes((context) -> {
         if (!Amethyst.getInstance().getWaypointManager().isEmpty()) {
            Amethyst.getInstance().getWaypointManager().clear();
            MessageUtil.displayInfo("GPS Ð½Ð° Ñ‚Ð¾Ñ‡ÐºÑƒ Ð¸Ð³Ñ€Ð¾ÐºÐ° ÑƒÐ´Ð°Ð»ÐµÐ½");
         } else {
            MessageUtil.displayInfo("ÐÐµÑ‚ Ð°ÐºÑ‚Ð¸Ð²Ð½Ð¾Ð³Ð¾ Ð½Ð° Ð¸Ð³Ñ€Ð¾ÐºÐ° GPS");
         }
         return 1;
      }));
   }
}



