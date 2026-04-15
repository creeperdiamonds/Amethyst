package tech.amethyst.utility.math;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
public class MathUtils {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
    public static double getRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    public static boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
    public static double coerceIn(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    public static float coerceIn(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}



