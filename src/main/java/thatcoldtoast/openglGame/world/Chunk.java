package thatcoldtoast.openglGame.world;

import org.lwjgl.system.CallbackI;
import thatcoldtoast.openglGame.gameObjects.Block;

public class Chunk {
    int chunkSize = 16;
    Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];

    public Chunk() {
//        blocks[0][0][0] = new Block(0, 0, 0);

        for(int x = 0; x < blocks.length; x++) {
            for(int y = 0; y < blocks.length; y++) {
                for(int z = 0; z < blocks.length; z++) {
                    blocks[x][y][z] = new Block(x, y, z);
                }
            }
        }
    }

    public void update() {
        for(int x = 0; x < blocks.length; x++) {
            for(int y = 0; y < blocks.length; y++) {
                for(int z = 0; z < blocks.length; z++) {
                    boolean Right = true;
                    boolean Left = true;
                    boolean Top = true;
                    boolean Bottom = true;
                    boolean Front = true;
                    boolean Back = true;

                    if(x < chunkSize-1) {
                        Right = false;
                    }
                    if(x > 0) {
                        Left = false;
                    }
                    if(y < chunkSize-1) {
                        Top = false;
                    }
                    if(y > 0) {
                        Bottom = false;
                    }
                    if(z < chunkSize-1) {
                        Front = false;
                    }
                    if(z > 0) {
                        Back = false;
                    }

                    boolean[] bools = new boolean[6];
                    bools[0] = Top;
                    bools[1] = Front;
                    bools[2] = Left;
                    bools[3] = Right;
                    bools[4] = Back;
                    bools[5] = Bottom;
                    blocks[x][y][z].update(bools); //not super efficient
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
