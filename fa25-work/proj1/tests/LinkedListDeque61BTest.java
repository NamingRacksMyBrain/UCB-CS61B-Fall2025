import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         assertThat(lld1.toList()).containsExactly("front").inOrder();
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

     // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    /** This test verifies that isEmpty works correctly. */
    public void isEmptyTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addLast(0);   // [0]
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    /** This test verifies that size works correctly. */
    public void sizeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addLast(0);   // [0]
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addLast(1);   // [0, 1]
        assertThat(lld1.size()).isEqualTo(2);

        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.size()).isEqualTo(3);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(2);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);

        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    /** This test verifies that get works correctly. */
    public void getTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.get(1)).isEqualTo(null);
        assertThat(lld1.get(-1)).isEqualTo(null);

        lld1.addLast(0);   // [0]
        assertThat(lld1.get(0)).isEqualTo(0);
        assertThat(lld1.get(1)).isEqualTo(null);
        assertThat(lld1.get(-17)).isEqualTo(null);

        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.get(0)).isEqualTo(-1);
        assertThat(lld1.get(1)).isEqualTo(0);
        assertThat(lld1.get(2)).isEqualTo(1);
        assertThat(lld1.get(-12)).isEqualTo(null);
        assertThat(lld1.get(3)).isEqualTo(null);
    }

    @Test
    /** This test verifies that getRecursive works correctly. */
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(1)).isEqualTo(null);
        assertThat(lld1.getRecursive(-1)).isEqualTo(null);

        lld1.addLast(0);   // [0]
        assertThat(lld1.getRecursive(0)).isEqualTo(0);
        assertThat(lld1.getRecursive(1)).isEqualTo(null);
        assertThat(lld1.getRecursive(-17)).isEqualTo(null);

        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.getRecursive(0)).isEqualTo(-1);
        assertThat(lld1.getRecursive(1)).isEqualTo(0);
        assertThat(lld1.getRecursive(2)).isEqualTo(1);
        assertThat(lld1.getRecursive(-12)).isEqualTo(null);
        assertThat(lld1.getRecursive(3)).isEqualTo(null);
    }

    @Test
    /** This test verifies that removeFirst works correctly. */
    public void removeFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        assertThat(lld1.removeFirst()).isEqualTo("front");
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test verifies that removeLast works correctly. */
    public void removeLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        assertThat(lld1.removeLast()).isEqualTo("back");
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("front").inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test performs interspersed removeFirst and removeLast calls. */
    public void removeFirstAndRemoveLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        lld1.removeLast();   // [-2, -1, 0, 1]
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1).inOrder();
        lld1.removeLast();   // [-2, -1, 0]
        assertThat(lld1.toList()).containsExactly(-2, -1, 0).inOrder();
        lld1.removeFirst();  // [-1, 0]
        assertThat(lld1.toList()).containsExactly(-1, 0).inOrder();
        lld1.removeLast();   // [-1]
        assertThat(lld1.toList()).containsExactly(-1).inOrder();
        lld1.removeFirst();  // []
        assertThat(lld1.toList()).containsExactly().inOrder();
    }

    @Test
    /** This test verifies that add after remove to empty works correctly. */
    public void addAfterRemoveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        lld1.removeLast();   // [-2, -1, 0, 1]
        lld1.removeLast();   // [-2, -1, 0]
        lld1.removeFirst();  // [-1, 0]
        lld1.removeFirst();   // [0]
        assertThat(lld1.toList()).containsExactly(0).inOrder();
        lld1.removeFirst();  // []

        lld1.addFirst(17);
        assertThat(lld1.toList()).containsExactly(17).inOrder();

        assertThat(lld1.removeLast()).isEqualTo(17);
        assertThat(lld1.toList()).containsExactly().inOrder();

        lld1.addLast(7);
        assertThat(lld1.toList()).containsExactly(7).inOrder();
    }
}