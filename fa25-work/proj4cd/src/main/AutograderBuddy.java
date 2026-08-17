package main;

import browser.NgordnetQueryHandler;
import browser.NgordnetServer;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {

        WordNet wn = new WordNet(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordHistoryFile, yearHistoryFile);

        return new HyponymsHandler(wn, ngm);
    }
}
