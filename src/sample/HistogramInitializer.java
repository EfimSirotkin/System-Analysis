package sample;

import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Arrays;

public class HistogramInitializer {
    public static LineChart<String, Number> initializeHistogram(int[] sourceArray) {
        String[] categories = { "[0-9]", "[10-19]", "[20-29]","[30-39]","[40-49]","[50-59]",
                               "[60-69]","[70-79]","[80-89]","[90-99]","[100-109]","[110-119]",
                "[120-129]", "[130-139]","[140-149]","[150-159]","[160-169]","[170-179]","[180-189]","[190-199]",
                "[200-209]","[210-219]","[220-229]","[230-239]","[240-249]","[250-255]"};
        ArrayList<String> categoriesList = new ArrayList(categories.length);
        categoriesList.addAll(Arrays.asList(categories));

        final CategoryAxis xAxis = new CategoryAxis();
        //xAxis.setCategories(FXCollections.<String>observableArrayList(categoriesList));
        xAxis.setLabel("Интервалы яркости");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Частота");
        LineChart<String, Number> initializableHistogram = new LineChart<String, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName("Линия распределения");
        for(int i = 0; i < sourceArray.length; ++i) {
            series.getData().add(new XYChart.Data<>(categories[i], sourceArray[i]));
        }
        initializableHistogram.getData().add(series);
        return initializableHistogram;
    }
}
