package thatcoldtoast.openglGame.world;

public class WorldGenerator {
    long seed;

    public WorldGenerator(long s) {
        seed = s;
    }

    public Chunk genChunk(int xOff, int yOff, int zOff) {
        return new Chunk();
    }
}
