package main;

import java.util.*;

public class Graph {

    private final List<List<Integer>> adjList;
    private final List<List<Integer>> reverseAdjList;
    private final int size;

    public Graph(int V) {
        adjList = new ArrayList<>(V);
        reverseAdjList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
            reverseAdjList.add(new ArrayList<>());
        }
        this.size = V;
    }

    public void addEdge(int v, int w) {
        adjList.get(v).add(w);
        reverseAdjList.get(w).add(v);
    }

    public List<Integer> adj(int v) {
        return adjList.get(v);
    }

    public List<Integer> reverseAdj(int v) {
        return reverseAdjList.get(v);
    }

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

    public List<Integer> shortestPath(Set<Integer> starts, Set<Integer> targets) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> edgeTo = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int s : starts) {
            queue.add(s);
            visited.add(s);
        }

        int foundTarget = -1;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (targets.contains(curr)) {
                foundTarget = curr;
                break;
            }

            List<Integer> neighbors = new ArrayList<>(adjList.get(curr));
            neighbors.addAll(reverseAdjList.get(curr));

            for (int next : neighbors) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    edgeTo.put(next, curr);
                    queue.add(next);
                }
            }
        }

        if (foundTarget == -1) {
            return Collections.emptyList();
        }

        List<Integer> path = new ArrayList<>();
        int curr = foundTarget;
        while (!starts.contains(curr)) {
            path.add(curr);
            curr = edgeTo.get(curr);
        }
        path.add(curr);
        Collections.reverse(path);
        return path;
    }
}