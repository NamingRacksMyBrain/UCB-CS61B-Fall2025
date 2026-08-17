package main;

import java.util.*;

public class Graph {

    private final List<List<Integer>> adjList;
    private final List<List<Integer>> reverseAdjList;
    private final int size;

    // Create empty graph with v vertices
    public Graph(int V) {
        adjList = new ArrayList<>(V);
        reverseAdjList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
            reverseAdjList.add(new ArrayList<>());
        }
        this.size = V;
    }

    // Add an edge v -> w
    public void addEdge(int v, int w) {
        adjList.get(v).add(w);
        reverseAdjList.get(w).add(v);
    }

    // Vertices adjacent to v
    public List<Integer> adj(int v) {
        return adjList.get(v);
    }

    public List<Integer> reverseAdj(int v) {
        return reverseAdjList.get(v);
    }

    // Reachable nodes of V using DFS (Hyponyms)
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
                reachable.addAll(reachable(w, marked));
            }
        }

        return reachable;
    }

    // Reverse reachable nodes of V using DFS (Hypernyms / Ancestors)
    public Set<Integer> reverseReachable(int v) {
        boolean[] marked = new boolean[this.size];
        return reverseReachable(v, marked);
    }

    private Set<Integer> reverseReachable(int v, boolean[] marked) {
        Set<Integer> ancestors = new TreeSet<>();

        ancestors.add(v);
        marked[v] = true;

        for (int w : reverseAdj(v)) {
            if (!marked[w]) {
                ancestors.addAll(reverseReachable(w, marked));
            }
        }

        return ancestors;
    }
}