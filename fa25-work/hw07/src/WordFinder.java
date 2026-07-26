import edu.princeton.cs.algs4.In;

import java.util.Comparator;
import java.util.List;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        if (strings.length == 0) {
            return null;
        }
        String theMax = strings[0];
        for (String s : strings) {
            if (c.compare(s, theMax) > 0) {
                theMax = s;
            }
        }
        return theMax;
    }

    private static class VowelComparator implements Comparator<String> {

        /**
         * Compares based on the number of lower case vowels.
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
            Comparator<String> cmp = WordComparators.getCharListComparator(List.of('a', 'e', 'i', 'o', 'u'));
            return cmp.compare(o1, o2);
        }
    }

    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        Comparator<String> vowelComparator = new VowelComparator();
        System.out.println(findMax(words, vowelComparator));

        String[] zebraWords = ParseUtils.fetchWords("https://en.wikipedia.org/wiki/zebra");
        System.out.println(findMax(zebraWords, vowelComparator));
    }
}
