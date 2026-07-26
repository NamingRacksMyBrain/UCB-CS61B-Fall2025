import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.List;

public class Star implements Comparable<Star> {
    private final String name;
    private final double distanceLy;
    private final double massMsun;
    private final double mV;

    public Star(String name, double distanceLy, double massMsun, double mV) {
        this.name = name;
        this.distanceLy = distanceLy;
        this.massMsun = massMsun;
        this.mV = mV;
    }

    @Override
    public String toString() {
        return String.format("Star{name='%s', distanceLy=%.6f, massMsun=%s, mV=%.2f}",
                name, distanceLy, String.format("%.3f", massMsun), mV);
    }

    // You do not need to modify anything above this line
    // (except the "public class Star" line).

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(Star o) {
        if (massMsun < o.massMsun) {
            return -1;
        } else if (massMsun > o.massMsun) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        In in = new In("data/stars20.txt");
        List<Star> stars = ParseUtils.readCsv(in);

        System.out.println(Collections.max(stars));
        // System.out.println("Loaded " + stars.size() + " stars.");
        // int show = 5;
        // for (int i = 0; i < show; i += 1) {
        //     System.out.println(stars.get(i));
        // }
    }
}
