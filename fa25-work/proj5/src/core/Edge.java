package core;

public class Edge implements Comparable<Edge> {
    private Room a;
    private Room b;
    private int length;

    public Edge(Room a, Room b) {
        this.a = a;
        this.b = b;
        this.length = a.distanceTo(b);
    }

    @Override
    public int compareTo(Edge o) {
        return length - o.length;
    }

    public Room a() {
        return a;
    }

    public Room b() {
        return b;
    }
}
