package ru.nexusguard;
import lombok.Generated;
public class UserProfile implements IGuard {
   public static UserProfile instance = new UserProfile();
   public UserProfile.IRCProfile irc = new UserProfile.IRCProfile((String)null);
   public static String username = "NexusUser";
   public static String hwid = "123";
   public static int uid = 1337;
   public static String role = "DEV";
   public String username() {
      return username;
   }
   public String hwid() {
      return hwid;
   }
   public String role() {
      return role;
   }
   public int uid() {
      return uid;
   }
   public String roleName() {
      String var1 = role;
      byte var2 = -1;
      switch(var1.hashCode()) {
      case 67573:
         if (var1.equals("DEV")) {
            var2 = 1;
         }
         break;
      case 2035184:
         if (var1.equals("BETA")) {
            var2 = 4;
         }
         break;
      case 2094806:
         if (var1.equals("DEV+")) {
            var2 = 0;
         }
         break;
      case 62130991:
         if (var1.equals("ADMIN")) {
            var2 = 2;
         }
         break;
      case 73234372:
         if (var1.equals("MEDIA")) {
            var2 = 3;
         }
      }
      switch(var2) {
      case 0:
      case 1:
         return "Ð Ð°Ð·Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸Ðº";
      case 2:
         return "ÐÐ´Ð¼Ð¸Ð½Ð¸ÑÑ‚Ñ€Ð°Ñ‚Ð¾Ñ€";
      case 3:
         return "ÐœÐµÐ´Ð¸Ð°";
      case 4:
         return "Ð‘ÐµÑ‚Ð°";
      default:
         return "ÐŸÐ¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÐµÐ»ÑŒ";
      }
   }
   public static class IRCProfile {
      private String prefix;
      public String getPrefix() {
         return this.prefix == null ? this.getRoleName() : this.prefix;
      }
      public void setPrefix(String newPrefix) {
         this.prefix = newPrefix;
      }
      public String getRoleName() {
         String var1 = UserProfile.role;
         byte var2 = -1;
         switch(var1.hashCode()) {
         case 67573:
            if (var1.equals("DEV")) {
               var2 = 1;
            }
            break;
         case 2035184:
            if (var1.equals("BETA")) {
               var2 = 4;
            }
            break;
         case 2094806:
            if (var1.equals("DEV+")) {
               var2 = 0;
            }
            break;
         case 62130991:
            if (var1.equals("ADMIN")) {
               var2 = 2;
            }
            break;
         case 73234372:
            if (var1.equals("MEDIA")) {
               var2 = 3;
            }
         }
         switch(var2) {
         case 0:
         case 1:
            return "Â§cÐ Ð°Ð·Ñ€Ð°Ð±Ð¾Ñ‚Ñ‡Ð¸Ðº";
         case 2:
            return "Â§4ÐÐ´Ð¼Ð¸Ð½Ð¸ÑÑ‚Ñ€Ð°Ñ‚Ð¾Ñ€";
         case 3:
            return "Â§cÐœÐµÐ´Ð¸Ð°";
         case 4:
            return "Â§9Ð‘ÐµÑ‚Ð°";
         default:
            return "Â§7ÐŸÐ¾Ð»ÑŒÐ·Ð¾Ð²Ð°Ñ‚ÐµÐ»ÑŒ";
         }
      }
      @Generated
      public IRCProfile(String prefix) {
         this.prefix = prefix;
      }
   }
}



