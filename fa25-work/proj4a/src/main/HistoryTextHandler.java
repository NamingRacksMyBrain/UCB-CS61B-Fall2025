package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    // Optimized by using StringBuilder. Implemented by gemini
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();

        for (String word : words) {
            response.append(word).append(": ");

            TimeSeries wordWeight = map.weightHistory(word, startYear, endYear);

            response.append(wordWeight.toString());
            response.append("\n");
        }

        return response.toString();
    }
}
