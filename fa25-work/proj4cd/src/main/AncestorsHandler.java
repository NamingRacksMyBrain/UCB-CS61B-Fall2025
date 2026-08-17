package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class AncestorsHandler extends NgordnetQueryHandler {
    private WordNet wn;

    public AncestorsHandler(WordNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<Set<String>> multiSets = new ArrayList<>();

        for (String word : words) {
            multiSets.add(wn.ancestors(word));
        }

        Set<String> intersection = SetIntersection.intersect(multiSets);
        return intersection.toString();
    }

    private static class SetIntersection {
        public static <T> Set<T> intersect(Collection<Set<T>> sets) {
            if (sets == null || sets.isEmpty()) {
                return new TreeSet<>();
            }

            Iterator<Set<T>> iterator = sets.iterator();
            Set<T> result = new TreeSet<>(iterator.next());

            while (iterator.hasNext()) {
                result.retainAll(iterator.next());
                if (result.isEmpty()) {
                    break;
                }
            }
            return result;
        }
    }
}