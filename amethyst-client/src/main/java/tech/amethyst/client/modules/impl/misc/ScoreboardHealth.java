package tech.amethyst.client.modules.impl.misc;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "ScoreboardHealth",
   category = Category.MISC,
   description = "Fixes fake target health"
)
public class ScoreboardHealth extends Module {
   public static final ScoreboardHealth INSTANCE = new ScoreboardHealth();
}



