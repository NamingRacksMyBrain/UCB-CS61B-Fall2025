public class ArrayExercises {
    /** Returns the second to last item in the given array.
     *  Assumes the array has at least 2 elements. */
    public static String secondToLastItem(String[] items) {
        return items[items.length - 2];
    }

    /** Returns the difference between the minimum and maximum item in the given array */
    public static int minMaxDifference(int[] items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int minimum = items[0];
        int maximum = items[0];

        for (int i : items) {
            if (i > maximum) {
                maximum = i;
            }
            if (i < minimum) {
                minimum = i;
            }
        }

        return maximum - minimum;
    }
}
