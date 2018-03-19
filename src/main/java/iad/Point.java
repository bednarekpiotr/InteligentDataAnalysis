package iad;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Point implements Serializable {

    @Getter
    @Setter
    private Double x1;
    @Getter
    @Setter
    private Double x2;
    @Getter
    @Setter
    private Double tag;

    public Point(Double t, Double d1, Double d2) {
        tag = t;
        x1 = d1;
        x2 = d2;
    }

    @Override
    public String toString() {
        return new StringBuffer(String.valueOf(tag)).append(",").append(String.valueOf(x1)).append(",").append(String.valueOf(x2)).toString();
    }
}
