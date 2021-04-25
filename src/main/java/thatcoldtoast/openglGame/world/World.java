package thatcoldtoast.openglGame.world;

import thatcoldtoast.openglGame.gameObjects.Block;

import java.util.ArrayList;

public class World { //actual world object
    public WorldGenerator worldGenerator;
    public ArrayList<Chunk> chunks = new ArrayList<Chunk>();
    private long seed;

    private boolean CREATED = false;

    public World() {

    }

    public void create(long s) { //s = seed
        if(!CREATED) {
            CREATED = true;
            seed = s;

            //Generation starts here
            chunks.add(worldGenerator.genChunk(0, 0, 0));
        }
    }

    public void update() {
        for(Chunk c: chunks) {
            c.update();
        }
    }

    public void destroy() {
        for(Chunk c: chunks) {
            c.destroy();
        }
    }
}
