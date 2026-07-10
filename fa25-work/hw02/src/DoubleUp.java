public class DoubleUp {
   /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
   public static String doubleUp(String s) {
      // Prevent NullPointerException
      if (s == null) {
         return null;
      }

      // Original implementation: O(N**2)
      /**
      String doubleS = "";
      for (int i = 0; i < s.length(); i++) {
         doubleS += s.charAt(i);
         doubleS += s.charAt(i);
      }
      return doubleS;
       */

      StringBuilder doubleS = new StringBuilder();
      for (int i = 0; i < s.length(); i++) {
         doubleS.append(s.charAt(i));
         doubleS.append(s.charAt(i));
      }
      return doubleS.toString();
   }

   public static void main(String[] args) {
      String s = doubleUp("hello");
      System.out.println(s);
      
      System.out.println(doubleUp("cat"));
   }
}