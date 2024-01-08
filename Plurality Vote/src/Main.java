import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int voterSize = 100;
    public static String winnerStr;
    public static int w;

    public static void main(String[] args) {
        for (int o = 0; o < 100; o++) {
            //Crée la liste de décomptes
            List<Integer> voteCounts = Arrays.asList(0, 0, 0);

            //Crée 50 voters dans une liste
            ArrayList<Voter> voters = new ArrayList<>();

            while (voters.size() < voterSize) { // try implementing while with radius?
                int x = ThreadLocalRandom.current().nextInt(-10, 11);
                int y = ThreadLocalRandom.current().nextInt(-10, 11);

                if ((x * x + y * y) < 100) {
                    voters.add(new Voter(x, y));
                }
            }

            //Crée une liste de candidats
            //Positions de Trump (0), Hawkins (1) et Biden (3)
            ArrayList<Candidate> candidates = new ArrayList<>();
            candidates.add(new Candidate(5, 5, "A")); //A, position 0
            candidates.add(new Candidate(-5, -5, "C")); //C, position 1
            candidates.add(new Candidate(-4, -4, "B")); //B, position 2



            //Itération à travers les voteurs
            for (int i = 0; i < voterSize; i++) {
                Voter v = voters.get(i);

                //Itération à travers les 3 candidats
                for (int j = 0; j < candidates.size(); j++) {
                    Candidate c = candidates.get(j);

                    //Calcule la distance entre lui et le candidat
                    double distance = Math.sqrt(
                            (v.xPos - c.xPos) * (v.xPos - c.xPos) +
                                    (v.yPos - c.yPos) * (v.yPos - c.yPos)
                    );

                    //Garde la distance dans une variable
                    v.distances.set(j, distance);
                }

                //Détermine la préférence
                double closestGuy = Collections.min(v.distances);
                System.out.println(closestGuy);
                v.preference = v.distances.indexOf(closestGuy);

                //Compte les votes
                voteCounts.set(v.preference, (voteCounts.get(v.preference) + 1));
            }

            //Détermine le gagnant
            w = voteCounts.indexOf(Collections.max(voteCounts));
            //winnerStr = candidates.get(w).name;

            //Orders vote list
            Collections.sort(voteCounts, Collections.reverseOrder());

            //Writes to file for easy access
            try {
                FileWriter fW = new FileWriter(new File("C:\\Users\\Dasha\\Desktop", "Plurality.txt"), true);
                PrintWriter pW = new PrintWriter(fW);
                String results
                        = w + " " + voteCounts.get(0) + " " + voteCounts.get(1)
                        + " " + voteCounts.get(2)
                        + "\n";
                pW.append(results);
                pW.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}