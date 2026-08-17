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

    private final Map<String, List<Integer>> wordMap = new HashMap<>(); // 1 word -> fake indices
    private final Graph wordGraph; // graph of all fake indices
    private final Map<Integer, Node> nodeMap = new HashMap<>(); // fake index -> node that contains synset
    private final Map<Integer, Integer> realToFake = new HashMap<>(); // real index -> fake index

    /**
     * Constructs a WordNet from SYNSETSFILENAME and HYPONYMSFILENAME.
     */
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
}