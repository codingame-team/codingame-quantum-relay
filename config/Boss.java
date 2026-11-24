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
                if (i == 0) {
                    System.out.println("MOVE " + trunkX + " " + trunkY + " LINK -1");
                } else {
                    int[] targetP = pulsars.get(Math.min(i - 1, pulsars.size() - 1));
                    int hx = (trunkX + targetP[1]) / 2;
                    int hy = (trunkY + targetP[2]) / 2;
                    System.out.println("MOVE " + hx + " " + hy + " LINK " + trunkId);
                }
            }
        }
    }
}
