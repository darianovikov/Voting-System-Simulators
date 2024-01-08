import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int voterSize = 100;

    public static int trumpVotes = 0;
    public static int hawkinsVotes = 0;
    public static int bidenVotes = 0;

    public static int leastVotes;

    public static String winner;

    public static void main(String[] args) {
        //Crée 50 voters dans une liste
        ArrayList<Voter> voters = new ArrayList<>();

        for (int i = 0; i < voterSize; i++) {
            int x = ThreadLocalRandom.current().nextInt(-10, 11);
            int y = ThreadLocalRandom.current().nextInt(-10, 11);
            Voter v = new Voter(x, y);
            voters.add(v);
        }

        //Crée une liste de candidats
        //Positions de Trump (0), Hawkins (1) et Biden (3)
        ArrayList<Candidate> candidates = new ArrayList<>();

        candidates.add(new Candidate(4, 4)); //Trump, position 0
        candidates.add(new Candidate(-6, -8)); //Hawkins, position 1
        candidates.add(new Candidate(-4, -4)); //Biden, position 2


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

            //Détermine l'ordre de préférences
            //Préférence = (ordre, candidat)
            if (v.distances.get(0) < v.distances.get(1) && v.distances.get(1) < v.distances.get(2)) {
                v.preferences.set(0, 0);
                v.preferences.set(1, 1);
                v.preferences.set(2, 2);
                //0 1 2 in 2nd column
            } else if (v.distances.get(0) < v.distances.get(2) && v.distances.get(2) < v.distances.get(1)) {
                v.preferences.set(0, 0);
                v.preferences.set(1, 2);
                v.preferences.set(2, 1);
                //0 2 1
            } else if (v.distances.get(1) < v.distances.get(0) && v.distances.get(0) < v.distances.get(2)) {
                v.preferences.set(0, 1);
                v.preferences.set(1, 0);
                v.preferences.set(2, 2);
                //1 0 2
            } else if (v.distances.get(1) < v.distances.get(2) && v.distances.get(2) < v.distances.get(0)) {
                v.preferences.set(0, 1);
                v.preferences.set(1, 2);
                v.preferences.set(2, 0);
                //1 2 0
            } else if (v.distances.get(2) < v.distances.get(0) && v.distances.get(0) < v.distances.get(1)) {
                v.preferences.set(0, 2);
                v.preferences.set(1, 0);
                v.preferences.set(2, 1);
                //2 0 1
            } else if (v.distances.get(2) < v.distances.get(1) && v.distances.get(1) < v.distances.get(0)) {
                v.preferences.set(0, 2);
                v.preferences.set(1, 1);
                v.preferences.set(2, 0);
                //2 1 0
            } else {
                v.preferences.set(0, 0);
                v.preferences.set(1, 1);
                v.preferences.set(2, 2);
            }
        }


        //Iterates through, eliminating each time
        for (int b = 0; b < candidates.size(); b++) {

            //
            for (int i = 0; i < voterSize; i++) {
                Voter v = voters.get(i);
                switch (v.preferences.get(0)) {
                    case 0:
                        trumpVotes += 1;
                        break;
                    case 1:
                        hawkinsVotes += 1;
                        break;
                    case 2:
                        bidenVotes += 1;
                        break;
                }
            }
        }

        //Selectionne un candidat
        if (trumpVotes > hawkinsVotes) {
            if (trumpVotes > bidenVotes) {
                winner = "Trump";
            } else if (trumpVotes < bidenVotes)
                winner = "Biden";
        } else if (trumpVotes < hawkinsVotes) {
            if (hawkinsVotes < bidenVotes) {
                winner = "Biden";
            } else if (hawkinsVotes > bidenVotes) {
                winner = "Hawkins";
            }
        }

        //Testing prints
        Voter v = voters.get(4);
        System.out.println("Trump: " + v.distances.get(0) +
                ", Hawkins: " + v.distances.get(1) +
                ", Biden: " + v.distances.get(2));
        System.out.println("winner was: " + winner);
        System.out.println("T: " + trumpVotes + ", H: " + hawkinsVotes + ", B: " + bidenVotes);
    }
}
