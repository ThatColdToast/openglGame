package thatcoldtoast.openglGame.world;

import thatcoldtoast.openglGame.gameObjects.Block;

public class Chunk {
    Block[][][] blocks = new Block[16][16][16];

    public Chunk() {

    }

    public void update() {
        for(Block[][] blocks2D: blocks) {
            for(Block[] blocks : blocks2D) {
                for(Block b: blocks) {
                    b.update();
                }
            }
        }
    }

    public void destroy() {
        for(Block[][] blocks2D: blocks) {
            for(Block[] blocks : blocks2D) {
                for(Block b: blocks) {
                    b.destroy();
                }
            }
        }
    }
}
