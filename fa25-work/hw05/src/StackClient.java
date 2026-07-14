public class StackClient {
    // Returns a version of the stack that is flipped
    public static Stack flipped(Stack s) {
        Stack flippedS = new Stack();
        int size = s.size();
        for (int i = 0; i < size; i++) {
            flippedS.push(s.pop());
        }
        return flippedS;
    }
}
