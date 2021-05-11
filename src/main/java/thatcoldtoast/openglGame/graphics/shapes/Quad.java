package thatcoldtoast.openglGame.graphics.shapes;

public class Quad {
    Mesh mesh1;
    Mesh mesh2;
    float textureAtlasSize = 4; // 4x4 grid should be int but float makes math easier

    public Quad() {
        mesh1 = new Mesh();
        mesh2 = new Mesh();
    }

    public void create(float[] vert1, float[] vert2, float[] vert3, float[] vert4, int textureIndex) {
    //public void create() {
        if (vert1.length != 3) //make sure positions are of correct size
            return;
        if (vert2.length != 3)
            return;
        if (vert3.length != 3)
            return;
        if (vert4.length != 3)
            return;
        if (textureIndex < 0 || textureIndex > 15)
            return;

        float index = textureIndex;

        float xPos = (index % textureAtlasSize) / textureAtlasSize; //left bound of texture
        float yPos = (float) (Math.ceil((index / textureAtlasSize)) - 1); //top bound of texture
        float offsetSize = 1.0f / textureAtlasSize;

        mesh1.create(new float[]{
                vert1[0], vert1[1], vert1[2], xPos,              yPos + offsetSize, //Bottom Left   Texture coords start at top left
                vert3[0], vert3[1], vert3[2], xPos + offsetSize, yPos + offsetSize, //Bottom Right
                vert2[0], vert2[1], vert2[2], xPos,              yPos, //Top Left
        });
        mesh2.create(new float[]{
                vert4[0], vert4[1], vert4[2], xPos + offsetSize, yPos, //Top Right   Texture coords start at top left
                vert2[0], vert2[1], vert2[2], xPos,              yPos, //Top Left
                vert3[0], vert3[1], vert3[2], xPos + offsetSize, yPos + offsetSize, //Bottom Right
        });
    }

    public void draw() {
        mesh1.draw();
        mesh2.draw();
    }

    public void destroy()
    {
        mesh1.destroy();
        mesh2.destroy();
    }
}
