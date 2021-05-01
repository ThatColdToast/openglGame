package thatcoldtoast.openglGame.world;

import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.gameObjects.Block;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class World { //actual world object
    public WorldGeneratorThread[] worldGenerators = new WorldGeneratorThread[100];
    public WorldGeneratorThread worldGenerator;
    public WorldGeneratorThread worldGenerator2;
    public WorldGeneratorThread worldGenerator3;
    public WorldGeneratorThread worldGenerator4;

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
                for(int i = 1; i <= 8; i++)
                {
                    for(int j = 1; j <= 8; j++)
                    {
                        chunks.add(new Chunk(i, j));
                    }
                }

//            for(int i = 1; i <= 8; i++)
//            {
//                for(int j = 1; j <= 8; j++)
//                {
//                    int num = i * 8 + j;
//                    worldGenerators[num] = new WorldGeneratorThread();
//                    worldGenerators[num].start(this, i, j);
//                }
//            }

//            for(int i = 1; i <= 8; i++)
//            {
//                for(int j = 1; j <= 8; j++)
//                {
//                    WorldGeneratorThread worldGeneratorTmp = new WorldGeneratorThread();
//                    worldGeneratorTmp.start(this, i, j);
//                }
//            }

//            WorldGeneratorThread generatorThread = new WorldGeneratorThread();
//            WorldGeneratorThread generatorThread2 = new WorldGeneratorThread();
//            WorldGeneratorThread generatorThread3 = new WorldGeneratorThread();
//            WorldGeneratorThread generatorThread4 = new WorldGeneratorThread();
//
//            generatorThread.start(this, 0, 0);
//            generatorThread2.start(this, 1, 0);
//            generatorThread3.start(this, 0, 1);
//            generatorThread4.start(this, 1, 1);
        }
    }

    int pressed = 0;

    public void update() {
        float playerPosX = -Main.MainTransform.getPosition().x;
        float playerPosZ = -Main.MainTransform.getPosition().z;

        for(int i = 0; i < chunks.size(); i++) {
            //Remove far chunks
            int chunkX = chunks.get(i).chunkX * Chunk.chunkSize;
            int chunkZ = chunks.get(i).chunkZ * Chunk.chunkSize;
            float xDist = chunkX - playerPosX;
            float zDist = chunkZ - playerPosZ;
            float dist = (float) Math.sqrt((xDist * xDist) + (zDist * zDist));
//            System.out.println("X: " + chunkX + " Z: " + chunkZ + " Dist: " + dist);

            if(dist > 100) {
                System.out.println("Removing Chunk at: " + chunkX + " " + chunkZ);
                chunks.get(i).destroy();
                chunks.remove(i);
            }

            //Add new chunks
            if(pressed > 0)
                pressed--;

//            if(!(chunks.get(i).chunkX == (int)(playerPosX / 8) && chunks.get(i).chunkZ == (int)(playerPosZ / 8))) {
            if(KeyboardHandler.getKey(GLFW_KEY_3) && pressed == 0) { //replace with "get near" check
                //Check if chunks are already created
                float xpos = (playerPosX - (Chunk.chunkSize / 2)) / 8;
                float zpos = (playerPosZ - (Chunk.chunkSize / 2)) / 8;

                xpos+=3; //make it based off chunkSize
                zpos+=3;

//                for(int j = 0; j < chunks.size(); j++) { //ineffecient check for duplicate chunks
//                    if(!(chunks.get(j).chunkX == xpos && chunks.get(j).chunkZ == zpos))
                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos);//center

                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos + 1);//top right
                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos - 1);//bottom right
                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos + 1);//top left
                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos - 1);//bottom left

                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos);//right
                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos);//left
                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos + 1);//top
                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos - 1);//bottom
//                }
                pressed = 1000;

            }
        }

        for(int i = 0; i < chunks.size(); i++) {
            chunks.get(i).update();
        }
    }

    public void destroy() {
        for(int i = 0; i < chunks.size(); i++) {
            chunks.get(i).destroy();
            chunks.remove(i);
        }
    }
}
