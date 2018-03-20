package iad;

import lombok.Getter;
import lombok.Setter;

public class Input {
    @Getter
    @Setter
    private Double inputValue;
    @Setter
    @Getter
    private Double inputWeight;

    public Input(Double inputValue, Double inputWeight) {
        this.inputValue = inputValue;
        this.inputWeight = inputWeight;
    }

    @Override
    public String toString() {
        return new StringBuffer("Input: ").append(inputValue.toString()).append("Wight: ").append(inputWeight.toString()).toString();
    }
}
