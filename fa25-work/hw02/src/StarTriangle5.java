public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      for (int i = 1; i <= 5; i++) {
         for (int k = 1; k <= 5 - i; k++) {
            System.out.print(' ');
         }
         for (int k = 1; k <= i; k++) {
            System.out.print('*');
         }
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}