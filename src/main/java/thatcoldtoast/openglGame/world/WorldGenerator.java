package thatcoldtoast.openglGame.world;

public class WorldGenerator {
    long seed;

    public WorldGenerator(long s) {
        seed = s;
    }

    public Chunk genChunk() {
        return new Chunk(0, 0);
    }
}
