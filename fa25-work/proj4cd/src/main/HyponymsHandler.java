package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        return handleReturnSet(q, this.wn, this.ngm).toString();
    }

    public static Set<String> handleReturnSet(NgordnetQuery q, WordNet wn, NGramMap ngm) {
        List<String> words = q.words();

        List<Set<String>> multiSets = new ArrayList<>();
        for (String word : words) {
            multiSets.add(wn.hyponyms(word));
        }
        Set<String> intersection = SetIntersection.intersect(multiSets);

        if (q.k() == 0) {
            return intersection;
        } else {
            int startYear = q.startYear();
            int endYear = q.endYear();

            PriorityQueue<wordNode> maxHeap = new PriorityQueue<>();

            for (String word : intersection) {
                TreeMap<Integer, Double> wordHistory = ngm.countHistory(word, startYear, endYear);
                if (!wordHistory.isEmpty()) {
                    double sum = wordHistory.values().stream()
                            .mapToDouble(Double::doubleValue)
                            .sum();
                    if (sum > 0) {
                        maxHeap.add(new wordNode(word, sum));
                    }

                    if (maxHeap.size() > q.k()) {
                        maxHeap.poll();
                    }
                }
            }
            Set<String> topWords = new TreeSet<>();
            for (wordNode n : maxHeap) {
                topWords.add(n.word);
            }
            return topWords;
        }
    }

    // Intersection of multi-sets. From stackOverFlow
    private static class SetIntersection {
        public static <T> Set<T> intersect(Collection<Set<T>> sets) {
            if (sets == null || sets.isEmpty()) {
                return new TreeSet<>();
            }

            Iterator<Set<T>> iterator = sets.iterator();
            // Seed the result with a copy of the first set to avoid mutating the original
            Set<T> result = new TreeSet<>(iterator.next());

            // Intersect with every remaining set
            while (iterator.hasNext()) {
                result.retainAll(iterator.next());
                // Optimization: If the intersection becomes empty, stop early
                if (result.isEmpty()) {
                    break;
                }
            }
            return result;
        }
    }

    private static class wordNode implements Comparable<wordNode> {
        String word;
        double count;

        wordNode(String word, double count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public int compareTo(wordNode o) {
            if (this.count > o.count) {
                return 1;
            } else if (this.count < o.count) {
                return -1;
            } else {
                return o.word.compareTo(this.word);
            }
        }
    }
}
