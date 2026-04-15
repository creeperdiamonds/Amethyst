package tech.amethyst.base.discord.callbacks;
import com.sun.jna.Callback;
import tech.amethyst.base.discord.utils.DiscordUser;
public interface ReadyCallback extends Callback {
   void apply(DiscordUser var1);
}



