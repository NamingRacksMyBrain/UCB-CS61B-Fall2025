package main;

import java.util.*;

public class Graph {

    List<List<Integer>> adjList;
    private final int size;

    // Create empty graph with v vertices
    public Graph(int V) {
        adjList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjList.add(i, new ArrayList<>());
        }
        this.size = V;
    }

    // Add an edge v -> w
    public void addEdge(int v, int w) {
        adjList.get(v).add(w);
    }

    // Vertices adjacent to v
    public List<Integer> adj(int v) {
        return adjList.get(v);
    }

    // Reachable nodes of V using DFS
    public Set<Integer> reachable(int v) {
        boolean[] marked = new boolean[this.size];
        return reachable(v, marked);
    }

    private Set<Integer> reachable(int v, boolean[] marked) {
        Set<Integer> reachable = new TreeSet<>();

        reachable.add(v);
        marked[v] = true;

        for (int w : adj(v)) {
            if (!marked[w]) {
                reachable.add(w);
                reachable.addAll(reachable(w, marked));
                marked[w] = true;
            }
        }

        return reachable;
    }
}
