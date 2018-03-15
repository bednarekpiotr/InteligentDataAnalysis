package iad;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import utils.NeuronUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa reprezntująca pojedynczy neuron
 */
@AllArgsConstructor
public class Neuron {

    private static final Logger LOGGER = Logger.getLogger(Neuron.class);
    private List<Double> inputs;
    private List<Double> weights;
    private Double output;
    private Boolean isBias;
    private int numberOfInputs;
    private Double bias;

    public Neuron(Boolean isBias, int numberOfInputs) {
        this.output = 0.0;
        this.isBias = isBias;
        this.numberOfInputs = numberOfInputs;
        inputs = new ArrayList<>();
        weights = new ArrayList<>();
        NeuronUtils.fillWagesWithRandoms(weights, -5, 5, numberOfInputs);
        if (isBias) bias = ThreadLocalRandom.current().nextDouble(-5, 5);
    }
}
