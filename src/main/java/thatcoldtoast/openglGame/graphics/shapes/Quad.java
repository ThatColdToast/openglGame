package thatcoldtoast.openglGame.graphics.shapes;

import thatcoldtoast.openglGame.graphics.Mesh;

public class Quad {
    Mesh mesh1;
    Mesh mesh2;

    public Quad() {
        mesh1 = new Mesh();
        mesh2 = new Mesh();
    }

    public void create(float[] vert1, float[] vert2, float[] vert3, float[] vert4) {
    //public void create() {
        if (vert1.length != 3) //make sure positions are of correct size
            return;
        if (vert2.length != 3)
            return;
        if (vert3.length != 3)
            return;
        if (vert4.length != 3)
            return;

        mesh1.create(new float[]{
                -1, -1, 0, 0, 1, //Bottom Left   Texture coords start at top left
                -1, 1, 0, 0, 0, //Top Left
                1, -1, 0, 1, 1, //Bottom Right
        });
        mesh2.create(new float[]{
                1, 1, 0, 1, 0, //Top Right   Texture coords start at top left
                -1, 1, 0, 0, 0, //Top Left
                1, -1, 0, 1, 1, //Bottom Right
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
