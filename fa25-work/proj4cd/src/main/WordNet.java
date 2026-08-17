package main;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class WordNet {

    private class Node {
        private int realIndex;
        private String[] synset;

        Node(int i, String[] synset) {
            this.realIndex = i;
            this.synset = synset;
        }
    }

    private final Map<String, List<Integer>> wordMap = new HashMap<>();
    private final Graph wordGraph;
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private final Map<Integer, Integer> realToFake = new HashMap<>();

    public WordNet(String synsetsFileName, String hyponymsFileName) {
        int count = 0;

        In inSynsets = new In(synsetsFileName);
        while (!inSynsets.isEmpty()) {
            String nextLine = inSynsets.readLine();
            String[] splitLine = nextLine.split(",");

            String[] synonyms = splitLine[1].split(" ");
            int realIndex = Integer.parseInt(splitLine[0]);
            Node theNode = new Node(realIndex, synonyms);

            nodeMap.put(count, theNode);
            realToFake.put(realIndex, count);

            for (String word : synonyms) {
                if (!wordMap.containsKey(word)) {
                    List<Integer> indices = new ArrayList<>();
                    indices.add(count);
                    wordMap.put(word, indices);
                } else {
                    wordMap.get(word).add(count);
                }
            }
            count++;
        }

        wordGraph = new Graph(count);

        In inHyponyms = new In(hyponymsFileName);
        while (!inHyponyms.isEmpty()) {
            String nextLine = inHyponyms.readLine();
            String[] splitLine = nextLine.split(",");

            for (int i = 1; i < splitLine.length; i++) {
                wordGraph.addEdge(realToFake.get(Integer.parseInt(splitLine[0])), realToFake.get(Integer.parseInt(splitLine[i])));
            }
        }
    }

    public Set<String> hyponyms(String word) {
        if (!wordMap.containsKey(word)) {
            return new TreeSet<>();
        }
        Set<Integer> reachable = new TreeSet<>();
        for (int i : wordMap.get(word)) {
            reachable.addAll(wordGraph.reachable(i));
        }
        Set<String> hyponyms = new TreeSet<>();
        for (int j : reachable) {
            hyponyms.addAll(Arrays.asList(nodeMap.get(j).synset));
        }
        return hyponyms;
    }

    public Set<String> ancestors(String word) {
        if (!wordMap.containsKey(word)) {
            return new TreeSet<>();
        }
        Set<Integer> reachable = new TreeSet<>();
        for (int i : wordMap.get(word)) {
            reachable.addAll(wordGraph.reverseReachable(i));
        }
        Set<String> ancestors = new TreeSet<>();
        for (int j : reachable) {
            ancestors.addAll(Arrays.asList(nodeMap.get(j).synset));
        }
        return ancestors;
    }

    public String shortestPath(String word1, String word2) {
        if (!wordMap.containsKey(word1) || !wordMap.containsKey(word2)) {
            return "One or both words not found in WordNet.";
        }
        Set<Integer> starts = new HashSet<>(wordMap.get(word1));
        Set<Integer> targets = new HashSet<>(wordMap.get(word2));

        List<Integer> path = wordGraph.shortestPath(starts, targets);
        if (path.isEmpty()) {
            return "No semantic path found between " + word1 + " and " + word2;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Shortest Path (Distance = ").append(path.size() - 1).append("):\n\n");
        for (int i = 0; i < path.size(); i++) {
            sb.append("[").append(String.join(", ", nodeMap.get(path.get(i)).synset)).append("]");
            if (i < path.size() - 1) {
                sb.append("\n  ↕\n");
            }
        }
        return sb.toString();
    }
}