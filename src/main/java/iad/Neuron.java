package iad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import utils.NeuronUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Klasa reprezntująca pojedynczy neuron
 */
@AllArgsConstructor
public class Neuron {

    private static final Logger LOGGER = Logger.getLogger(Neuron.class);
    @Getter
    @Setter
    private List<Double> inputs;
    @Getter
    @Setter
    private List<Double> weights;
    @Getter
    @Setter
    private Double output;
    @Getter
    @Setter
    private Boolean isBias;
    @Getter
    @Setter
    private int numberOfInputs;
    @Getter
    @Setter
    private Double bias;

    public Neuron(Boolean isBias, int numberOfInputs) {
        this.output = 0.0;
        this.isBias = isBias;
        this.numberOfInputs = numberOfInputs;
        inputs = Arrays.asList(new Double[numberOfInputs]);
        weights = Arrays.asList(new Double[numberOfInputs]);
        NeuronUtils.fillWagesWithRandoms(weights, 2, 5, numberOfInputs);
        if (isBias) bias = 0.1;
    }

    public Double countU(Point point) {
        Double u = 0.0;
        this.inputs.set(0, point.getX1());
        this.inputs.set(1, point.getX2());
        for (int i = 0; i < numberOfInputs; i++) {
            u = u + inputs.get(i) * weights.get(i);
        }
        u = u + bias;
        return u;
    }

    public void correctWeights(Double u, Double temp) {
        for (int i = 0; i < numberOfInputs; i++) {
            if (u <= 0) {
                weights.set(i, weights.get(i) - inputs.get(i) * temp);
                bias = bias - inputs.get(i) * temp;

            } else {
                weights.set(i, weights.get(i) + inputs.get(i) * temp);
                bias = bias + inputs.get(i) * temp;
            }
        }
        if (u <= 0) {

        }


    }


}
