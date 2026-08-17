package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.*;

public class TrendingHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public TrendingHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    private static class WordTrend implements Comparable<WordTrend> {
        String word;
        double startWeight;
        double endWeight;
        double delta;

        WordTrend(String word, double startWeight, double endWeight) {
            this.word = word;
            this.startWeight = startWeight;
            this.endWeight = endWeight;
            this.delta = endWeight - startWeight;
        }

        @Override
        public int compareTo(WordTrend o) {
            return Double.compare(o.delta, this.delta);
        }
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        if (words.isEmpty()) {
            return "Please enter a category word (e.g. vehicle, food).";
        }

        Set<String> hyponyms = new HashSet<>();
        for (String w : words) {
            hyponyms.addAll(wn.hyponyms(w));
        }

        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k() == 0 ? 5 : q.k();

        List<WordTrend> trends = new ArrayList<>();
        for (String word : hyponyms) {
            TimeSeries wHistory = ngm.weightHistory(word, startYear, endYear);
            double startW = wHistory.getOrDefault(startYear, 0.0);
            double endW = wHistory.getOrDefault(endYear, 0.0);

            if (startW > 0 || endW > 0) {
                trends.add(new WordTrend(word, startW, endW));
            }
        }

        if (trends.isEmpty()) {
            return "No frequency data available for hyponyms of " + words + " in the selected time range.";
        }

        Collections.sort(trends);

        StringBuilder sb = new StringBuilder();
        sb.append("===== 🚀 TRENDING / RISING WORDS (Top ").append(Math.min(k, trends.size())).append(") =====\n");
        sb.append(String.format("%-20s | %-12s | %-12s | %-12s\n", "Word", "Weight(" + startYear + ")", "Weight(" + endYear + ")", "Net Change"));
        sb.append("-------------------------------------------------------------------\n");
        for (int i = 0; i < Math.min(k, trends.size()); i++) {
            WordTrend t = trends.get(i);
            sb.append(String.format("%-20s | %-12.3e | %-12.3e | %+.3e\n", t.word, t.startWeight, t.endWeight, t.delta));
        }

        sb.append("\n===== 📉 DECLINING / EXTINCT WORDS (Bottom ").append(Math.min(k, trends.size())).append(") =====\n");
        sb.append(String.format("%-20s | %-12s | %-12s | %-12s\n", "Word", "Weight(" + startYear + ")", "Weight(" + endYear + ")", "Net Change"));
        sb.append("-------------------------------------------------------------------\n");
        for (int i = Math.max(0, trends.size() - k); i < trends.size(); i++) {
            WordTrend t = trends.get(i);
            sb.append(String.format("%-20s | %-12.3e | %-12.3e | %+.3e\n", t.word, t.startWeight, t.endWeight, t.delta));
        }

        return sb.toString();
    }
}