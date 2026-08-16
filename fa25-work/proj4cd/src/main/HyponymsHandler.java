package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;

    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<Set<String>> multiSets = new ArrayList<>();

        for (String word : words) {
            multiSets.add(wn.hyponyms(word));
        }

        return SetIntersection.intersect(multiSets).toString();
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
}
