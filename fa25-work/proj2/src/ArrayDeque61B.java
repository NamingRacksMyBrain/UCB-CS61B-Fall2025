import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resizeUp();
        }
        items[nextFirst] = x;
        size++;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resizeUp();
        }
        items[nextLast] = x;
        size++;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(this.get(i));
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        double usageFactor = (double) size / items.length;
        if (items.length >= 16 && usageFactor <= 0.25) {
            resizeDown();
        }

        T removedItem = get(0);
        size--;

        nextFirst = (nextFirst + 1) % items.length;
        items[nextFirst] = null;

        return removedItem;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        double usageFactor = (double) size / items.length;
        if (items.length >= 16 && usageFactor <= 0.25) {
            resizeDown();
        }

        T removedItem = get(size - 1);
        size--;

        nextLast = (nextLast - 1 + items.length) % items.length;
        items[nextLast] = null;

        return removedItem;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index < 0 || index >= size) {
            return null;
        }

        return items[(nextFirst + 1 + index) % items.length];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    /**
     * This method resizes up by a geometric factor when the array fills.
     */
    private void resizeUp() {
        int newLength = items.length * 2;
        T[] newItems = (T[]) new Object[newLength];

        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        nextFirst = newLength - 1;
        nextLast = size;
        items = newItems;
    }

    /**
     * This method resizes down by a geometric factor when the array is sparse.
     * For arrays of length 16 or more, your usage factor should always be at least 25%.
     * This means that before performing a remove operation, if the number of elements
     * in the array is at or under 25% the length of the array, you should resize the array down.
     */
    private void resizeDown() {
        int newLength = items.length / 2;
        while (newLength >= 16 && (double) size / newLength <= 0.25) {
            newLength /= 2;
        }

        T[] newItems = (T[]) new Object[newLength];

        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        nextFirst = newLength - 1;
        nextLast = size;
        items = newItems;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Deque61B<?> mySet) {
            if (size != mySet.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(mySet.get(i))) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * I write the proj2 part that involves iterators/Object methods
     * on the first day of the summer session at JiaDing. I name my iterator
     * class to express the strong feeling: I miss DUODUO!
     */
    private class IMissDuoIterator implements Iterator<T> {
        int duoPos;

        IMissDuoIterator() {
            duoPos = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return duoPos < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T nextItem = get(duoPos);
            duoPos++;
            return nextItem;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new IMissDuoIterator();
    }

    /**
     * How to add .iterator() method to ArrayDeque61B class?
     * --------------------------------------------------------------------------
     * Step1: In Deque61B, change
     * public interface Deque61B<T>
     *      to
     * public interface Deque61B<T> extends Iterable<T>
     *
     * because we want to guarantee that EVERY type of Deque we ever build
     * can be iterated over using a beautiful, enhanced for-loop!
     *
     * After this step, your ArrayDeque61B class will have an .iterator() method
     * --------------------------------------------------------------------------
     * Step2: Create a new class.
     * Mine here: private class IMissDuoIterator implements Iterator<T>
     *
     * which has 1 variable: duoPos
     * and two methods:     .hasNext()
     *                      .next()
     * --------------------------------------------------------------------------
     * Step3: Implement the .iterator() method in ArrayDeque61B class,
     * which returns a new IMissDuoIterator instance.
     *
     * Note:
     * manipulate hasNext() and next() is not our job! the for loop
     * will take care of that!
     */
}
