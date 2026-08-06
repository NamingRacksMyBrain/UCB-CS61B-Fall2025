package main;

import edu.princeton.cs.algs4.In;
import java.util.Collection;
import java.util.HashMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private TimeSeries yearHistory = new TimeSeries();
    private HashMap<String, TimeSeries> wordHistory = new HashMap<>();

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        In inYearHistory = new In(yearHistoryFilename);
        while (!inYearHistory.isEmpty()) {
            String nextLine = inYearHistory.readLine();
            String[] splitLine = nextLine.split(",");
            yearHistory.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }

        In inWordHistory = new In(wordHistoryFilename);
        while (!inWordHistory.isEmpty()) {
            String nextLine = inWordHistory.readLine();
            String[] splitLine = nextLine.split("\t");
            TimeSeries theWord;
            if (!wordHistory.containsKey(splitLine[0])) {
                theWord = new TimeSeries();
            } else {
                theWord = wordHistory.get(splitLine[0]);
            }
            theWord.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
            wordHistory.put(splitLine[0], theWord);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordHistory.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordHistory.get(word), TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearHistory, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries theWord = new TimeSeries(wordHistory.get(word), startYear, endYear);
        TimeSeries theYear = new TimeSeries(yearHistory, startYear, endYear);
        return theWord.dividedBy(theYear);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries theWord = new TimeSeries(wordHistory.get(word), TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
        return theWord.dividedBy(yearHistory);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            if (wordHistory.containsKey(word)) {
                TimeSeries theWord = new TimeSeries(wordHistory.get(word), startYear, endYear);
                sum = sum.plus(theWord);
            }
        }
        TimeSeries theYear = new TimeSeries(yearHistory, startYear, endYear);
        return sum.dividedBy(theYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            if (wordHistory.containsKey(word)) {
                sum.plus(wordHistory.get(word));
            }
        }
        return sum.dividedBy(yearHistory);
    }
}
