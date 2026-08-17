package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.List;

public class AvgLengthHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public AvgLengthHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        TimeSeries avgLengths = ngm.averageWordLengthHistory(q.startYear(), q.endYear());
        XYChart chart = Plotter.generateTimeSeriesChart(List.of("Average Word Length"), List.of(avgLengths));
        chart.setTitle("Average Word Length from " + q.startYear() + " to " + q.endYear());
        return Plotter.encodeChartAsString(chart);
    }
}