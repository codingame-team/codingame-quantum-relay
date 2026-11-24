import java.util.*;

/**
 * Advanced Algorithmic Agent for Quantum Relay:
 * - Evaluates whether each Pulsar can be harvested via a 1-hop direct link to the Mothership
 *   (distance(Base, Pulsar) <= MAX_LINK_RANGE + HARVEST_RANGE = 680)!
 * - Deploys parallel direct harvesters (each with its own independent 6 or 12 bandwidth trunk to -1!)
 *   instead of bottlenecking multiple harvesters through a single trunk relay!
 * - In League 2+, uses OVERCHARGE (-1) as soon as a harvester is in range of both a Pulsar and the Mothership!
 */
public class Agent1 {
    static final double HARVEST_RANGE = 192.0;
    static final double MAX_LINK_RANGE = 472.0;

    static int[] computeDirectAnchor(int baseX, int baseY, int px, int py) {
        double dist = Math.hypot(px - baseX, py - baseY);
        if (dist <= MAX_LINK_RANGE) {
            return new int[]{px, py};
        }
        // Position at distance 185 from Pulsar toward Base
        double r = HARVEST_RANGE / dist;
        int ax = (int) Math.round(px + (baseX - px) * r);
        int ay = (int) Math.round(py + (baseY - py) * r);
        return new int[]{ax, ay};
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int leagueLevel = 4 - in.nextInt();
        int myBaseX = in.nextInt();
        int myBaseY = in.nextInt();
        int oppBaseX = in.nextInt();
        int oppBaseY = in.nextInt();

        while (true) {
            int turn = in.nextInt();
            int pulsarCount = in.nextInt();
            List<int[]> pulsars = new ArrayList<>();
            for (int i = 0; i < pulsarCount; i++) {
                pulsars.add(new int[]{in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt()});
            }
            int droneCount = in.nextInt();
            List<int[]> myDrones = new ArrayList<>();
            for (int i = 0; i < droneCount; i++) {
                int id = in.nextInt(), owner = in.nextInt(), x = in.nextInt(), y = in.nextInt();
                int link = in.nextInt(), cd = in.nextInt();
                if (owner == 0) myDrones.add(new int[]{id, x, y, link, cd});
            }

            // Sort pulsars by high energy & proximity to our Mothership
            pulsars.sort((a, b) -> {
                double da = Math.hypot(a[1] - myBaseX, a[2] - myBaseY) - a[3] * 3.0;
                double db = Math.hypot(b[1] - myBaseX, b[2] - myBaseY) - b[3] * 3.0;
                return Double.compare(da, db);
            });

            // Assign each drone to its own distinct high-yield reachable pulsar for parallel direct links!
            for (int i = 0; i < myDrones.size(); i++) {
                int[] d = myDrones.get(i);
                int[] targetP = pulsars.get(i % pulsars.size());
                int[] anchor = computeDirectAnchor(myBaseX, myBaseY, targetP[1], targetP[2]);

                double distToAnchor = Math.hypot(d[1] - anchor[0], d[2] - anchor[1]);
                double distToBase = Math.hypot(d[1] - myBaseX, d[2] - myBaseY);
                double distToPulsar = Math.hypot(d[1] - targetP[1], d[2] - targetP[2]);

                if (distToBase <= 478 && distToPulsar <= 198) {
                    if (leagueLevel >= 2 && d[4] == 0 && targetP[3] >= 8) {
                        System.out.println("OVERCHARGE -1 MSG 12GW!");
                    } else {
                        System.out.println("MOVE " + anchor[0] + " " + anchor[1] + " LINK -1");
                    }
                } else if (i > 0 && distToBase > 478) {
                    // Relay through Drone 0 until within direct Mothership link range
                    System.out.println("MOVE " + anchor[0] + " " + anchor[1] + " LINK " + myDrones.get(0)[0]);
                } else {
                    System.out.println("MOVE " + anchor[0] + " " + anchor[1] + " LINK -1");
                }
            }
        }
    }
}
