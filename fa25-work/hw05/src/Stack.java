public class Stack {
    /** Your Stack should use a linked list with a sentinel node.
     Your push, pop, and size operations should be very fast,
     i.e. the runtime of these methods should not get longer as the Stack grows.
     There is no restriction on the runtime for sum.
     You may assume that the stack is never empty when pop or size is called.*/
    private class IntNode {
        int item;
        IntNode next;

        IntNode(int i, IntNode n) {
            this.item = i;
            this.next = n;
        }
    }

    private int size;
    private int sum;
    private IntNode sentinel;

    public Stack() {
        sentinel = new IntNode(404, null);
        size = 0;
        sum = 0;
    }

    public Stack(int x) {
        sentinel = new IntNode(404, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
        sum = x;
    }

    // puts x on top of the stack
    public void push(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        sum += x;
        size++;
    }

    // removes and returns the top item on the stack
    public int pop() {
        int top = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size--;
        sum -= top;
        return top;
    }

    // returns the number of items on the stack
    public int sum() {
        return sum;
    }

    // computes the sum of the numbers on the stack
    public int size() {
        return size;
    }
}
