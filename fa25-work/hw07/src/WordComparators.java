import java.util.Comparator;
import java.util.List;

public class WordComparators {
    private static class XComparator implements Comparator<String> {

        /**
         * Compares strings based on the number of lowercase x’s that appear in the string.
         * For example, “xelha” should be considered less than “xoxocotla”,
         * because “xelha” has one x and “xoxocotla” has two.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(String o1, String o2) {
            int countO1 = charCountInString('x', o1);
            int countO2 = charCountInString('x', o2);
            return countO1 - countO2;
        }
    }

    private static class CharComparator implements Comparator<String> {
        char c;
        CharComparator(char c) {
            this.c = c;
        }

        /**
         *  Compares strings based on the number of instances of c that occur,
         *  where c is the character specified when the Comparator is created.
         *  For example, the Comparator returned by getCharComparator('a')
         *  should consider “xelha” to be equal to “xoxocotla”, because they both have one ‘a’.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(String o1, String o2) {
            int countO1 = charCountInString(this.c, o1);
            int countO2 = charCountInString(this.c, o2);
            return countO1 - countO2;
        }
    }

    private static class CharListComparator implements Comparator<String> {
        List<Character> chars;
        CharListComparator(List<Character> chars) {
            this.chars = chars;
        }

        /**
         * Compares strings based on the number of times any of the characters in the list appear in the string.
         * For example, getCharListComparator(List.of('a', 'e', 'i', 'o', 'u')) should return a comparator that
         * compares based on the number of lowercase vowels in the string.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(String o1, String o2) {
            int countO1 = 0;
            int countO2 = 0;
            for (Character c : this.chars) {
                countO1 += charCountInString(c, o1);
                countO2 += charCountInString(c, o2);
            }
            return countO1 - countO2;
        }
    }

    /** Returns the count of char C in string S. */
    private static int charCountInString(char c, String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        return new XComparator();
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        return new CharComparator(c);
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        return new CharListComparator(chars);
    }
}
