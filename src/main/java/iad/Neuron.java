package iad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import utils.NeuronUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezntująca pojedynczy neuron
 */
@AllArgsConstructor
public class Neuron {

    private static final Logger LOGGER = Logger.getLogger(Neuron.class);
    @Getter
    @Setter
    private List<Input> inputs;
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
    private Input bias;

    public Neuron(Boolean isBias, int numberOfInputs) {
        this.output = 0.0;
        this.isBias = isBias;
        this.numberOfInputs = numberOfInputs;
        inputs = new ArrayList<>(numberOfInputs);
        NeuronUtils.fillWeightsWithRandoms(inputs, -1, 1, numberOfInputs);
    }

    public Double countOutput() {
        for (Input input : inputs) {
            output = output + input.getInputValue() * input.getInputWeight();
        }
        if (isBias) {
            output = output + bias.getInputWeight() * bias.getInputValue();
        }
        return output;
    }

    public void correctWeights(Double output, Double learningStep) {
        if (isBias) {
            for (Input input : inputs) {
                if (output <= 0) {
                    input.setInputWeight(input.getInputWeight() - input.getInputValue() * learningStep);
                    bias.setInputWeight(bias.getInputWeight() - bias.getInputValue() * learningStep);
                    LOGGER.info("Wejscie" + input.toString() + "Bias" + bias.toString());

                } else {
                    input.setInputWeight(input.getInputWeight() + input.getInputValue() * learningStep);
                    bias.setInputWeight(bias.getInputWeight() + bias.getInputValue() * learningStep);
                    LOGGER.info("Wejscie" + input.toString() + "Bias" + bias.toString());

                }
            }
        } else {
            for (Input input : inputs) {
                if (output <= 0) {
                    input.setInputWeight(input.getInputWeight() - input.getInputValue() * learningStep);
                    LOGGER.info(input.toString());
                } else {
                    input.setInputWeight(input.getInputWeight() + input.getInputValue() * learningStep);
                    LOGGER.info(input.toString());
                }
            }
        }



    }


}
