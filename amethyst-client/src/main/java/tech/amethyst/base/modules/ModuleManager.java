package tech.amethyst.base.modules;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import tech.amethyst.base.events.impl.input.EventKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tech.amethyst.utility.interfaces.IMinecraft;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
// Combat
import tech.amethyst.client.modules.impl.combat.AntiBot;
import tech.amethyst.client.modules.impl.combat.Aura;
import tech.amethyst.client.modules.impl.combat.AutoSwap;
import tech.amethyst.client.modules.impl.combat.AutoTotem;
import tech.amethyst.client.modules.impl.combat.ClickPearl;
import tech.amethyst.client.modules.impl.combat.TriggerBot;
import tech.amethyst.client.modules.impl.combat.WTap;
import tech.amethyst.client.modules.impl.combat.AimAssist;
import tech.amethyst.client.modules.impl.combat.ShieldBreaker;
import tech.amethyst.client.modules.impl.combat.AutoJumpReset;
// Crystal
import tech.amethyst.client.modules.impl.crystal.AutoCrystal;
import tech.amethyst.client.modules.impl.crystal.AutoHitCrystal;
// Misc
import tech.amethyst.client.modules.impl.misc.AHHelper;
import tech.amethyst.client.modules.impl.misc.AutoAccept;
import tech.amethyst.client.modules.impl.misc.AutoAuth;
import tech.amethyst.client.modules.impl.misc.AutoRespawn;
import tech.amethyst.client.modules.impl.misc.ClickAction;
import tech.amethyst.client.modules.impl.misc.ElytraHelper;
import tech.amethyst.client.modules.impl.misc.FreeCam;
import tech.amethyst.client.modules.impl.misc.ItemScroller;
import tech.amethyst.client.modules.impl.misc.NameProtect;
import tech.amethyst.client.modules.impl.misc.NoInteract;
import tech.amethyst.client.modules.impl.misc.ScoreboardHealth;
import tech.amethyst.client.modules.impl.misc.ServerHelper;
// Movement
import tech.amethyst.client.modules.impl.movement.AirStuck;
import tech.amethyst.client.modules.impl.movement.AutoSprint;
import tech.amethyst.client.modules.impl.movement.ElytraAccelerate;
import tech.amethyst.client.modules.impl.movement.ElytraBooster;
import tech.amethyst.client.modules.impl.movement.ElytraMotion;
import tech.amethyst.client.modules.impl.movement.ElytraRecast;
import tech.amethyst.client.modules.impl.movement.GuiWalk;
import tech.amethyst.client.modules.impl.movement.NoSlow;
import tech.amethyst.client.modules.impl.movement.NoWeb;
import tech.amethyst.client.modules.impl.movement.Speed;
// Player
import tech.amethyst.client.modules.impl.player.AutoArmor;
import tech.amethyst.client.modules.impl.player.AutoTool;
import tech.amethyst.client.modules.impl.player.Blink;
import tech.amethyst.client.modules.impl.player.FastBreak;
import tech.amethyst.client.modules.impl.player.NoDelay;
import tech.amethyst.client.modules.impl.player.NoPush;
// Render
import tech.amethyst.client.modules.impl.render.AntiInvisible;
import tech.amethyst.client.modules.impl.render.Crosshair;
import tech.amethyst.client.modules.impl.render.CustomFog;
import tech.amethyst.client.modules.impl.render.EntityESP;
import tech.amethyst.client.modules.impl.render.FullBright;
import tech.amethyst.client.modules.impl.render.Interface;
import tech.amethyst.client.modules.impl.render.Menu;
import tech.amethyst.client.modules.impl.render.NoRender;
import tech.amethyst.client.modules.impl.render.Predictions;
import tech.amethyst.client.modules.impl.render.SwingAnimation;
import tech.amethyst.client.modules.impl.render.TargetESP;
import tech.amethyst.client.modules.impl.render.ViewModel;
import tech.amethyst.client.modules.impl.render.WorldTime;
public final class ModuleManager implements IMinecraft {
   private final List<Module> modules = new ArrayList<>();
   private float acceleration;
   public ModuleManager() {
      this.registerCombat();
      this.registerMovement();
      this.registerPlayer();
      this.registerRender();
      this.registerMisc();
      this.registerCrystal();
      EventManager.register(this);
   }
   @EventTarget
   public void onUpdate(tech.amethyst.base.events.impl.player.EventUpdate event) {
      for (Module module : this.modules) {
         if (module.isEnabled()) {
            module.getAnimation().update(1.0F);
         } else {
            module.getAnimation().update(0.0F);
         }
      }
   }
   @EventTarget
   public void onKey(EventKey event) {
      if (event.getAction() == 1) { // 1 is PRESS
         for (Module module : this.modules) {
            if (module.getKeyCode() == event.getKeyCode()) {
               module.toggle();
            }
         }
      }
   }
   private void registerCombat() {
      this.registerModule(Aura.INSTANCE);
      this.registerModule(AntiBot.INSTANCE);
      this.registerModule(AutoSwap.INSTANCE);
      this.registerModule(AutoTotem.INSTANCE);
      this.registerModule(ClickPearl.INSTANCE);
      this.registerModule(TriggerBot.INSTANCE);
      this.registerModule(WTap.INSTANCE);
      this.registerModule(AimAssist.INSTANCE);
      this.registerModule(ShieldBreaker.INSTANCE);
      this.registerModule(AutoJumpReset.INSTANCE);
   }
   private void registerCrystal() {
      this.registerModule(AutoCrystal.INSTANCE);
      this.registerModule(AutoHitCrystal.INSTANCE);
   }
   private void registerMovement() {
      this.registerModule(AutoSprint.INSTANCE);
      this.registerModule(NoSlow.INSTANCE);
      this.registerModule(NoWeb.INSTANCE);
      this.registerModule(Speed.INSTANCE);
      this.registerModule(AirStuck.INSTANCE);
      this.registerModule(ElytraAccelerate.INSTANCE);
      this.registerModule(ElytraBooster.INSTANCE);
      this.registerModule(ElytraMotion.INSTANCE);
      this.registerModule(ElytraRecast.INSTANCE);
      this.registerModule(GuiWalk.INSTANCE);
   }
   private void registerPlayer() {
      this.registerModule(AutoArmor.INSTANCE);
      this.registerModule(AutoTool.INSTANCE);
      this.registerModule(Blink.INSTANCE);
      this.registerModule(FastBreak.INSTANCE);
      this.registerModule(NoDelay.INSTANCE);
      this.registerModule(NoPush.INSTANCE);
   }
   private void registerMisc() {
      this.registerModule(AHHelper.INSTANCE);
      this.registerModule(AutoAccept.INSTANCE);
      this.registerModule(AutoAuth.INSTANCE);
      this.registerModule(AutoRespawn.INSTANCE);
      this.registerModule(ClickAction.INSTANCE);
      this.registerModule(ElytraHelper.INSTANCE);
      this.registerModule(FreeCam.INSTANCE);
      this.registerModule(ItemScroller.INSTANCE);
      this.registerModule(NameProtect.INSTANCE);
      this.registerModule(NoInteract.INSTANCE);
      this.registerModule(ScoreboardHealth.INSTANCE);
      this.registerModule(ServerHelper.INSTANCE);
   }
   private void registerRender() {
      this.registerModule(AntiInvisible.INSTANCE);
      this.registerModule(Crosshair.INSTANCE);
      this.registerModule(CustomFog.INSTANCE);
      this.registerModule(EntityESP.INSTANCE);
      this.registerModule(FullBright.INSTANCE);
      this.registerModule(Interface.INSTANCE);
      this.registerModule(Menu.INSTANCE);
      this.registerModule(NoRender.INSTANCE);
      this.registerModule(Predictions.INSTANCE);
      this.registerModule(SwingAnimation.INSTANCE);
      this.registerModule(TargetESP.INSTANCE);
      this.registerModule(ViewModel.INSTANCE);
      this.registerModule(WorldTime.INSTANCE);
   }
   public void registerModule(Module module) {
      this.modules.add(module);
   }
   public List<Module> getModules() {
      return this.modules;
   }
   public List<Module> getModulesByCategory(Category category) {
      List<Module> modulesByCategory = new ArrayList<>();
      Iterator var3 = this.modules.iterator();
      while(var3.hasNext()) {
         Module module = (Module)var3.next();
         if (module.getCategory() == category) {
            modulesByCategory.add(module);
         }
      }
      return modulesByCategory;
   }
   public <T extends Module> T getModule(Class<T> clazz) {
      Iterator var2 = this.modules.iterator();
      Module module;
      do {
         if (!var2.hasNext()) {
            return null;
         }
         module = (Module)var2.next();
      } while(module.getClass() != clazz);
      return (T)module;
   }
   public <T extends Module> T getModule(String name) {
      Iterator var2 = this.modules.iterator();
      Module module;
      do {
         if (!var2.hasNext()) {
            return null;
         }
         module = (Module)var2.next();
      } while(!module.getName().equalsIgnoreCase(name));
      return (T)module;
   }
   public float getAcceleration() {
      return this.acceleration;
   }
   public void setAcceleration(float acceleration) {
      this.acceleration = acceleration;
   }
}



