package tech.amethyst.utility.game.other;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.PlainTextContent.Literal;
import net.minecraft.util.Formatting;
import ru.nexusguard.protection.annotations.Native;
public class ReplaceUtil {
   @Native
   public static Text replace(Text input, String target, String replacement) {
      if (input != null && target != null && replacement != null) {
         MutableText result = Text.empty().setStyle(input.getStyle());
         appendReplaced(result, input, target, replacement);
         return result;
      } else {
         return input;
      }
   }
   @Native
   private static void appendReplaced(MutableText result, Text current, String target, String replacement) {
      TextContent content = current.getContent();
      Style style = current.getStyle();
      if (content instanceof Literal) {
         Literal literal = (Literal)content;
         Pattern pattern = Pattern.compile(Pattern.quote(target), 2);
         String replaced = pattern.matcher(literal.string()).replaceAll(replacement);
         result.append(Text.literal(replaced).setStyle(style));
      }
      Iterator var9 = current.getSiblings().iterator();
      while(var9.hasNext()) {
         Text sibling = (Text)var9.next();
         appendReplaced(result, sibling, target, replacement);
      }
   }
   @Native
   public static Text replaceLiteral(Text input, String target, String replacement) {
      if (input == null) {
         return null;
      } else {
         String full = input.getString();
         if (!full.toLowerCase().contains(target.toLowerCase())) {
            return input;
         } else {
            full = full.replaceAll("(?i)" + Pattern.quote(target), replacement);
            MutableText out = Text.empty();
            List<ReplaceUtil.StyledChar> styledChars = flatten(input);
            int index = 0;
            for(int i = 0; i < full.length(); ++i) {
               Style style = index < styledChars.size() ? ((ReplaceUtil.StyledChar)styledChars.get(index)).style : Style.EMPTY;
               out.append(Text.literal(String.valueOf(full.charAt(i))).setStyle(style));
               ++index;
            }
            return out;
         }
      }
   }
   @Native
   private static List<ReplaceUtil.StyledChar> flatten(Text text) {
      List<ReplaceUtil.StyledChar> list = new ArrayList();
      collect(text, list);
      return list;
   }
   private static void collect(Text text, List<ReplaceUtil.StyledChar> list) {
      Style style = text.getStyle();
      TextContent var4 = text.getContent();
      if (var4 instanceof Literal) {
         Literal literal = (Literal)var4;
         String s = literal.string();
         for(int i = 0; i < s.length(); ++i) {
            list.add(new ReplaceUtil.StyledChar(s.charAt(i), style));
         }
      }
      Iterator var6 = text.getSiblings().iterator();
      while(var6.hasNext()) {
         Text sibling = (Text)var6.next();
         collect(sibling, list);
      }
   }
   @Native
   public static String replaceSymbols(String string) {
      String var10000 = string.replaceAll("ê”—", String.valueOf(Formatting.BLUE) + "MODER").replaceAll("ê”¥", String.valueOf(Formatting.BLUE) + "ST.MODER").replaceAll("ê”¡", String.valueOf(Formatting.LIGHT_PURPLE) + "MODER+").replaceAll("ê”€", String.valueOf(Formatting.GRAY) + "PLAYER").replaceAll("ê”‰", String.valueOf(Formatting.YELLOW) + "HELPER").replaceAll("â—†", "@").replaceAll("â”ƒ", "|").replaceAll("ê”³", String.valueOf(Formatting.AQUA) + "ML.ADMIN");
      String var10002 = String.valueOf(Formatting.RED);
      return var10000.replaceAll("ê”…", var10002 + "Y" + String.valueOf(Formatting.WHITE) + "T").replaceAll("ê”‚", String.valueOf(Formatting.BLUE) + "D.MODER").replaceAll("ê• ", String.valueOf(Formatting.YELLOW) + "D.HELPER").replaceAll("ê•„", String.valueOf(Formatting.RED) + "DRACULA").replaceAll("ê”–", String.valueOf(Formatting.AQUA) + "OVERLORD").replaceAll("ê•ˆ", String.valueOf(Formatting.GREEN) + "COBRA").replaceAll("ê”¨", String.valueOf(Formatting.LIGHT_PURPLE) + "DRAGON").replaceAll("ê”¤", String.valueOf(Formatting.RED) + "IMPERATOR").replaceAll("ê” ", String.valueOf(Formatting.GOLD) + "MAGISTER").replaceAll("ê”„", String.valueOf(Formatting.BLUE) + "HERO").replaceAll("ê”’", String.valueOf(Formatting.GREEN) + "AVENGER").replaceAll("ê•’", String.valueOf(Formatting.WHITE) + "RABBIT").replaceAll("ê”ˆ", String.valueOf(Formatting.YELLOW) + "TITAN").replaceAll("ê•€", String.valueOf(Formatting.DARK_GREEN) + "HYDRA").replaceAll("ê”¶", String.valueOf(Formatting.GOLD) + "TIGER").replaceAll("ê”²", String.valueOf(Formatting.DARK_PURPLE) + "BULL").replaceAll("ê•–", String.valueOf(Formatting.BLACK) + "BUNNY").replaceAll("ê•—ê•˜", String.valueOf(Formatting.YELLOW) + "SPONSOR").replaceAll("\ud83d\udd25", "@").replaceAll("á´€", "A").replaceAll("Ê™", "B").replaceAll("á´„", "C").replaceAll("á´…", "D").replaceAll("á´‡", "E").replaceAll("Ò“", "F").replaceAll("É¢", "G").replaceAll("Êœ", "H").replaceAll("Éª", "I").replaceAll("á´Š", "J").replaceAll("á´‹", "K").replaceAll("ÊŸ", "L").replaceAll("á´", "M").replaceAll("É´", "N").replaceAll("êœ±", "S").replaceAll("á´", "O").replaceAll("á´˜", "P").replaceAll("Ç«", "Q").replaceAll("Ê€", "R").replaceAll("á´›", "T").replaceAll("á´œ", "U").replaceAll("á´ ", "V").replaceAll("á´¡", "W").replaceAll("êœ°", "F").replaceAll("Ê", "Y").replaceAll("á´¢", "Z");
   }
   public static Text replaceSymbols(Text text) {
      if (text.getString().contains("ê”—")) {
         text = replace(text, "ê”—", String.valueOf(Formatting.BLUE) + "MODER");
      }
      if (text.getString().contains("ê”¥")) {
         text = replace(text, "ê”¥", String.valueOf(Formatting.BLUE) + "ST.MODER");
      }
      if (text.getString().contains("ê”¡")) {
         text = replace(text, "ê”¡", String.valueOf(Formatting.LIGHT_PURPLE) + "MODER+");
      }
      if (text.getString().contains("ê”€")) {
         text = replace(text, "ê”€", String.valueOf(Formatting.GRAY) + "PLAYER");
      }
      if (text.getString().contains("ê”‰")) {
         text = replace(text, "ê”‰", String.valueOf(Formatting.YELLOW) + "HELPER");
      }
      if (text.getString().contains("â—†")) {
         text = replace(text, "â—†", "@");
      }
      if (text.getString().contains("â”ƒ")) {
         text = replace(text, "â”ƒ", "|");
      }
      if (text.getString().contains("ê”³")) {
         text = replace(text, "ê”³", String.valueOf(Formatting.AQUA) + "ML.ADMIN");
      }
      if (text.getString().contains("ê”…")) {
         String var10002 = String.valueOf(Formatting.RED);
         text = replace(text, "ê”…", var10002 + "Y" + String.valueOf(Formatting.WHITE) + "T");
      }
      if (text.getString().contains("ê”‚")) {
         text = replace(text, "ê”‚", String.valueOf(Formatting.BLUE) + "D.MODER");
      }
      if (text.getString().contains("ê• ")) {
         text = replace(text, "ê• ", String.valueOf(Formatting.YELLOW) + "D.HELPER");
      }
      if (text.getString().contains("ê•„")) {
         text = replace(text, "ê•„", String.valueOf(Formatting.RED) + "DRACULA");
      }
      if (text.getString().contains("ê”–")) {
         text = replace(text, "ê”–", String.valueOf(Formatting.AQUA) + "OVERLORD");
      }
      if (text.getString().contains("ê•ˆ")) {
         text = replace(text, "ê•ˆ", String.valueOf(Formatting.GREEN) + "COBRA");
      }
      if (text.getString().contains("ê”¨")) {
         text = replace(text, "ê”¨", String.valueOf(Formatting.LIGHT_PURPLE) + "DRAGON");
      }
      if (text.getString().contains("ê”¤")) {
         text = replace(text, "ê”¤", String.valueOf(Formatting.RED) + "IMPERATOR");
      }
      if (text.getString().contains("ê” ")) {
         text = replace(text, "ê” ", String.valueOf(Formatting.GOLD) + "MAGISTER");
      }
      if (text.getString().contains("ê”„")) {
         text = replace(text, "ê”„", String.valueOf(Formatting.BLUE) + "HERO");
      }
      if (text.getString().contains("ê”’")) {
         text = replace(text, "ê”’", String.valueOf(Formatting.GREEN) + "AVENGER");
      }
      if (text.getString().contains("ê•’")) {
         text = replace(text, "ê•’", String.valueOf(Formatting.WHITE) + "RABBIT");
      }
      if (text.getString().contains("ê”ˆ")) {
         text = replace(text, "ê”ˆ", String.valueOf(Formatting.YELLOW) + "TITAN");
      }
      if (text.getString().contains("ê•€")) {
         text = replace(text, "ê•€", String.valueOf(Formatting.DARK_GREEN) + "HYDRA");
      }
      if (text.getString().contains("ê”¶")) {
         text = replace(text, "ê”¶", String.valueOf(Formatting.GOLD) + "TIGER");
      }
      if (text.getString().contains("ê”²")) {
         text = replace(text, "ê”²", String.valueOf(Formatting.DARK_PURPLE) + "BULL");
      }
      if (text.getString().contains("ê•–")) {
         text = replace(text, "ê•–", String.valueOf(Formatting.BLACK) + "BUNNY");
      }
      if (text.getString().contains("ê•—ê•˜")) {
         text = replace(text, "ê•—ê•˜", String.valueOf(Formatting.YELLOW) + "SPONSOR");
      }
      if (text.getString().contains("\ud83d\udd25")) {
         text = replace(text, "\ud83d\udd25", "@");
      }
      if (text.getString().contains("á´€")) {
         text = replace(text, "á´€", "A");
      }
      if (text.getString().contains("Ê™")) {
         text = replace(text, "Ê™", "B");
      }
      if (text.getString().contains("á´„")) {
         text = replace(text, "á´„", "C");
      }
      if (text.getString().contains("á´…")) {
         text = replace(text, "á´…", "D");
      }
      if (text.getString().contains("á´‡")) {
         text = replace(text, "á´‡", "E");
      }
      if (text.getString().contains("Ò“")) {
         text = replace(text, "Ò“", "F");
      }
      if (text.getString().contains("É¢")) {
         text = replace(text, "É¢", "G");
      }
      if (text.getString().contains("Êœ")) {
         text = replace(text, "Êœ", "H");
      }
      if (text.getString().contains("Éª")) {
         text = replace(text, "Éª", "I");
      }
      if (text.getString().contains("á´Š")) {
         text = replace(text, "á´Š", "J");
      }
      if (text.getString().contains("á´‹")) {
         text = replace(text, "á´‹", "K");
      }
      if (text.getString().contains("ÊŸ")) {
         text = replace(text, "ÊŸ", "L");
      }
      if (text.getString().contains("á´")) {
         text = replace(text, "á´", "M");
      }
      if (text.getString().contains("É´")) {
         text = replace(text, "É´", "N");
      }
      if (text.getString().contains("êœ±")) {
         text = replace(text, "êœ±", "S");
      }
      if (text.getString().contains("á´")) {
         text = replace(text, "á´", "O");
      }
      if (text.getString().contains("á´˜")) {
         text = replace(text, "á´˜", "P");
      }
      if (text.getString().contains("Ç«")) {
         text = replace(text, "Ç«", "Q");
      }
      if (text.getString().contains("Ê€")) {
         text = replace(text, "Ê€", "R");
      }
      if (text.getString().contains("á´›")) {
         text = replace(text, "á´›", "T");
      }
      if (text.getString().contains("á´œ")) {
         text = replace(text, "á´œ", "U");
      }
      if (text.getString().contains("á´ ")) {
         text = replace(text, "á´ ", "V");
      }
      if (text.getString().contains("á´¡")) {
         text = replace(text, "á´¡", "W");
      }
      if (text.getString().contains("êœ°")) {
         text = replace(text, "êœ°", "F");
      }
      if (text.getString().contains("Ê")) {
         text = replace(text, "Ê", "Y");
      }
      if (text.getString().contains("á´¢")) {
         text = replace(text, "á´¢", "Z");
      }
      return text;
   }
   public static String toQwerty(String text) {
      return text.replace("Ð¹", "q").replace("Ñ†", "w").replace("Ñƒ", "e").replace("Ðº", "r").replace("Ðµ", "t").replace("Ð½", "y").replace("Ð³", "u").replace("Ñˆ", "i").replace("Ñ‰", "o").replace("Ð·", "p").replace("Ñ…", "[").replace("ÑŠ", "]").replace("Ñ„", "a").replace("Ñ‹", "s").replace("Ð²", "d").replace("Ð°", "f").replace("Ð¿", "g").replace("Ñ€", "h").replace("Ð¾", "j").replace("Ð»", "k").replace("Ð´", "l").replace("Ð¶", ";").replace("Ñ", "'").replace("Ñ", "z").replace("Ñ‡", "x").replace("Ñ", "c").replace("Ð¼", "v").replace("Ð¸", "b").replace("Ñ‚", "n").replace("ÑŒ", "m").replace("Ð±", ",").replace("ÑŽ", ".").replace("Ñ‘", "`").replace("Ð™", "Q").replace("Ð¦", "W").replace("Ð£", "E").replace("Ðš", "R").replace("Ð•", "T").replace("Ð", "Y").replace("Ð“", "U").replace("Ð¨", "I").replace("Ð©", "O").replace("Ð—", "P").replace("Ð¥", "{").replace("Ðª", "}").replace("Ð¤", "A").replace("Ð«", "S").replace("Ð’", "D").replace("Ð", "F").replace("ÐŸ", "G").replace("Ð ", "H").replace("Ðž", "J").replace("Ð›", "K").replace("Ð”", "L").replace("Ð–", ":").replace("Ð­", "\"").replace("Ð¯", "Z").replace("Ð§", "X").replace("Ð¡", "C").replace("Ðœ", "V").replace("Ð˜", "B").replace("Ð¢", "N").replace("Ð¬", "M").replace("Ð‘", "<").replace("Ð®", ">").replace("Ð", "~");
   }
   private static record StyledChar(char c, Style style) {
      private StyledChar(char c, Style style) {
         this.c = c;
         this.style = style;
      }
      public char c() {
         return this.c;
      }
      public Style style() {
         return this.style;
      }
   }
}



