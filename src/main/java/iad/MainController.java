package iad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import utils.NeuronUtils;

import java.util.List;


public class MainController {

    @FXML
    private NumberAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Button learn;
    @FXML
    private Slider bias;
    @FXML
    private CheckBox ifBias;
    @FXML
    private Button loadPoints;


    List<Point> points = NeuronUtils.readPointsFromFile();
    XYChart.Series seriesOne = new XYChart.Series();
    XYChart.Series seriesZero = new XYChart.Series();
    XYChart.Series seriesThree = new XYChart.Series();
    Neuron neuron;

    @FXML
    void initialize() {
    }

    @FXML
    void refreshChart(ActionEvent event) {
        Double left = 0.0;
        Double right = 5.0;
        XYChart.Series tempSeries = new XYChart.Series();

        for (Point point : points) {
            Double value = neuron.countU(point);
            neuron.correctWeights(value, 0.1);

        }
        if (ifBias.isSelected()) {
            Point temp = new Point(null, left, (neuron.getWeights().get(0) / neuron.getWeights().get(1)) * left - (neuron.getBias() / neuron.getWeights().get(1)));
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (neuron.getWeights().get(0) / neuron.getWeights().get(1)) * right - (neuron.getBias() / neuron.getWeights().get(1)));
            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        } else {
            Point temp = new Point(null, left, (neuron.getWeights().get(0) / neuron.getWeights().get(1)) * left);
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (neuron.getWeights().get(0) / neuron.getWeights().get(1)) * right);
            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        }


        lineChart.getData().remove(2);
        lineChart.getData().add(tempSeries);


    }


    @FXML
    void resetChart(ActionEvent event) {
        lineChart.getData().remove(0, lineChart.getData().size() - 1);
        initialize();
    }

    @FXML
    void loadPoints(ActionEvent event) {
        neuron = new Neuron(ifBias.isSelected(), 2);
        for (Point point : points) {
            if (point.getTag() == 1)
                seriesOne.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
            else seriesZero.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
        }
        for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
            Point temp = new Point(null, i + 1.0, (-neuron.getWeights().get(0) / neuron.getWeights().get(1)) * (i + 1.0) - (neuron.getBias() / neuron.getWeights().get(1)));
            seriesThree.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
        }
        lineChart.getData().addAll(seriesOne, seriesZero, seriesThree);
    }

}
