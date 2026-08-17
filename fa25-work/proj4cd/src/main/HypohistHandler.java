package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.Set;

public class HypohistHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;

    public HypohistHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        Set<String> words = HyponymsHandler.handleReturnSet(q, wn, ngm);
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        if (q.k() == 0 && words.size() > 50) {
            return "Too many words to plot. Please specify a k > 0.";
        }

        for (String word : words) {
            TimeSeries theWord = ngm.weightHistory(word, startYear, endYear);
            if (!theWord.isEmpty()) {
                labels.add(word);
                lts.add(theWord);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
