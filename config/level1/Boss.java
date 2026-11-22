import java.util.*;

class Player {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int leagueLevel = in.nextInt();
        int myBaseX = in.nextInt();
        int myBaseY = in.nextInt();
        int oppBaseX = in.nextInt();
        int oppBaseY = in.nextInt();

        while (true) {
            int pulsarCount = in.nextInt();
            List<int[]> pulsars = new ArrayList<>();
            for (int i = 0; i < pulsarCount; i++) {
                int id = in.nextInt();
                int px = in.nextInt();
                int py = in.nextInt();
                int energy = in.nextInt();
                pulsars.add(new int[]{id, px, py, energy});
            }
            int droneCount = in.nextInt();
            List<int[]> myDrones = new ArrayList<>();
            for (int i = 0; i < droneCount; i++) {
                int id = in.nextInt();
                int owner = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                int link = in.nextInt();
                int cd = in.nextInt();
                if (owner == 0) {
                    myDrones.add(new int[]{id, x, y, link, cd});
                }
            }

            // Wood 2 Boss: sends Drone 0 toward closest pulsar, Drone 1 as relay midpoint
            int[] bestP = pulsars.get(0);
            double bestDist = 1e9;
            for (int[] p : pulsars) {
                if (p[3] <= 0) continue;
                double d = Math.hypot(p[1] - myBaseX, p[2] - myBaseY);
                if (d < bestDist) {
                    bestDist = d;
                    bestP = p;
                }
            }

            for (int i = 0; i < myDrones.size(); i++) {
                if (i == 0) {
                    int mx = (myBaseX + bestP[1]) / 2;
                    int my = (myBaseY + bestP[2]) / 2;
                    System.out.println("MOVE " + mx + " " + my + " LINK -1");
                } else {
                    int hx = (myBaseX + bestP[1] * 3) / 4;
                    int hy = (myBaseY + bestP[2] * 3) / 4;
                    System.out.println("MOVE " + hx + " " + hy + " LINK " + myDrones.get(0)[0]);
                }
            }
        }
    }
}
