package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {


    private class Node {
        private int index;
        private String[] synset;
    }

    private final Map<String, List<Integer>> wordMap = new HashMap<>(); // 1 word -> indices of synsets that contain it
    private final Graph wordGraph; // graph of all indices
    private final Map<Integer, String[]> synsetMap = new HashMap<>(); // 1 to 1 mapping: index -> synset

    /**
     * Constructs a WordNet from SYNSETSFILENAME and HYPONYMSFILENAME.
     */
    public WordNet(String synsetsFileName, String hyponymsFileName) {
        int count = 0;

        In inSynsets = new In(synsetsFileName);
        while (!inSynsets.isEmpty()) {
            String nextLine = inSynsets.readLine();
            String[] splitLine = nextLine.split(",");

            int index = Integer.parseInt(splitLine[0]);
            String[] synonyms = splitLine[1].split(" ");

            synsetMap.put(index, synonyms);

            for (String word : synonyms) {
                if (!wordMap.containsKey(word)) {
                    List<Integer> indices = new ArrayList<>();
                    indices.add(index);
                    wordMap.put(word, indices);
                } else {
                    wordMap.get(word).add(index);
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
                wordGraph.addEdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[i]));
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
            hyponyms.addAll(Arrays.asList(synsetMap.get(j)));
        }
        return hyponyms; // TreeSet is supposed to be sorted naturally
    }
}
