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
        bias = new Input(1.0, 0.0);
        NeuronUtils.fillWeightsWithRandoms(inputs, 0, 0.1, numberOfInputs);
    }

    /**
     * Metoda do obliczania wyjścia neuronu
     *
     * @return
     */
    public Double countOutput() {
        for (Input input : inputs) {
            output = output + input.getInputValue() * input.getInputWeight();
        }
        if (isBias) {
            output = output + bias.getInputWeight() * bias.getInputValue();
        }
        LOGGER.info("Obliczone wyjscie: " + output);
        if (output >= 0) return 1.0;
        else return -1.0;
    }


    public void correctWeights(Double output, Double learningStep, Double expectedValue) {
        if (output.compareTo(expectedValue) != 0) {
            LOGGER.info("Korekcja wag");
            if (output.compareTo(1.0) == 0) {
                for (Input input : this.getInputs()) {
                    LOGGER.info("Waga przed korekcja: " + input.getInputWeight());
                    input.setInputWeight(input.getInputWeight() - input.getInputValue() * learningStep);
                    LOGGER.info("Waga po korekcja: " + input.getInputWeight());

                }
                LOGGER.info("Bias przed korekcji: " + bias.getInputWeight());
                bias.setInputWeight(bias.getInputWeight() - bias.getInputValue() * learningStep);
                LOGGER.info("Bias po korekcji: " + bias.getInputWeight());
            } else {
                for (Input input : this.getInputs()) {
                    LOGGER.info("Waga przed korekcja: " + input.getInputWeight());
                    input.setInputWeight(input.getInputWeight() + input.getInputValue() * learningStep);
                    LOGGER.info("Waga po korekcja: " + input.getInputWeight());
                }
                LOGGER.info("Bias przed korekcji: " + bias.getInputWeight());
                bias.setInputWeight(bias.getInputWeight() + bias.getInputValue() * learningStep);
                LOGGER.info("Bias po korekcji: " + bias.getInputWeight());
            }

        } else {
            LOGGER.info("Bez korekcji");
        }

    }


}
