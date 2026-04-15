package tech.amethyst.client.modules.impl.combat;
import com.darkmagician6.eventapi.EventTarget;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.nexusguard.protection.annotations.Native;
import tech.amethyst.Amethyst;
import tech.amethyst.base.events.impl.other.EventGameUpdate;
import tech.amethyst.base.events.impl.other.EventTick;
import tech.amethyst.base.events.impl.other.EventTickMovement;
import tech.amethyst.base.events.impl.player.EventMoveInput;
import tech.amethyst.base.events.impl.player.EventUpdate;
import tech.amethyst.base.player.AttackUtil;
import tech.amethyst.base.request.ScriptManager;
import tech.amethyst.client.modules.api.Category;
import tech.amethyst.client.modules.api.Module;
import tech.amethyst.client.modules.api.ModuleAnnotation;
import tech.amethyst.client.modules.api.setting.impl.BooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.ModeSetting;
import tech.amethyst.client.modules.api.setting.impl.MultiBooleanSetting;
import tech.amethyst.client.modules.api.setting.impl.NumberSetting;
import tech.amethyst.client.modules.impl.movement.AutoSprint;
import tech.amethyst.utility.component.RotationComponent;
import tech.amethyst.utility.game.player.MovingUtil;
import tech.amethyst.utility.game.player.PlayerInventoryUtil;
import tech.amethyst.utility.game.player.RaytracingUtil;
import tech.amethyst.utility.game.player.rotation.Rotation;
import tech.amethyst.utility.game.player.rotation.RotationUtil;
import tech.amethyst.utility.math.MultipointUtils;
import tech.amethyst.utility.math.Timer;
import tech.amethyst.utility.predict.PredictUtils;
@ModuleAnnotation(
        name = "Aura",
        category = Category.COMBAT,
        description = "ÐÐ²Ñ‚Ð¾Ð¼Ð°Ñ‚Ð¸Ñ‡ÐµÑÐºÐ¸ Ð±ÑŒÐµÑ‚ Ñ†ÐµÐ»ÑŒ"
)
public final class Aura extends Module {
   public static final Aura INSTANCE = new Aura();
   private final MultiBooleanSetting targetTypeSetting = MultiBooleanSetting.create("ÐÑ‚Ð°ÐºÐ¾Ð²Ð°Ñ‚ÑŒ", List.of("Ð˜Ð³Ñ€Ð¾ÐºÐ¾Ð²", "ÐœÐ¾Ð±Ð¾Ð²", "Ð–Ð¸Ð²Ð¾Ñ‚Ð½Ñ‹Ñ…"));
   public final ModeSetting rotationMode = new ModeSetting("Ð Ð¾Ñ‚Ð°Ñ†Ð¸Ñ", new String[0]);
   private final ModeSetting.Value hvh;
   private final ModeSetting.Value lonyJir;
   private final ModeSetting.Value cake;
   private final ModeSetting.Value legendsGrief;
   private final ModeSetting correction;
   private final ModeSetting.Value correctionFocus;
   private final ModeSetting.Value correctionGood;
   private final ModeSetting.Value correctionNone;
   private final NumberSetting distance;
   private final NumberSetting distanceRotation;
   private final BooleanSetting shieldBreak;
   private final BooleanSetting legitSwap;
   private final BooleanSetting raycastCheck;
   private final BooleanSetting predictOnElytra;
   public final NumberSetting predict;
   public final BooleanSetting critsOnlyWithSpace;
   private LivingEntity target;
   private float acceleration;
   private boolean isBack;
   private final Timer hurtTimer;
   private final ScriptManager.ScriptTask script;
   private int lastSlot;
   public float lastYaw;
   public float lastPitch;
   private Aura() {
      this.hvh = new ModeSetting.Value(this.rotationMode, "Vanilla");
      this.lonyJir = (new ModeSetting.Value(this.rotationMode, "LonyGrief")).select();
      this.cake = (new ModeSetting.Value(this.rotationMode, "CakeWorld")).select();
      this.legendsGrief = (new ModeSetting.Value(this.rotationMode, "LegendsGrief")).select();
      this.correction = new ModeSetting("ÐšÐ¾Ñ€Ñ€ÐµÐºÑ†Ð¸Ñ", new String[0]);
      this.correctionFocus = new ModeSetting.Value(this.correction, "Ð¤Ð¾ÐºÑƒÑ");
      this.correctionGood = (new ModeSetting.Value(this.correction, "Ð¡Ð²Ð¾Ð±Ð¾Ð´Ð½Ð°Ñ")).select();
      this.correctionNone = new ModeSetting.Value(this.correction, "ÐÐµÑ‚");
      this.distance = new NumberSetting("Ð”Ð¸ÑÑ‚Ð°Ð½Ñ†Ð¸Ñ", 3.0F, 0.5F, 6.0F, 0.1F, "Ð”Ð¸ÑÑ‚Ð°Ð½Ñ†Ð¸Ñ Ð°Ñ‚Ð°ÐºÐ¸");
      this.distanceRotation = new NumberSetting("Ð”Ð¸ÑÑ‚Ð°Ð½Ñ†Ð¸Ñ Ð°Ð¸Ð¼Ð°", 0.1F, 0.0F, 6.0F, 0.1F);
      this.shieldBreak = new BooleanSetting("Ð›Ð¾Ð¼Ð°Ñ‚ÑŒ Ñ‰Ð¸Ñ‚", true);
      BooleanSetting var10005 = this.shieldBreak;
      Objects.requireNonNull(var10005);
      this.legitSwap = new BooleanSetting("Ð›ÐµÐ³Ð¸Ñ‚Ð½Ð¾ Ð»Ð¾Ð¼Ð°Ñ‚ÑŒ", true, var10005::isEnabled);
      this.raycastCheck = new BooleanSetting("ÐŸÑ€Ð¾Ð²ÐµÑ€ÐºÐ° Ð½Ð° Ð½Ð°Ð²ÐµÐ´ÐµÐ½Ð¸Ðµ", false);
      this.predictOnElytra = new BooleanSetting("ÐŸÐµÑ€ÐµÐ³Ð¾Ð½ÑÑ‚ÑŒ Ð¿Ñ€Ð¾Ñ‚Ð¸Ð²Ð½Ð¸ÐºÐ°", true);
      this.predict = new NumberSetting("ÐÐ°ÑÐºÐ¾Ð»ÑŒÐºÐ¾ Ð¿ÐµÑ€ÐµÐ³Ð¾Ð½ÑÑ‚ÑŒ", 2.0F, 1.0F, 4.0F, 0.1F);
      this.critsOnlyWithSpace = new BooleanSetting("Ð¢Ð¾Ð»ÑŒÐºÐ¾ Ñ Ð¿Ñ€Ð¾Ð±ÐµÐ»Ð¾Ð¼", true);
      this.target = null;
      this.hurtTimer = new Timer();
      this.script = new ScriptManager.ScriptTask();
      this.lastSlot = -1;
   }
   @Native
   private void breakShieldAndAttack() {
      boolean wasSwapped = false;
      boolean wasSwappedInventory = false;
      int slotHotbar = PlayerInventoryUtil.find((List)List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE), 0, 8);
      int slotInventory = PlayerInventoryUtil.find((List)List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE), 8, 35);
      if (slotHotbar != -1 && this.shieldBreak.isEnabled() && this.target.isBlocking()) {
         if (this.legitSwap.isEnabled()) {
            this.lastSlot = mc.player.getInventory().selectedSlot;
            mc.player.getInventory().selectedSlot = slotHotbar;
         } else {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slotHotbar));
         }
         wasSwapped = true;
      }
      if (slotHotbar == -1 && slotInventory != -1 && this.shieldBreak.isEnabled() && this.target.isBlocking()) {
         if (this.legitSwap.isEnabled()) {
            mc.interactionManager.clickSlot(0, slotInventory, 8, SlotActionType.SWAP, mc.player);
            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            this.lastSlot = mc.player.getInventory().selectedSlot;
            mc.player.getInventory().selectedSlot = 8;
         } else {
            mc.interactionManager.clickSlot(0, slotInventory, 8, SlotActionType.SWAP, mc.player);
            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(slotHotbar));
         }
         wasSwappedInventory = true;
      }
      mc.interactionManager.attackEntity(mc.player, this.target);
      mc.player.swingHand(Hand.MAIN_HAND);
      if (wasSwapped) {
         if (this.legitSwap.isEnabled()) {
            Amethyst.getInstance().getScriptManager().addTask(this.script);
            this.script.schedule(EventUpdate.class, (eventUpdate) -> {
               mc.player.getInventory().selectedSlot = this.lastSlot;
               this.lastSlot = -1;
               return true;
            });
         } else {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
         }
      }
      if (wasSwappedInventory) {
         if (this.legitSwap.isEnabled()) {
            Amethyst.getInstance().getScriptManager().addTask(this.script);
            this.script.schedule(EventUpdate.class, (eventUpdate) -> {
               mc.player.getInventory().selectedSlot = this.lastSlot;
               mc.interactionManager.clickSlot(0, slotInventory, 8, SlotActionType.SWAP, mc.player);
               mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               this.lastSlot = -1;
               return true;
            });
         } else {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(mc.player.getInventory().selectedSlot));
            mc.interactionManager.clickSlot(0, slotInventory, 8, SlotActionType.SWAP, mc.player);
            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
         }
      }
   }
   @EventTarget
   public void onTick(EventTick e) {
      if (this.target == null || !this.isValid(this.target)) {
         this.target = this.updateTarget();
      }
      if (this.target != null) {
         if (this.isCanAttack() && this.hurtTimer.finished(458L) && !this.target.isBlocking()) {
            if (mc.player.isSprinting() && !mc.player.isOnGround() && !mc.player.isSwimming()) {
               mc.player.setSprinting(false);
               mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
               if (!AutoSprint.INSTANCE.isEnabled()) {
                  mc.options.sprintKey.setPressed(false);
               }
            }
            mc.interactionManager.attackEntity(mc.player, this.target);
            mc.player.swingHand(Hand.MAIN_HAND);
            this.hurtTimer.reset();
         }
      }
   }
   @EventTarget
   @Native
   public void onTickMovement(EventTickMovement e) {
      if (this.target != null) {
         if (this.target.isBlocking() && this.hurtTimer.finished(50L)) {
            if (mc.player.isSprinting() && !mc.player.isOnGround() && !mc.player.isSwimming()) {
               mc.player.setSprinting(false);
               mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
               if (!AutoSprint.INSTANCE.isEnabled()) {
                  mc.options.sprintKey.setPressed(false);
               }
            }
            this.breakShieldAndAttack();
            this.hurtTimer.reset();
         }
      }
   }
   @EventTarget
   @Native
   public void eventRotate(EventGameUpdate e) {
      if (this.target != null) {
         Amethyst.getInstance().getModuleManager().setAcceleration(0.0F);
         Box box = this.target.getBoundingBox();
         Vec3d eyes = mc.player.getEyePos();
         Vec3d point = this.hvh.isSelected() ? this.target.getBoundingBox().getCenter() : (this.legendsGrief.isSelected() ? MultipointUtils.getNearestPoint(this.target, (double)this.distance.getCurrent()) : MultipointUtils.getMultipoint(this.target, (double)this.distance.getCurrent()));
         if (this.target instanceof PlayerEntity && this.predictOnElytra.isEnabled() && mc.player.isGliding() && this.target.isGliding()) {
            point = PredictUtils.predict(this.target, this.target.getPos(), mc.player.getEyePos().distanceTo(this.target.getBoundingBox().getCenter()) > 8.0D ? 8.0F : this.predict.getCurrent());
         }
         Rotation angle = RotationUtil.fromVec3d(point.subtract(eyes));
         float deltaYaw;
         float deltaPitch;
         float smooth;
         float newYaw;
         if (this.hvh.isSelected()) {
            deltaYaw = MathHelper.wrapDegrees(angle.getYaw() - this.lastYaw);
            deltaPitch = angle.getPitch() - this.lastPitch;
            smooth = this.lastYaw + deltaYaw;
            newYaw = this.lastPitch + deltaPitch;
            smooth -= (smooth - this.lastYaw) % Rotation.gcd();
            newYaw -= (newYaw - this.lastPitch) % Rotation.gcd();
            Rotation smoothRot = new Rotation(smooth, newYaw);
            RotationComponent.update(new Rotation(smoothRot.getYaw(), smoothRot.getPitch()), 360.0F, 360.0F, 360.0F, 360.0F, 0, 1, false);
            this.lastYaw = smoothRot.getYaw();
            this.lastPitch = smoothRot.getPitch();
         }
         Rotation smoothRot;
         float deltaYaw2;
         float deltaPitch2;
         float newPitch;
         if (this.lonyJir.isSelected()) {
            if (mc.player.isGliding()) {
               if (!this.isBack) {
                  this.acceleration += 0.005F;
                  if (this.acceleration >= 0.13F) {
                     this.isBack = true;
                  }
               } else {
                  if (this.acceleration >= -0.02F) {
                     this.acceleration -= 0.005F;
                  }
                  if (this.acceleration <= -0.02F) {
                     this.isBack = false;
                  }
               }
            } else if (!RaytracingUtil.rayTrace(mc.player.getRotationVector(), 1488.0D, this.target.getBoundingBox())) {
               this.acceleration += 0.0015F;
            } else if (this.acceleration > 0.0F) {
               this.acceleration -= 0.01F;
            }
            deltaYaw = MathHelper.wrapDegrees(angle.getYaw() - this.lastYaw);
            deltaPitch = angle.getPitch() - this.lastPitch;
            smooth = Math.max(this.acceleration, 0.0F);
            newYaw = this.lastYaw + deltaYaw * Math.min(Math.max(smooth, 0.0F), 1.0F);
            newPitch = this.lastPitch + deltaPitch * Math.min(Math.max(smooth / 2.0F, 0.0F), 1.0F);
            newYaw -= (newYaw - this.lastYaw) % Rotation.gcd();
            newPitch -= (newPitch - this.lastPitch) % Rotation.gcd();
            smoothRot = new Rotation(newYaw, newPitch);
            deltaYaw2 = MathHelper.wrapDegrees(mc.gameRenderer.getCamera().getYaw() - this.lastYaw);
            deltaPitch2 = mc.gameRenderer.getCamera().getPitch() - this.lastPitch;
            if (mc.options.getPerspective() == Perspective.THIRD_PERSON_FRONT) {
               deltaYaw2 = MathHelper.wrapDegrees(mc.gameRenderer.getCamera().getYaw() - 180.0F - this.lastYaw);
               deltaPitch2 = -mc.gameRenderer.getCamera().getPitch() - this.lastPitch;
            }
            RotationComponent.update(new Rotation(smoothRot.getYaw(), smoothRot.getPitch()), 360.0F, 360.0F, !(Math.abs(deltaYaw2) > 3.0F) && !(Math.abs(deltaPitch2) > 3.0F) ? 360.0F : 0.0F, !(Math.abs(deltaYaw2) > 3.0F) && !(Math.abs(deltaPitch2) > 3.0F) ? 360.0F : 0.0F, 0, 1, false);
            this.lastYaw = smoothRot.getYaw();
            this.lastPitch = smoothRot.getPitch();
         }
         if (this.legendsGrief.isSelected() || this.cake.isSelected()) {
            if (mc.player.isGliding() && this.target.isGliding()) {
               if (this.isBack) {
                  if (this.acceleration >= -0.02F) {
                     this.acceleration -= Math.abs(MathHelper.wrapDegrees(angle.getYaw() - this.lastYaw)) > 80.0F ? 0.1F : 0.01F;
                  }
                  if (this.acceleration <= -0.02F) {
                     this.isBack = false;
                  }
               } else {
                  this.acceleration += 0.004F;
                  if (this.acceleration >= 0.17F || RaytracingUtil.rayTrace(mc.player.getRotationVector(), 1488.0D, box.offset(mc.player.isGliding() && this.target instanceof PlayerEntity && this.target.isGliding() ? PredictUtils.predict(this.target, this.target.getPos(), this.predict.getCurrent()) : Vec3d.ZERO))) {
                     this.isBack = true;
                  }
               }
            } else if (this.isBack) {
               if (this.acceleration >= -0.01F) {
                  this.acceleration -= Math.abs(MathHelper.wrapDegrees(angle.getYaw() - this.lastYaw)) > 80.0F ? 0.1F : 0.01F;
               }
               if (this.acceleration <= -0.01F) {
                  this.isBack = false;
               }
            } else {
               this.acceleration += 0.004F;
               if (this.acceleration >= 0.18F || RaytracingUtil.rayTrace(mc.player.getRotationVector(), 999.0D, box.offset(mc.player.isGliding() && this.target instanceof PlayerEntity && this.target.isGliding() ? PredictUtils.predict(this.target, this.target.getPos(), this.predict.getCurrent()) : Vec3d.ZERO).expand(-0.5D))) {
                  this.isBack = true;
               }
            }
            deltaYaw = MathHelper.wrapDegrees(angle.getYaw() - this.lastYaw);
            deltaPitch = angle.getPitch() - this.lastPitch;
            smooth = Math.max(this.acceleration, 0.0F);
            newYaw = this.lastYaw + deltaYaw * Math.min(Math.max(smooth, 0.0F), 1.0F);
            newPitch = this.lastPitch + deltaPitch * Math.min(Math.max(smooth / 2.0F, 0.0F), 1.0F);
            newYaw -= (newYaw - this.lastYaw) % Rotation.gcd();
            newPitch -= (newPitch - this.lastPitch) % Rotation.gcd();
            smoothRot = new Rotation(newYaw, newPitch);
            deltaYaw2 = MathHelper.wrapDegrees(mc.gameRenderer.getCamera().getYaw() - this.lastYaw);
            deltaPitch2 = mc.gameRenderer.getCamera().getPitch() - this.lastPitch;
            if (mc.options.getPerspective() == Perspective.THIRD_PERSON_FRONT) {
               deltaYaw2 = MathHelper.wrapDegrees(mc.gameRenderer.getCamera().getYaw() - 180.0F - this.lastYaw);
               deltaPitch2 = -mc.gameRenderer.getCamera().getPitch() - this.lastPitch;
            }
            RotationComponent.update(new Rotation(smoothRot.getYaw(), smoothRot.getPitch()), 360.0F, 360.0F, !(Math.abs(deltaYaw2) > 3.0F) && !(Math.abs(deltaPitch2) > 3.0F) ? 360.0F : 0.0F, !(Math.abs(deltaYaw2) > 3.0F) && !(Math.abs(deltaPitch2) > 3.0F) ? 360.0F : 0.0F, 0, 1, false);
            this.lastYaw = smoothRot.getYaw();
            this.lastPitch = smoothRot.getPitch();
         }
      }
   }
   private boolean isCanAttack() {
      if (mc.player.getAttackCooldownProgress(0.5F) < 0.9F) {
         return false;
      } else if (!AttackUtil.canAttack()) {
         return false;
      } else if (this.target instanceof PlayerEntity && this.predictOnElytra.isEnabled() && mc.player.isGliding() && this.target.isGliding() && mc.player.getEyePos().distanceTo(PredictUtils.predict(this.target, this.target.getPos(), this.predict.getCurrent())) > 3.0D && mc.player.getEyePos().distanceTo(this.target.getBoundingBox().getCenter()) > 3.0D) {
         return false;
      } else if ((!mc.player.isGliding() || !this.target.isGliding()) && mc.player.getEyePos().distanceTo(MultipointUtils.getNearestPoint(this.target, (double)this.distance.getCurrent())) > (double)this.distance.getCurrent()) {
         return false;
      } else {
         return !this.raycastCheck.isEnabled() || RaytracingUtil.rayTrace(mc.player.getRotationVector(), (double)this.distance.getCurrent(), this.target.getBoundingBox()) || mc.targetedEntity != null || mc.player.isGliding() || this.target.isGliding();
      }
   }
   private LivingEntity updateTarget() {
      List<LivingEntity> targets = new ArrayList();
      Iterator var2 = mc.world.getEntities().iterator();
      while(var2.hasNext()) {
         Entity entity = (Entity)var2.next();
         if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            if (this.isValid(living)) {
               targets.add(living);
            }
         }
      }
      if (!targets.isEmpty() && this.isEnabled()) {
         targets.sort(Comparator.comparingDouble((entityx) -> {
            Rotation vec = Rotation.getRotations(entityx.getBoundingBox().getCenter());
            double dy = (double)Math.abs(MathHelper.wrapDegrees(vec.getYaw() - mc.player.getYaw()));
            double dp = (double)Math.abs(MathHelper.wrapDegrees(vec.getPitch() - mc.player.getPitch()));
            return dy + dp;
         }));
         return targets.isEmpty() ? null : (LivingEntity)targets.get(0);
      } else {
         return null;
      }
   }
   public boolean isValid(LivingEntity entity) {
      if (entity == mc.player) {
         return false;
      } else if (entity.isAlive() && !(entity.getHealth() <= 0.0F)) {
         if (mc.player.isAlive() && !(mc.player.getHealth() <= 0.0F)) {
            if (entity instanceof PlayerEntity) {
               PlayerEntity player = (PlayerEntity)entity;
               if (!this.targetTypeSetting.isEnable("Ð˜Ð³Ñ€Ð¾ÐºÐ¾Ð²")) {
                  return false;
               }
               if (Amethyst.getInstance().getFriendManager().isFriend(entity.getName().getString())) {
                  return false;
               }
               if (AntiBot.INSTANCE.isBot(player)) {
                  return false;
               }
            }
            if (!(entity instanceof PassiveEntity) && !(entity instanceof FishEntity) || this.targetTypeSetting.isEnable("Ð–Ð¸Ð²Ð¾Ñ‚Ð½Ñ‹Ñ…") && !Amethyst.getInstance().getServerHandler().isPvp()) {
               if (!(entity instanceof HostileEntity) && !(entity instanceof AmbientEntity) || this.targetTypeSetting.isEnable("ÐœÐ¾Ð±Ð¾Ð²") && !Amethyst.getInstance().getServerHandler().isPvp()) {
                  if (mc.player.getEyePos().distanceTo(MultipointUtils.getNearestPoint(entity, (double)(this.distance.getCurrent() + this.distanceRotation.getCurrent()))) > (double)(mc.player.isGliding() ? 20.0F : this.distance.getCurrent() + this.distanceRotation.getCurrent())) {
                     return false;
                  } else {
                     return !(entity instanceof ArmorStandEntity);
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
   @EventTarget
   private void setCorrection(EventMoveInput eventMoveInput) {
      if (!this.correctionNone.isSelected() && this.target != null) {
         if (this.correctionFocus.isSelected()) {
            MovingUtil.fixMovementFocus(eventMoveInput, mc.player.getYaw());
         } else {
            MovingUtil.fixMovementFree(eventMoveInput);
         }
      }
   }
   public LivingEntity getTarget() {
      return this.isEnabled() ? this.target : null;
   }
   public void onEnable() {
      this.target = null;
      super.onEnable();
   }
   public void onDisable() {
      Amethyst.getInstance().getModuleManager().setAcceleration(0.0F);
      // Reset rotation state to prevent locked rotation when re-enabling
      this.lastYaw = mc.player.getYaw();
      this.lastPitch = mc.player.getPitch();
      this.target = null;
      this.isBack = false;
      this.acceleration = 0.0F;
      super.onDisable();
   }
}



