package main;

import browser.NgordnetServer;
import org.slf4j.LoggerFactory;

public class Main {
    private static final String PREFIX = "./data/";

    public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
    public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";
    public static final String SYNSETS_SIZE82191_FILE = PREFIX + "synsets_size82191.txt";
    public static final String HYPONYMS_SIZE82191_FILE = PREFIX + "hyponyms_size82191.txt";

    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }

    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        WordNet wn = new WordNet(SYNSETS_SIZE82191_FILE, HYPONYMS_SIZE82191_FILE);
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE);

        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(wn, ngm));
        hns.register("hypohist", new HypohistHandler(wn, ngm));
        hns.register("hypohisttext", new HypohistTextHandler(wn, ngm));
        hns.register("ancestors", new AncestorsHandler(wn));

        hns.register("shortestpath", new ShortestPathHandler(wn));
        hns.register("zipf", new ZipfHandler(ngm));
        hns.register("wordlength", new AvgLengthHandler(ngm));
        hns.register("trending", new TrendingHandler(wn, ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}