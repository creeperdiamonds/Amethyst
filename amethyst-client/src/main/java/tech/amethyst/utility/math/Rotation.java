package tech.amethyst.utility.math;
public class Rotation {
   private final double yaw;
   private final double pitch;
   public Rotation(double yaw, double pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }
   public Rotation withfPitch(float pitch) {
      return new Rotation(this.yaw, (double)pitch);
   }
   public Rotation withfYaw(float yaw) {
      return new Rotation((double)yaw, this.pitch);
   }
   public Rotation withPitch(double pitch) {
      return new Rotation(this.yaw, pitch);
   }
   public Rotation withYaw(double yaw) {
      return new Rotation(yaw, this.pitch);
   }
   public float fyaw() {
      return (float)this.yaw;
   }
   public float fpitch() {
      return (float)this.pitch;
   }
   public double yaw() {
      return this.yaw;
   }
   public double pitch() {
      return this.pitch;
   }
   public Rotation copy() {
      return new Rotation(this.yaw, this.pitch);
   }
   public String toString() {
      return "Yaw: " + this.yaw + " Pitch: " + this.pitch;
   }
}



