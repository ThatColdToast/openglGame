package thatcoldtoast.openglGame.world;

import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.handlers.KeyboardHandler;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
    private int renderDistance = 2;

    public World() {

    }

    public void create(long s) { //s = seed
        if(!CREATED) {
            CREATED = true;
            seed = s;

            int gridSize = 4;



            //Generation starts here
//            for(int i = 1; i <= gridSize; i++) //Single Threaded
//            {
//                for(int j = 1; j <= gridSize; j++)
//                {
//                    chunks.add(new Chunk(i, j));
//                }
//            }


//            for(int i = 1; i <= gridSize; i++) //Multi Threaded, array of objects
//            {
//                for(int j = 1; j <= gridSize; j++)
//                {
//                    int num = i * 8 + j;
//                    worldGenerators[num] = new WorldGeneratorThread();
//                    worldGenerators[num].start(this, i, j);
//                }
//            }

//            for(int i = 1; i <= gridSize; i++) //Multi Threaded, 1 object
//            {
//                for(int j = 1; j <= gridSize; j++)
//                {
//                    WorldGeneratorThread worldGeneratorTmp = new WorldGeneratorThread();
//                    worldGeneratorTmp.start(this, i, j);
//                }
//            }


        }
    }

    int pressed = 0;

    public void update() {
        float playerPosX = Main.MainPlayer.getPosition().x;
        float playerPosZ = Main.MainPlayer.getPosition().z;

        int playerChunkCoordX = (int)(playerPosX / Chunk.chunkSize);
        int playerChunkCoordZ = (int)(playerPosZ / Chunk.chunkSize);

//        System.out.println("X: " + playerChunkCoordX + " Z: " + playerChunkCoordZ);

        for(int x = playerChunkCoordX - renderDistance; x < playerChunkCoordX + renderDistance; x++) {
            for(int z = playerChunkCoordZ - renderDistance; z < playerChunkCoordZ + renderDistance; z++) {
                int duplicateChunks = 0;
                for(Chunk c: chunks) {
                    if(c.chunkX == x && c.chunkZ == z) {
                        duplicateChunks++;
                    }
                }

                if(duplicateChunks > 1) {
                    System.out.println("ERROR: More than one chunk at location found");
                } else if(duplicateChunks == 0) {

                    //Generate new chunk
                    chunks.add(new Chunk(x, z));

//                    CompletableFuture.supplyAsync(this::addChunk);
                }
            }
        }

        //Simple pseudo code
        //Get possible chunk locations
        //Loop through possible chunks
            //Check if distance is inside 50
                //Generate chunk

        //Complicated pseudo code
        //Get player pos and add/sub by render distance for both x and z
        //loop through all possible positions for a new chunk
            //If it is already a chunk
                //continue
            //Else
                //Generate new chunk at x and z




        for(int i = 0; i < chunks.size(); i++) { //Chunk update
            //Remove far chunks
            int chunkX = chunks.get(i).chunkX * Chunk.chunkSize;
            int chunkZ = chunks.get(i).chunkZ * Chunk.chunkSize;
            float xDist = chunkX - playerPosX;
            float zDist = chunkZ - playerPosZ;
            float dist = (float) Math.sqrt((xDist * xDist) + (zDist * zDist));
//            System.out.println("X: " + chunkX + " Z: " + chunkZ + " Dist: " + dist);

            if(dist > 50) { //Multithreading
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
//                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos);//center
//
//                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos + 1);//top right
//                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos - 1);//bottom right
//                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos + 1);//top left
//                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos - 1);//bottom left
//
//                    new WorldGeneratorThread().start(this, (int) xpos + 1, (int) zpos);//right
//                    new WorldGeneratorThread().start(this, (int) xpos - 1, (int) zpos);//left
//                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos + 1);//top
//                    new WorldGeneratorThread().start(this, (int) xpos, (int) zpos - 1);//bottom
//                }
                pressed = 1000;
            }
        }

        for(int i = 0; i < chunks.size(); i++) {
            chunks.get(i).update();
        }


    }

    private void addChunk(int x, int z) {
        chunks.add(new Chunk(x, z));
    }

    public void destroy() {
        for(int i = 0; i < chunks.size(); i++) {
            chunks.get(i).destroy();
            chunks.remove(i);
        }
    }
}
