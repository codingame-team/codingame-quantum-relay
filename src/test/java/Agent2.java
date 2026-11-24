import java.util.*;

public class Agent2 {
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

            pulsars.sort(Comparator.comparingDouble(p -> Math.hypot(p[1] - myBaseX, p[2] - myBaseY)));
            int trunkId = myDrones.get(0)[0];
            int dir = (myBaseX < width / 2) ? 1 : -1;
            int trunkX = myBaseX + dir * 320;
            int trunkY = myBaseY;

            for (int i = 0; i < myDrones.size(); i++) {
                int[] d = myDrones.get(i);
                if (i == 0) {
                    if (leagueLevel >= 2 && Math.hypot(d[1] - trunkX, d[2] - trunkY) < 25 && d[4] == 0) {
                        System.out.println("OVERCHARGE -1");
                    } else {
                        System.out.println("MOVE " + trunkX + " " + trunkY + " LINK -1");
                    }
                } else {
                    int[] targetP = pulsars.get(Math.min(i - 1, pulsars.size() - 1));
                    double distPToTrunk = Math.hypot(targetP[1] - trunkX, targetP[2] - trunkY);
                    int hx = targetP[1];
                    int hy = targetP[2];
                    if (distPToTrunk > 150) {
                        double r = 150.0 / distPToTrunk;
                        hx = (int) Math.round(targetP[1] + (trunkX - targetP[1]) * r);
                        hy = (int) Math.round(targetP[2] + (trunkY - targetP[2]) * r);
                    }
                    System.out.println("MOVE " + hx + " " + hy + " LINK " + trunkId);
                }
            }
        }
    }
}
