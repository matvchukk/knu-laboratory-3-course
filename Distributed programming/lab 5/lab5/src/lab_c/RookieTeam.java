package lab_c;

import java.util.ArrayList;
import java.util.Arrays;

public class RookieTeam implements Runnable{
    private static final ArrayList<Boolean> teamFinished = new ArrayList<Boolean>();

    private final int[] rookies;
    private final int teamIndex;
    private final int leftIndex;
    private final int rightIndex;
    private final CustomBarrier cyclicBarrier;

    public RookieTeam(int[] rookies, int teamIndex, int leftIndex, int rightIndex, CustomBarrier cyclicBarrier) {
        super();
        this.rookies = rookies;
        this.teamIndex = teamIndex;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        while (Main.running) {
            boolean thisPartFinished = teamFinished.get(teamIndex);
            if (!thisPartFinished) {
                System.out.println(Arrays.toString(rookies));
                boolean formatted = true;
                for (int i = leftIndex; i < rightIndex - 1; i++) {
                    if (rookies[i] != rookies[i + 1]) {
                        rookies[i] *= -1;
                        formatted = false;
                    }
                }
                if (formatted) {
                    finish();
                }
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void finish() {
        System.out.println("Team " + (teamIndex+1) + " finished");
        teamFinished.set(teamIndex, true);

        for (boolean currentPartFinished : teamFinished) {
            if (!currentPartFinished) {
                return;
            }
        }
        Main.stop();
    }

    public static void fillFinishedArray(int teamsAmount) {
        for (int i = 0; i < teamsAmount; i++) {
            teamFinished.add(false);
        }
    }
}