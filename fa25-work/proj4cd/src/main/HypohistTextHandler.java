package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.Set;

public class HypohistTextHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HypohistTextHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = HyponymsHandler.handleReturnSet(q, wn, ngm);
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();

        for (String word : words) {
            response.append(word).append(": ");

            TimeSeries wordWeight = ngm.weightHistory(word, startYear, endYear);

            response.append(wordWeight.toString());
            response.append("\n");
        }

        return response.toString();
    }
}