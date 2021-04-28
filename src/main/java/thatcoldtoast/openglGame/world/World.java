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
//            chunks.add(worldGenerator.genChunk());//0, 0, 0));
//            chunks.add(new Chunk(0, 0));

            for(int i = 1; i <= 5; i++)
            {
                chunks.add(new Chunk(0, i));
            }
        }
    }

    public void update() {
        for(int i = 0; i < chunks.size(); i++) {
            chunks.get(i).update();
        }
    }

    public void destroy() {
        for(Chunk c: chunks) {
            c.destroy();
        }
    }
}
