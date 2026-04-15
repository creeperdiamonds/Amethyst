package tech.amethyst.base.autobuy.enchantes.container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tech.amethyst.base.autobuy.enchantes.Enchant;
import tech.amethyst.base.autobuy.enchantes.custom.EnchantCustom;
import tech.amethyst.base.autobuy.enchantes.minecraft.EnchantVanilla;
public class EnchantContainer {
   public static final Map<String, String> ENCHANT_MAP = new HashMap();
   public static List<Enchant> parse(String input) {
      List<Enchant> enchants = new ArrayList();
      String trimmed = input.trim();
      if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
         trimmed = trimmed.substring(1, trimmed.length() - 1);
      }
      Pattern pattern = Pattern.compile("(EnchantCustom|EnchantVanilla) \\[checked=([^,]+), level=(\\d+)]");
      Object enchant;
      for(Matcher matcher = pattern.matcher(trimmed); matcher.find(); ) {
         String className = matcher.group(1);
         String checked = matcher.group(2);
         int level = Integer.parseInt(matcher.group(3));
         String name = (String)ENCHANT_MAP.getOrDefault(checked, checked);
         if (className.equals("EnchantCustom")) {
            enchant = new EnchantCustom(name, checked, level);
         } else {
            enchant = new EnchantVanilla(name, checked, level);
         }
      }
      return enchants;
   }
   static {
      ENCHANT_MAP.put("oxidation", "ÐžÐºÐ¸ÑÐ»ÐµÐ½Ð¸Ðµ");
      ENCHANT_MAP.put("detection", "Ð”ÐµÑ‚ÐµÐºÑ†Ð¸Ñ");
      ENCHANT_MAP.put("poison", "Ð¯Ð´");
      ENCHANT_MAP.put("vampirism", "Ð’Ð°Ð¼Ð¿Ð¸Ñ€Ð¸Ð·Ð¼");
      ENCHANT_MAP.put("skilled", "ÐžÐ¿Ñ‹Ñ‚Ð½Ñ‹Ð¹");
      ENCHANT_MAP.put("smelting", "ÐÐ²Ñ‚Ð¾Ð¿Ð»Ð°Ð²ÐºÐ°");
      ENCHANT_MAP.put("magnet", "ÐœÐ°Ð³Ð½Ð¸Ñ‚");
      ENCHANT_MAP.put("pinger", "ÐŸÐ¸Ð½Ð³ÐµÑ€");
      ENCHANT_MAP.put("web", "ÐŸÐ°ÑƒÑ‚Ð¸Ð½Ð°");
      ENCHANT_MAP.put("buldozing", "Ð‘ÑƒÐ»ÑŒÐ´Ð¾Ð·ÐµÑ€");
      ENCHANT_MAP.put("pulling", "ÐŸÑ€Ð¸Ñ‚ÑÐ³Ð¸Ð²Ð°Ð½Ð¸Ðµ");
      ENCHANT_MAP.put("stupor", "Ð¡Ñ‚ÑƒÐ¿Ð¾Ñ€");
      ENCHANT_MAP.put("demolishing", "Ð Ð°Ð·Ñ€ÑƒÑˆÐµÐ½Ð¸Ðµ");
      ENCHANT_MAP.put("returning", "Ð’Ð¾Ð·Ð²Ñ€Ð°Ñ‚");
      ENCHANT_MAP.put("scout", "Ð¡ÐºÐ°ÑƒÑ‚");
      ENCHANT_MAP.put("minecraft:protection", "Ð—Ð°Ñ‰Ð¸Ñ‚Ð°");
      ENCHANT_MAP.put("minecraft:fire_protection", "ÐžÐ³Ð½ÐµÑƒÐ¿Ð¾Ñ€Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:feather_falling", "ÐÐµÐ²ÐµÑÐ¾Ð¼Ð¾ÑÑ‚ÑŒ (ÐŸÐ°Ð´ÐµÐ½Ð¸Ðµ)");
      ENCHANT_MAP.put("minecraft:blast_protection", "Ð’Ð·Ñ€Ñ‹Ð²Ð¾ÑƒÑÑ‚Ð¾Ð¹Ñ‡Ð¸Ð²Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:projectile_protection", "Ð—Ð°Ñ‰Ð¸Ñ‚Ð° Ð¾Ñ‚ ÑÐ½Ð°Ñ€ÑÐ´Ð¾Ð²");
      ENCHANT_MAP.put("minecraft:thorns", "Ð¨Ð¸Ð¿Ñ‹");
      ENCHANT_MAP.put("minecraft:soul_speed", "Ð¡ÐºÐ¾Ñ€Ð¾ÑÑ‚ÑŒ Ð´ÑƒÑˆÐ¸");
      ENCHANT_MAP.put("minecraft:respiration", "ÐŸÐ¾Ð´Ð²Ð¾Ð´Ð½Ð¾Ðµ Ð´Ñ‹Ñ…Ð°Ð½Ð¸Ðµ");
      ENCHANT_MAP.put("minecraft:depth_strider", "ÐŸÐ¾Ð´Ð²Ð¾Ð´Ð½Ð¸Ðº (ÐŸÐ¾Ð´Ð²Ð¾Ð´Ð½Ð°Ñ Ñ…Ð¾Ð´ÑŒÐ±Ð°)");
      ENCHANT_MAP.put("minecraft:aqua_affinity", "ÐŸÐ¾Ð´Ð²Ð¾Ð´Ð½Ð¸Ðº (Ð£ÑÐºÐ¾Ñ€ÐµÐ½Ð¸Ðµ Ð´Ð¾Ð±Ñ‹Ñ‡Ð¸ Ð¿Ð¾Ð´ Ð²Ð¾Ð´Ð¾Ð¹)");
      ENCHANT_MAP.put("minecraft:frost_walker", "Ð›ÐµÐ´Ð¾Ñ…Ð¾Ð´");
      ENCHANT_MAP.put("minecraft:sharpness", "ÐžÑÑ‚Ñ€Ð¾Ñ‚Ð°");
      ENCHANT_MAP.put("minecraft:smite", "ÐÐµÐ±ÐµÑÐ½Ð°Ñ ÐºÐ°Ñ€Ð°");
      ENCHANT_MAP.put("minecraft:bane_of_arthropods", "Ð‘Ð¸Ñ‡ Ñ‡Ð»ÐµÐ½Ð¸ÑÑ‚Ð¾Ð½Ð¾Ð³Ð¸Ñ…");
      ENCHANT_MAP.put("minecraft:knockback", "ÐžÑ‚Ð±Ñ€Ð°ÑÑ‹Ð²Ð°Ð½Ð¸Ðµ");
      ENCHANT_MAP.put("minecraft:fire_aspect", "Ð—Ð°Ð³Ð¾Ð²Ð¾Ñ€ Ð¾Ð³Ð½Ñ");
      ENCHANT_MAP.put("minecraft:looting", "Ð”Ð¾Ð±Ñ‹Ñ‡Ð°");
      ENCHANT_MAP.put("minecraft:sweeping_edge", "Ð Ð°Ð·ÑÑ‰Ð¸Ð¹ ÐºÐ»Ð¸Ð½Ð¾Ðº");
      ENCHANT_MAP.put("minecraft:efficiency", "Ð­Ñ„Ñ„ÐµÐºÑ‚Ð¸Ð²Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:silk_touch", "Ð¨Ñ‘Ð»ÐºÐ¾Ð²Ð¾Ðµ ÐºÐ°ÑÐ°Ð½Ð¸Ðµ");
      ENCHANT_MAP.put("minecraft:unbreaking", "ÐŸÑ€Ð¾Ñ‡Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:fortune", "Ð£Ð´Ð°Ñ‡Ð°");
      ENCHANT_MAP.put("minecraft:mending", "ÐŸÐ¾Ñ‡Ð¸Ð½ÐºÐ°");
      ENCHANT_MAP.put("minecraft:impaling", "ÐŸÑ€Ð¾Ð½Ð·Ð°Ñ‚ÐµÐ»ÑŒ");
      ENCHANT_MAP.put("minecraft:power", "Ð¡Ð¸Ð»Ð°");
      ENCHANT_MAP.put("minecraft:punch", "ÐžÑ‚ÐºÐ¸Ð´Ñ‹Ð²Ð°Ð½Ð¸Ðµ (Ð£Ð´Ð°Ñ€ ÑÑ‚Ñ€ÐµÐ»Ð¾Ð¹)");
      ENCHANT_MAP.put("minecraft:flame", "Ð’Ð¾ÑÐ¿Ð»Ð°Ð¼ÐµÐ½ÐµÐ½Ð¸Ðµ");
      ENCHANT_MAP.put("minecraft:infinity", "Ð‘ÐµÑÐºÐ¾Ð½ÐµÑ‡Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:piercing", "Ð¢Ð¾Ñ‡Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:multishot", "ÐœÐ½Ð¾Ð³Ð¾ÑÑ‚Ñ€ÐµÐ»ÑŒÐ½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:quick_charge", "Ð‘Ñ‹ÑÑ‚Ñ€Ð°Ñ Ð¿ÐµÑ€ÐµÐ·Ð°Ñ€ÑÐ´ÐºÐ°");
      ENCHANT_MAP.put("minecraft:riptide", "Ð—Ð°Ð¼ÐµÐ´Ð»ÐµÐ½Ð¸Ðµ");
      ENCHANT_MAP.put("minecraft:loyalty", "Ð’ÐµÑ€Ð½Ð¾ÑÑ‚ÑŒ");
      ENCHANT_MAP.put("minecraft:channeling", "Ð“Ñ€Ð¾Ð¼Ð¾Ð²ÐµÑ€Ð¶ÐµÑ†");
      ENCHANT_MAP.put("minecraft:luck_of_the_sea", "Ð’ÐµÐ·ÑƒÑ‡Ð¸Ð¹ Ñ€Ñ‹Ð±Ð°Ðº");
      ENCHANT_MAP.put("minecraft:lure", "ÐŸÑ€Ð¸Ð¼Ð°Ð½ÐºÐ°");
      ENCHANT_MAP.put("minecraft:binding_curse", "ÐŸÑ€Ð¾ÐºÐ»ÑÑ‚Ð¸Ðµ Ð½ÐµÑÑŠÑ‘Ð¼Ð½Ð¾ÑÑ‚Ð¸");
      ENCHANT_MAP.put("minecraft:vanishing_curse", "ÐŸÑ€Ð¾ÐºÐ»ÑÑ‚Ð¸Ðµ ÑƒÑ‚Ñ€Ð°Ñ‚Ñ‹");
   }
}



