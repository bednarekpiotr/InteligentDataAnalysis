package iad;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import utils.NeuronUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private NumberAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private ScatterChart<?, ?> scatterChart;


    @FXML
    void initialize() {
        XYChart.Series series = new XYChart.Series();
        List<Point> points = NeuronUtils.readPointsFromFile();
        for (Point point : points) {
            series.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
        }
        scatterChart.getData().addAll(series);

    }

}
