package main;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class NGramMap {

    private TimeSeries yearHistory = new TimeSeries();
    private HashMap<String, TimeSeries> wordHistory = new HashMap<>();

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

    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordHistory.get(word), startYear, endYear);
    }

    public TimeSeries countHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordHistory.get(word), TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearHistory, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries theWord = new TimeSeries(wordHistory.get(word), startYear, endYear);
        TimeSeries theYear = new TimeSeries(yearHistory, startYear, endYear);
        return theWord.dividedBy(theYear);
    }

    public TimeSeries weightHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries theWord = new TimeSeries(wordHistory.get(word), TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
        return theWord.dividedBy(yearHistory);
    }

    public TimeSeries averageWordLengthHistory(int startYear, int endYear) {
        TimeSeries avgLengths = new TimeSeries();
        for (int y = startYear; y <= endYear; y++) {
            if (!yearHistory.containsKey(y) || yearHistory.get(y) == 0.0) {
                continue;
            }
            double totalChars = 0.0;
            double totalWords = 0.0;
            for (Map.Entry<String, TimeSeries> entry : wordHistory.entrySet()) {
                TimeSeries ts = entry.getValue();
                if (ts.containsKey(y)) {
                    double count = ts.get(y);
                    totalChars += count * entry.getKey().length();
                    totalWords += count;
                }
            }
            if (totalWords > 0) {
                avgLengths.put(y, totalChars / totalWords);
            }
        }
        return avgLengths;
    }

    public List<Double> getSortedCountsForYear(int year) {
        List<Double> counts = new ArrayList<>();
        for (TimeSeries ts : wordHistory.values()) {
            if (ts.containsKey(year) && ts.get(year) > 0) {
                counts.add(ts.get(year));
            }
        }
        counts.sort(Collections.reverseOrder());
        return counts;
    }
}