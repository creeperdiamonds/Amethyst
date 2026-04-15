package tech.amethyst.base.discord.callbacks;
import com.sun.jna.Callback;
import tech.amethyst.base.discord.utils.DiscordUser;
public interface JoinRequestCallback extends Callback {
   void apply(DiscordUser var1);
}



