import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int voterSize = 100;

    public static String winnerStr;
    public static int w;

    public static int radius = 6;

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
            candidates.add(new Candidate(5, 5, "Trump")); //Trump, position 0
            candidates.add(new Candidate(-5, -5, "Hawkins")); //Hawkins, position 1
            candidates.add(new Candidate(-4, -4, "Biden")); //Biden, position 2


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

                //Determines the closest candidate
                double close = Collections.min(v.distances);
                v.closestGuy = v.distances.indexOf(close);

                //Determines which candidates are preferred and counts votes
                for (int a = 0; a < candidates.size(); a++) {

                    //If the candidate is within radius then
                    if (v.distances.get(a) <= radius) {
                        voteCounts.set(a, voteCounts.get(a) + 1);
                        v.preferences.set(a, 1);
                    }

                    //If the candidate isn't within radius but he's the Closest Guy
                    else if (a == v.closestGuy) {
                        voteCounts.set(a, voteCounts.get(a) + 1);
                        v.preferences.set(a, 1);
                    }
                }
                System.out.println("(" + v.xPos + ", " + v.yPos + ")");
            }

            //Détermine le gagnant
            w = voteCounts.indexOf(Collections.max(voteCounts));
            //winnerStr = candidates.get(w).name;

            //Orders vote list
            Collections.sort(voteCounts, Collections.reverseOrder());

            //Filewriting
            try {
                FileWriter fW = new FileWriter(new File("C:\\Users\\Dasha\\Desktop", "Approval.txt"), true);
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

            //Test prints
        /* Voter vo = voters.get(ThreadLocalRandom.current().nextInt(0, voters.size()));
        System.out.println("voter number: " + voters.indexOf(vo));
        System.out.println(vo.xPos + ", " + vo.yPos);
        for (int item : vo.preferences) System.out.print(vo.preferences.get(item) + " ");
        System.out.println("Trump: " + vo.distances.get(0) +
                ", Hawkins: " + vo.distances.get(1) +
                ", Biden: " + vo.distances.get(2));
        System.out.println("Winner was: " + candidates.get(w).name);
        System.out.println("T: " + voteCounts.get(0) + ", H: " + voteCounts.get(1) + ", B: " + voteCounts.get(2)); */
        }
    }
}
