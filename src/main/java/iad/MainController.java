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

import java.util.ArrayList;
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
    @FXML
    private Button generatePoints;



    List<Point> points = NeuronUtils.readPointsFromFile();
    XYChart.Series seriesOne = new XYChart.Series();
    XYChart.Series seriesZero = new XYChart.Series();
    XYChart.Series seriesThree = new XYChart.Series();
    Neuron neuron;

    @FXML
    void initialize() {
        neuron = new Neuron(ifBias.isSelected(), 2);
    }

    @FXML
    void refreshChart(ActionEvent event) {
        for (int i = 0; i < 100; i++) {
            refreshChart();
        }
    }


    void refreshChart() {
        Double left = -5.0;
        Double right = 5.0;
        XYChart.Series tempSeries = new XYChart.Series();
            for (Point point : points) {
                neuron.getInputs().get(0).setInputValue(point.getX1());
                neuron.getInputs().get(1).setInputValue(point.getX2());
                Double output = neuron.countOutput();
                neuron.correctWeights(output, 0.1);
            }


        if (ifBias.isSelected()) {
            Point temp = new Point(null, left, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * left + (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * right + (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        } else {
            Point temp = new Point(null, left, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * left);

            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * right);

            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        }


        lineChart.getData().remove(2);
        lineChart.getData().add(tempSeries);


    }


    @FXML
    void resetChart(ActionEvent event) {
        lineChart.getData().remove(0, lineChart.getData().size());
        neuron.setInputs(new ArrayList<>());
        neuron.setInputs(new ArrayList<>());
        System.gc();
        System.runFinalization();

    }

    @FXML
    void loadPoints(ActionEvent event) {

        if (ifBias.isSelected()) neuron.setBias(new Input(0.0, bias.getValue()));


        for (Point point : points) {
            if (point.getTag() == 1)
                seriesOne.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
            else seriesZero.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
        }
        if (ifBias.isSelected()) {
            for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
                Point temp = new Point(null, i + 1.0, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * (i + 1.0) - (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
                seriesThree.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            }
        } else {
            for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
                Point temp = new Point(null, i + 1.0, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * (i + 1.0));

            }
        }

        lineChart.getData().addAll(seriesOne, seriesZero, seriesThree);
    }

    @FXML
    void generatePoints(ActionEvent event) {
        NeuronUtils.generatePointsToFile(-5.0, 1.0, 1500, -25.0, 25.0);
    }

    @FXML
    void changeBiasValue(ActionEvent event) {
        if (neuron.getIsBias()) {
            neuron.setBias(new Input(bias.getValue(), 0.1));
        } else neuron.setBias(new Input(bias.getValue(), 0.1));


    }

}
