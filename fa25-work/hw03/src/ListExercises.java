import java.util.ArrayList;
import java.util.List;

public class ListExercises {
    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int sum = 0;
        for (Integer i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evenL = new ArrayList<>();
        for (Integer i : L) {
            if (i % 2 == 0) {
                evenL.add(i);
            }
        }
        return evenL;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> common = new ArrayList<>();
        for (Integer i : L1) {
            if (L2.contains(i)) {
                common.add(i);
            }
        }
        return common;
    }

    public static int countOccurrencesOfWord(List<String> words, String w) {
        int occurences = 0;
        for (String word : words) {
            if (word == w) {
                occurences++;
            }
        }
        return occurences;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int occurences = 0;
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c) {
                    occurences++;
                }
            }
        }
        return occurences;
    }
}
