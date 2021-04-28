package thatcoldtoast.openglGame.world;

import org.joml.SimplexNoise;
import org.lwjgl.system.CallbackI;
import thatcoldtoast.openglGame.gameObjects.Block;
import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Chunk {
    int chunkSize = 8;
    int chunkX;
    int chunkZ;
    float noiseMult = 0.05f;
    Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];

    public Chunk(int chunkX_, int chunkZ_) {
        chunkX = chunkX_;
        chunkZ = chunkZ_;

        for(int x = 0; x < blocks.length; x++) { //fill with noise
                String line = "";
                for (int z = 0; z < blocks.length; z++) {
                    int height;

                    if(chunkX == 0 || chunkZ == 0)
                        height = (int) ((SimplexNoise.noise(x * noiseMult, z * noiseMult) + 1) * (chunkSize/2)); //chunkX or chunkZ multiply by 0 problem
                    else
                        height = (int) ((SimplexNoise.noise(x * chunkX * noiseMult, z * chunkZ * noiseMult) + 1) * (chunkSize/2)); //multiply noise(x, z) by chunk(x, z) for whole world gen
                    line += "" + height + " ";

                    blocks[x][height][z] = new Block(x, height, z); // times chunkX and chunkZ

                    int num = (int) Math.random() * 2;

                    if(num == 0)
                        blocks[x][height][z].blockType = "wood";
                    else if(num == 1)
                        blocks[x][height][z].blockType = "checker";
                }
                System.out.println(line);
        }

        for(int x = 0; x < blocks.length; x++) { //fill empty space with dirt
            for (int y = 0; y < blocks.length; y++) {
                for (int z = 0; z < blocks.length; z++) {
                    if(!(blocks[x][y][z] instanceof Block))
                    {
                        blocks[x][y][z] = new Block((int) (x * chunkX), y, (int) (z * chunkZ));
                        blocks[x][y][z].blockType = "air";
                    }
                }
            }
        }
    }

    public void update() {
        for(int x = 0; x < chunkSize; x++) {
            for(int y = 0; y < chunkSize; y++) {
                for(int z = 0; z < chunkSize; z++) {
                    boolean Right = true;
                    boolean Left = true;
                    boolean Top = true;
                    boolean Bottom = true;
                    boolean Front = true;
                    boolean Back = true;

                    if(x+1 < chunkSize && !blocks[x+1][y][z].blockType.equals("air")) { //make switch
                        Right = false;
                    }
                    if(x-1 >= 0 && !blocks[x-1][y][z].blockType.equals("air")) {
                        Left = false;
                    }
                    if(y+1 < chunkSize && !blocks[x][y+1][z].blockType.equals("air")) {
                        Top = false;
                    }
                    if(y-1 >= 0 && !blocks[x][y-1][z].blockType.equals("air")) {
                        Bottom = false;
                    }
                    if(z+1 < chunkSize && !blocks[x][y][z+1].blockType.equals("air")) {
                        Front = false;
                    }
                    if(z-1 >= 0 && !blocks[x][y][z-1].blockType.equals("air")) {
                        Back = false;
                    }

//                    if(x < chunkSize-1) { //whole inside chunk removal
//                        Right = false;
//                    }
//                    if(x > 0) {
//                        Left = false;
//                    }
//                    if(y < chunkSize-1) {
//                        Top = false;
//                    }
//                    if(y > 0) {
//                        Bottom = false;
//                    }
//                    if(z < chunkSize-1) {
//                        Front = false;
//                    }
//                    if(z > 0) {
//                        Back = false;
//                    }

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
