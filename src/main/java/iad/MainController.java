package iad;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.val;
import org.apache.log4j.Logger;
import utils.NeuronUtils;

import java.util.ArrayList;
import java.util.List;


public class MainController {


    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    @FXML
    private NumberAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Button learn;
    @FXML
    private Button loadPoints;
    @FXML
    private Button generatePoints;

    @FXML
    private TextField a;

    @FXML
    private TextField b;

    @FXML
    private TextField max;

    @FXML
    private TextField min;

    @FXML
    private TextField quantity;

    private int epoka = 1;

    Double left;
    Double right;



    List<Point> points = NeuronUtils.readPointsFromFile();
    XYChart.Series seriesOne = new XYChart.Series();

    XYChart.Series seriesZero = new XYChart.Series();
    XYChart.Series seriesThree = new XYChart.Series();
    XYChart.Series seriesFour = new XYChart.Series();
    Neuron neuron;

    @FXML
    void initialize() {
        neuron = new Neuron(Boolean.TRUE, 2);

    }

    @FXML
    void refreshChart(ActionEvent event) {
        for (int i = 0; i < 1000; i++) {
            LOGGER.info("###############################################################################");
            LOGGER.info("Epoka: " + epoka);
            refreshChart();
            epoka++;
        }

    }


    void refreshChart() {

        XYChart.Series tempSeries = new XYChart.Series();
        for (Point point : points) {
            LOGGER.info("Punkt" + point.toString());
            neuron.getInputs().get(0).setInputValue(point.getX1());
            neuron.getInputs().get(1).setInputValue(point.getX2());
            neuron.setOutput(neuron.countOutput());
            LOGGER.info("Wyjscie neuronu: " + neuron.getOutput());
            neuron.correctWeights(neuron.getOutput(), 0.3, point.getTag());

        }


        if (neuron.getIsBias()) {
            Point temp = new Point(null, left, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * left - (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * right - (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        } else {
            Point temp = new Point(null, left, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * left);
            tempSeries.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            Point temp2 = new Point(null, right, (-neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * right);
            tempSeries.getData().add(new XYChart.Data<>(temp2.getX1(), temp2.getX2()));
        }


        lineChart.getData().remove(3);
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
        left = Double.valueOf(min.getText());
        right = Double.valueOf(max.getText());



        for (Point point : points) {
            if (point.getTag() == 1)
                seriesOne.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
            else seriesZero.getData().add(new XYChart.Data<>(point.getX1(), point.getX2()));
        }
        if (neuron.getIsBias()) {
            for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
                Point temp = new Point(null, i + 1.0, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * (i + 1.0) - (neuron.getBias().getInputWeight() / neuron.getInputs().get(1).getInputWeight()));
                seriesThree.getData().add(new XYChart.Data<>(temp.getX1(), temp.getX2()));
            }
        } else {
            for (int i = 0; i < neuron.getNumberOfInputs(); i++) {
                Point temp = new Point(null, i + 1.0, (neuron.getInputs().get(0).getInputWeight() / neuron.getInputs().get(1).getInputWeight()) * (i + 1.0));

            }
        }

        seriesFour.getData().add(new XYChart.Data<>(left, Double.valueOf(a.getText()) * left + Double.valueOf(b.getText())));
        seriesFour.getData().add(new XYChart.Data<>(right, Double.valueOf(a.getText()) * right + Double.valueOf(b.getText())));
        lineChart.getData().addAll(seriesOne, seriesZero, seriesFour, seriesThree);
    }

    @FXML
    void generatePoints(ActionEvent event) {
        NeuronUtils.generatePointsToFile(Double.valueOf(a.getText()), Double.valueOf(b.getText()), Integer.valueOf(quantity.getText()), Double.valueOf(min.getText()), Double.valueOf(max.getText()));
    }


}
