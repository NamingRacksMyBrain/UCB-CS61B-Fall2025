import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        assertThat(ad1.toList()).containsExactly("front").inOrder();
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        ad1.addLast(0);

        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0).inOrder();
    }

    @Test
    /** This test verifies that isEmpty works correctly. */
    public void isEmptyTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.isEmpty()).isTrue();

        ad1.addLast(0);   // [0]
        assertThat(ad1.isEmpty()).isFalse();
    }

    @Test
    /** This test verifies that size works correctly. */
    public void sizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.size()).isEqualTo(0);

        ad1.addLast(0);   // [0]
        assertThat(ad1.size()).isEqualTo(1);

        ad1.addLast(1);   // [0, 1]
        assertThat(ad1.size()).isEqualTo(2);

        ad1.addFirst(-1); // [-1, 0, 1]
        assertThat(ad1.size()).isEqualTo(3);

        ad1.removeFirst();
        assertThat(ad1.size()).isEqualTo(2);

        ad1.removeFirst();
        assertThat(ad1.size()).isEqualTo(1);

        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(0);

        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(0);

        ad1.removeFirst();
        assertThat(ad1.size()).isEqualTo(0);
    }

    @Test
    /** This test verifies that get works correctly. */
    public void getTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        assertThat(ad1.get(0)).isEqualTo(null);
        assertThat(ad1.get(1)).isEqualTo(null);
        assertThat(ad1.get(-1)).isEqualTo(null);

        ad1.addLast(0);   // [0]
        assertThat(ad1.get(0)).isEqualTo(0);
        assertThat(ad1.get(1)).isEqualTo(null);
        assertThat(ad1.get(-17)).isEqualTo(null);

        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        assertThat(ad1.get(0)).isEqualTo(-1);
        assertThat(ad1.get(1)).isEqualTo(0);
        assertThat(ad1.get(2)).isEqualTo(1);
        assertThat(ad1.get(-12)).isEqualTo(null);
        assertThat(ad1.get(3)).isEqualTo(null);

        ad1.addFirst(7);  // [7, -1, 0, 1]
        ad1.addFirst(9);  // [9, 7, -1, 0, 1]
        ad1.addFirst(11); // [11, 9, 7, -1, 0, 1]
        ad1.addFirst(13); // [13, 11, 9, 7, -1, 0, 1]
        ad1.addFirst(15); // [15, 13, 11, 9, 7, -1, 0, 1]
        assertThat(ad1.get(0)).isEqualTo(15);
        assertThat(ad1.get(1)).isEqualTo(13);
        assertThat(ad1.get(2)).isEqualTo(11);
        assertThat(ad1.get(3)).isEqualTo(9);
        assertThat(ad1.get(4)).isEqualTo(7);
        assertThat(ad1.get(5)).isEqualTo(-1);
        assertThat(ad1.get(6)).isEqualTo(0);
        assertThat(ad1.get(7)).isEqualTo(1);
    }

    @Test
    /** This test verifies that removeFirst works correctly. */
    public void removeFirstTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

        assertThat(ad1.removeFirst()).isEqualTo("front");
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly().inOrder();

        ad1.addLast("front");
        assertThat(ad1.toList()).containsExactly("front").inOrder();

        ad1.removeFirst();
        ad1.addFirst("front");
        assertThat(ad1.toList()).containsExactly("front").inOrder();
    }

    @Test
    /** This test verifies that removeLast works correctly. */
    public void removeLastTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

        assertThat(ad1.removeLast()).isEqualTo("back");
        assertThat(ad1.toList()).containsExactly("front", "middle").inOrder();
        ad1.removeLast();
        assertThat(ad1.toList()).containsExactly("front").inOrder();
        ad1.removeLast();
        assertThat(ad1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test performs interspersed removeFirst and removeLast calls. */
    public void removeFirstAndRemoveLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        ad1.removeLast();   // [-2, -1, 0, 1]
        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1).inOrder();
        ad1.removeLast();   // [-2, -1, 0]
        assertThat(ad1.toList()).containsExactly(-2, -1, 0).inOrder();
        ad1.removeFirst();  // [-1, 0]
        assertThat(ad1.toList()).containsExactly(-1, 0).inOrder();
        ad1.removeLast();   // [-1]
        assertThat(ad1.toList()).containsExactly(-1).inOrder();
        ad1.removeFirst();  // []
        assertThat(ad1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test verifies that add after remove to empty works correctly. */
    public void addAfterRemoveTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        ad1.removeLast();   // [-2, -1, 0, 1]
        ad1.removeLast();   // [-2, -1, 0]
        ad1.removeFirst();  // [-1, 0]
        ad1.removeFirst();   // [0]
        assertThat(ad1.toList()).containsExactly(0).inOrder();
        ad1.removeFirst();  // []

        ad1.addFirst(17);
        assertThat(ad1.toList()).containsExactly(17).inOrder();

        assertThat(ad1.removeLast()).isEqualTo(17);
        assertThat(ad1.toList()).containsExactly().inOrder();

        ad1.addLast(7);
        assertThat(ad1.toList()).containsExactly(7).inOrder();
    }

    @Test
    /** This test verifies that resize works correctly. */
    public void resizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        for (int i = 0; i < 35; i++) {
            ad1.addLast(i);
        }
        for (int i = 0; i < 35; i++) {
            ad1.removeLast();
        }
        for (int i = 0; i < 35; i++) {
            ad1.addFirst(i);
        }
        for (int i = 0; i < 35; i++) {
            ad1.removeFirst();
        }
    }

    @Test
    /** This test verifies that iterator works correctly. */
    public void iteratorTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addLast(3);

        List<Integer> result = new ArrayList<>();
        for (int x : ad) {
            result.add(x);
        }

        assertThat(result).containsExactly(2, 1, 3).inOrder();
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad).isEqualTo(ad2);

        ad.addLast("back");
        assertThat(ad).isNotEqualTo(ad2);
    }

    @Test
    public void toStringTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }

    // Below are tests generated by gemini
    @Test
    public void iteratorPhysicalVsConceptualTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addFirst(1);
        ad.addFirst(2);
        ad.addLast(3);

        // Conceptual [2, 1, 3]
        // Physical   [3, null, null, null, null, null, 2, 1]

        java.util.List<Integer> result = new java.util.ArrayList<>();
        for (int x : ad) {
            result.add(x);
        }

        assertThat(result).containsExactly(2, 1, 3).inOrder();
    }

    @Test
    public void equalsReferenceVsValueTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        String oski1 = new String("Oski");
        String oski2 = new String("Oski");

        ad1.addLast(oski1);
        ad2.addLast(oski2);

        assertThat(ad1).isEqualTo(ad2);
    }

    @Test
    public void resizeDownBoundaryTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        for (int i = 0; i < 16; i++) {
            ad.addLast(i);
        }

        for (int i = 0; i < 12; i++) {
            ad.removeLast();
        }

        assertThat(ad.size()).isEqualTo(4);
        assertThat(ad.toList()).containsExactly(0, 1, 2, 3).inOrder();

        ad.removeLast();
        assertThat(ad.size()).isEqualTo(3);
        assertThat(ad.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void resizeUpAndResizeDownTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Add 1000 elements to trigger multiple resizeUp calls
        for (int i = 0; i < 1000; i++) {
            ad.addLast(i);
        }

        // Remove 999 elements to trigger multiple resizeDown calls
        for (int i = 0; i < 999; i++) {
            ad.removeFirst();
        }

        // Only one element should be left, which is 999
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.get(0)).isEqualTo(999);
    }
}
