package thatcoldtoast.openglGame.world;

public class WorldGeneratorThread extends Thread {
    long seed;

//    public WorldGeneratorThread(long s) {
//        seed = s;
//    }

    public void run(World w, int x, int z) {
        System.out.println("Thread Id: " + getId());
        w.chunks.add(new Chunk(x, z));
    }

    public void start(World w, int x, int z) {
        run(w, x, z);
    }
}