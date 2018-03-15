package utils;

import iad.Point;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NeuronUtils {

    private static final String PATH = "src/main/resources/input.txt";
    private static final Logger LOGGER = Logger.getLogger(NeuronUtils.class);

    /**
     * Metoda losuje wagi z podanego przedziału
     *
     * @param weights
     * @param min
     * @param max
     * @param numberOfInputs
     */
    public static void fillWagesWithRandoms(List weights, double min, double max, int numberOfInputs) {
        for (int i = 0; i < numberOfInputs; i++) {
            LOGGER.info("Losowanie wag");
            weights.add(ThreadLocalRandom.current().nextDouble(min, max));
        }
    }

    /**
     * Metoda wczytuje z pliku punkty
     *
     * @return
     */
    public static List<Point> readPointsFromFile() {
        List<Point> pointsFromFile = new ArrayList<>();
        try {
            Stream<String> stringStream = Files.lines(Paths.get(PATH));
            List<String> linesFromFile = stringStream.collect(Collectors.toList());
            for (String line : linesFromFile) {
                String[] temp = line.split(",");
                pointsFromFile.add(new Point(Double.valueOf(temp[0]), Double.valueOf(temp[1]), Double.valueOf(temp[2])));
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            LOGGER.error("Błąd przy odczycie wartości z pliku", e);
        }
        return pointsFromFile;

    }

}
