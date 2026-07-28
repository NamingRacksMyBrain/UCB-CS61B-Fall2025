import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static java.lang.Math.*;

/**
 *  Model of a percolation system using Disjoint Sets.
 *
 *  --- THE BACKWASH PROBLEM & SOLUTION ---
 *  Original Flawed Approach:
 *  Previously, I attempted to solve the "backwash" issue (where empty sites at the
 *  bottom falsely appear full after the system percolates) by conditionally preventing
 *  connections to the virtualBottom if the system had already percolated (!percolates()).
 *  However, this failed for bottom sites opened BEFORE percolation. Once percolation
 *  occurred elsewhere, those early bottom sites became transitively connected to the
 *  virtualTop via the virtualBottom, causing backwash anyway.
 *
 *  New Solution (Two Union-Finds):
 *  To completely prevent backwash, we use TWO WeightedQuickUnionUF objects:
 *  1. fullSites: Contains BOTH virtualTop and virtualBottom.
 *     Used strictly to check if the system percolates().
 *  2. withoutBottomSites: Contains ONLY virtualTop (no virtualBottom).
 *     Used strictly to check if a site isFull(). Since there is no virtualBottom
 *     acting as a "backdoor" connection, water cannot flow backwards from the bottom!
 */
public class Percolation {
    private int R;
    private int C;
    private int numberOfOpenSites;
    private int virtualTop;
    private int virtualBottom;
    private int[] openSites; // 0 for unopen and 1 for open
    private WeightedQuickUnionUF fullSites;
    private WeightedQuickUnionUF withoutBottomSites;

    /** Create an N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.R = N;
        this.C = N;
        this.fullSites = new WeightedQuickUnionUF(R * C + 2); // extra 2 are the virtual top and bottom sites
        this.withoutBottomSites = new WeightedQuickUnionUF(R * C + 1); // extra 1 is virtualTop
        this.openSites = new int[R * C + 2];
        virtualTop = R * C;
        virtualBottom = R * C + 1;
        openSites[virtualTop] = 1;
        openSites[virtualBottom] = 1;
        this.numberOfOpenSites = 0;
    }

    /** Open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        checkIndexOutOfBound(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSites[xyTo1D(row, col)] = 1;
        numberOfOpenSites++;
        unionAdjacent(row, col);
        if (row == 0) {
            fullSites.union(xyTo1D(row, col), virtualTop);
            withoutBottomSites.union(xyTo1D(row, col), virtualTop);
        }
        if (row == R - 1) {
            fullSites.union(xyTo1D(row, col), virtualBottom);
        }
    }

    /** Returns whether the site is open */
    public boolean isOpen(int row, int col) {
        checkIndexOutOfBound(row, col);
        return openSites[xyTo1D(row, col)] == 1;
    }

    /** Returns whether the site is full */
    public boolean isFull(int row, int col) {
        checkIndexOutOfBound(row, col);
        return isOpen(row, col) && withoutBottomSites.connected(virtualTop, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /** Returns whether the system percolates */
    public boolean percolates() {
        return (fullSites.connected(virtualTop, virtualBottom));
    }

    /** Transforms the (x, y) 2-D representation of a site into a 1-D single number. */
    int xyTo1D(int x, int y) {
        return x * C + y;
    }

    private void checkIndexOutOfBound(int row, int col) {
        checkIndexOutOfBound(xyTo1D(row, col));
    }

    private void checkIndexOutOfBound(int x) {
        if (x >= R * C || x < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unionAdjacent(int row, int col) {
        for (int i = max(row - 1, 0); i <= min(row + 1, R - 1); i++) {
            for (int j = max(col - 1, 0); j <= min(col + 1, C - 1); j++) {
                if (adjacent(i, j, row, col) && isOpen(i, j)) {
                    fullSites.union(xyTo1D(row, col), xyTo1D(i, j));
                    withoutBottomSites.union(xyTo1D(row, col), xyTo1D(i, j));
                }
            }
        }
    }

    /** Returns whether two sites are adjacent in the same row or col.
     *  @param: {@code r1}, {@code c1}, {@code r2}, {@code c2} are the 2-D position of 2 sites */
    private boolean adjacent(int r1, int c1, int r2, int c2) {
        return (r1 == r2 && abs(c1 - c2) == 1) || (c1 == c2 && abs(r1 - r2) == 1);
    }
}
