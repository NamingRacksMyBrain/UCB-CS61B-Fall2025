package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class ZipfHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public ZipfHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        int year = q.startYear();
        List<Double> counts = ngm.getSortedCountsForYear(year);

        if (counts.isEmpty()) {
            return "";
        }

        List<Double> ranks = new ArrayList<>();
        for (int i = 1; i <= counts.size(); i++) {
            ranks.add((double) i);
        }

        XYChart chart = Plotter.generateCustomXYChart(
                "Zipf's Law Verification for Year " + year + " (Log-Log Plot)",
                "Rank vs Frequency", ranks, counts, true);

        return Plotter.encodeChartAsString(chart);
    }
}