package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.List;

public class ShortestPathHandler extends NgordnetQueryHandler {
    private WordNet wn;

    public ShortestPathHandler(WordNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        if (words.size() < 2) {
            return "Please provide at least two words separated by comma (e.g. cat, submarine).";
        }
        return wn.shortestPath(words.get(0).trim(), words.get(1).trim());
    }
}