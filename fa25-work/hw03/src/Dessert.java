public class Dessert {
    private static int numDesserts = 0;

    private int flavor;
    private int price;

    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        numDesserts++;
    }

    /** Prints the flavor and price of the dessert, along with the total number of
     * desserts created so far, separated by a space. */
    public void printDessert() {
        System.out.println(this.flavor + " " + this.price + " " + Dessert.numDesserts);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}
