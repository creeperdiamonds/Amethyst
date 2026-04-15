package tech.amethyst.client.modules.impl.render;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
@ModuleAnnotation(
   name = "FullBright",
   category = Category.RENDER,
   description = "Maximum brightness"
)
public class FullBright extends Module {
   public static final FullBright INSTANCE = new FullBright();
}



