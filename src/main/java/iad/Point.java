package iad;

import lombok.Getter;

public class Point {

    @Getter
    private Double x1;
    @Getter
    private Double x2;
    @Getter
    private Double tag;

    public Point(Double t, Double d1, Double d2) {
        tag = t;
        x1 = d1;
        x2 = d2;
    }
}
