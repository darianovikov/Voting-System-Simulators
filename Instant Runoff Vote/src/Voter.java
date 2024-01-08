import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Voter {
    int xPos = 0;
    int yPos = 0;

    List<Integer> preferences;
    List<Double> distances;

    public Voter(int a, int b) {
        xPos = a;
        yPos = b;

        distances = Arrays.asList(0.0, 0.0, 0.0);
        preferences = Arrays.asList(0, 0, 0);
    }
}