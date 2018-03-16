package iad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
    private LineChart<?, ?> lineChart;

    @FXML
    private Button learn;


    XYChart.Series seriesThree = new XYChart.Series();
    Neuron neuron = new Neuron(true, 2);
    List<Point> points = NeuronUtils.readPointsFromFile();

    @FXML
    void initialize() {

        XYChart.Series seriesOne = new XYChart.Series();
        XYChart.Series seriesZero = new XYChart.Series();
        for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
            Point temp = new Point(null, i + 1.0, (-neuron.getWeights().get(0) / neuron.getWeights().get(1)) * (i + 1.0) - (neuron.getBias() / neuron.getWeights().get(1)));
            seriesThree.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
        }


        for (Point point : points) {
            if (point.getTag() == 1)
                seriesOne.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
            else seriesZero.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
        }
        lineChart.getData().addAll(seriesOne, seriesZero, seriesThree);

    }

    @FXML
    void refreshChart(ActionEvent event) {
        XYChart.Series tempSeries = new XYChart.Series();
        for (Point point : points) {
            Double value = neuron.countU(point);
            neuron.correctWeights(value, 0.05);
        }
        for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
            Point temp = new Point(null, i + 1.0, (-neuron.getWeights().get(0) / neuron.getWeights().get(1)) * (i + 1.0) - (neuron.getBias() / neuron.getWeights().get(1)));
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
        }


        lineChart.getData().add(tempSeries);
        String temp = new String();



    }

}
