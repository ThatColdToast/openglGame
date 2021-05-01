package thatcoldtoast.openglGame.world;

import org.joml.SimplexNoise;
import org.lwjgl.system.CallbackI;
import thatcoldtoast.openglGame.gameObjects.Block;
import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Chunk {
    public static int chunkSize = 8;
    int chunkX;
    int chunkZ;
    float horizontalNoiseMult = 0.03f;
    float noiseAmplitude = 4.0f;
    Block[][][] blocks = new Block[chunkSize][64][chunkSize];

    public Chunk(int chunkX_, int chunkZ_) {
        chunkX = chunkX_;
        chunkZ = chunkZ_;

        System.out.println("Creating Chunk at: " + chunkX + " " + chunkZ);

        for(int x = 0; x < blocks.length; x++) { //fill with noise
//                String line = "";
                for (int z = 0; z < blocks.length; z++) {
                    int height;

                    height = (int) (((SimplexNoise.noise((x + (chunkX * chunkSize)) * horizontalNoiseMult, (z + (chunkZ * chunkSize)) * horizontalNoiseMult)) + 1) * noiseAmplitude); //multiply noise(x, z) by chunk(x, z) for whole world gen

                    blocks[x][height][z] = new Block(x + (chunkX * chunkSize), height, z + (chunkZ * chunkSize)); // times chunkX and chunkZ

//                    int num = (int) Math.random() * 2;
//
//                    if(num == 0)
                        blocks[x][height][z].blockType = "dirt";
//                    else if(num == 1)
//                        blocks[x][height][z].blockType = "checker";
                }
//                System.out.println(line);
        }

        for(int x = 0; x < blocks.length; x++) { //fill empty space with air (old)
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

//        for(int x = 0; x < blocks.length; x++) { //fill empty space with stone (new)
//            for (int z = 0; z < blocks.length; z++) {
//                boolean top = false;
//                for (int y = 0; y < blocks.length; y++) {
//                    if(blocks[x][y][z] instanceof Block) {
//                        top = true;
//                    } else {
//                        if(!top) {
//                            blocks[x][y][z] = new Block((int) (x * chunkX), y, (int) (z * chunkZ));
//                            blocks[x][y][z].blockType = "stone";
//                        } else {
//                            blocks[x][y][z] = new Block((int) (x * chunkX), y, (int) (z * chunkZ));
//                            blocks[x][y][z].blockType = "air";
//                        }
//                    }
//                }
//            }
//        }
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
